package com.example.minimoneybox.mvp.Account

import android.content.SharedPreferences
import android.util.Log
import com.example.minimoneybox.Activities.mainActivity
import com.example.minimoneybox.application.App
import com.example.minimoneybox.model.ApplicationModelContract
import com.example.minimoneybox.model.objects.Login_
import com.example.minimoneybox.mvp.Activity.MainContract
import io.realm.Realm
import io.realm.RealmChangeListener
import io.realm.RealmResults
import io.realm.kotlin.where

class AccountPresenter (mymodelinteractor: ApplicationModelContract): AccountContract.Presenter,RealmChangeListener<Realm> {


    private lateinit var type: String

    val userRealm = App.instance.getRealm("User")

    val realm= Realm.getInstance(userRealm)

    lateinit var myActivity:MainContract.View

    private val modelInteractor = mymodelinteractor

    lateinit var myView: AccountContract.View

    override fun attach(view: AccountContract.View) {
      myActivity.refreshCurrentFragment()
    }

    override fun attach(view: AccountContract.View, myActivity: mainActivity, accountType: String) {
        this.myView = view
        this.myActivity=myActivity
        this.type=accountType
        myView.updateDetails()

    }

    override fun registerRealmListener() {
        Log.e("REALM","registering realm change listener")
        realm.addChangeListener(this)

    }

    override fun onChange(t: Realm) {
        Log.e("REALM","activated realm listener Account")
        myActivity.refreshCurrentFragment()
    }


    override fun detatchView() {

        realm.removeChangeListener(this)
    }

    override fun makePayment(amount: Int, id: Int) {

        modelInteractor.makePaymentIntoAccount(amount,id)

    }






}
