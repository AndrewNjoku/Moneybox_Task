package com.example.minimoneybox.model


import android.content.Context
import android.content.SharedPreferences
import android.util.Log.e
import com.example.minimoneybox.API.MoneyBoxAPI
import com.example.minimoneybox.Activities.mainActivity
import com.example.minimoneybox.application.App
import com.example.minimoneybox.model.Callback.paymentCallback
import com.example.minimoneybox.model.Callback.userCallback
import com.example.minimoneybox.model.objects.Login
import com.example.minimoneybox.model.objects.Payment
import com.example.minimoneybox.mvp.Login.LoginContract
import com.example.minimoneybox.mvp.Activity.MainContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import io.realm.RealmConfiguration

class ApplicationModel (myService: MoneyBoxAPI): ApplicationModelContract {

    private lateinit var myActivityView: MainContract.View

    private lateinit var myRealm : RealmConfiguration

    private val service = myService

    private lateinit var mySharedPreferences: SharedPreferences

    private var searchDisposable: Disposable? = null

    val realm = Realm.getInstance(App.instance.getRealm("User")!!)

    override fun setUserLoginAccessToken(username: String, password: String ,context: LoginContract.Presenter, activity:mainActivity) {

        mySharedPreferences=App.instance.getSharedPreferences("Token Store", Context.MODE_PRIVATE)

        //mySharedPreferences.edit().clear().apply()
        this.myActivityView=activity
        val login = Login()
        login.email=username
        login.password=password
        login.idfa="ANYTHING"

        searchDisposable= service.Login(login)
                .subscribeOn(Schedulers.io()).flatMap { result -> service
                        .getAccountInfo("Bearer "+result.session?.bearerToken.toString())
                        .subscribeOn(Schedulers.io()).also { e("Token",result.session?.bearerToken) }
                        .also { mySharedPreferences.edit().putString("BEARER_TOKEN", result.session?.bearerToken.toString()).apply() }
                        .also { mySharedPreferences.edit().putLong("TIME",System.currentTimeMillis()).apply() }}
                .observeOn(AndroidSchedulers.mainThread()).doFinally { searchDisposable?.dispose() }
                .subscribeWith(userCallback())



        e("ERROR","after network call")


    }


    fun updateDetailsOnChange(){
        mySharedPreferences=App.instance.getSharedPreferences("Token Store", Context.MODE_PRIVATE)

        //clear cache
        Realm.getInstance(App.instance.getRealm("User")).executeTransactionAsync { realm -> realm.deleteAll() }

        val myToken = "Bearer "+mySharedPreferences.getString("BEARER_TOKEN","")
        searchDisposable=
        service.getAccountInfo(myToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).doFinally { searchDisposable?.dispose() }
                .subscribeWith(userCallback())
    }



    override fun makePaymentIntoAccount(ammount: Int, accountid: Int) {
        mySharedPreferences=App.instance.getSharedPreferences("Token Store", Context.MODE_PRIVATE)

        val myToken = "Bearer "+mySharedPreferences.getString("BEARER_TOKEN","")

        val payment = Payment()
        payment.Amount= ammount
        payment.InvestorProductId= accountid

        service.OneOffPayment(myToken,payment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete { updateDetailsOnChange() }
                .subscribe(paymentCallback(myActivityView))


    }


    override fun createInstance(service: MoneyBoxAPI):ApplicationModelContract{

        myRealm= App.instance.getRealm("Lisa")!!

        return ApplicationModel(service)


    }




}