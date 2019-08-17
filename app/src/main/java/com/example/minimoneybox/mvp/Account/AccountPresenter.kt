package com.example.minimoneybox.mvp.Account

import android.content.SharedPreferences
import android.util.Log
import com.example.minimoneybox.Activities.mainActivity
import com.example.minimoneybox.application.App
import com.example.minimoneybox.model.ApplicationModelContract
import com.example.minimoneybox.model.objects.Login_
import com.example.minimoneybox.mvp.Activity.MainContract
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where

class AccountPresenter (mymodelinteractor: ApplicationModelContract): AccountContract.Presenter {

    private lateinit var type: String

    val userRealm = App.instance.getRealm("User")

    val user= Realm.getInstance(userRealm)

    lateinit var myActivity:MainContract.View



    private val modelInteractor = mymodelinteractor

    lateinit var myView: AccountContract.View

    lateinit var sharedPref: SharedPreferences




    lateinit var userinfo:RealmResults<Login_>

    override fun attach(view: AccountContract.View) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun attach(view: AccountContract.View, myActivity: mainActivity, accountType: String) {
        this.myView = view

        this.myActivity=myActivity

        this.type=accountType


        //get account info from realm and update the view

        myView.updateDetails()
    }




    //Application context






    override fun detatchView() {

    }

    override fun makePayment(amount: Int, id: Int) {

        modelInteractor.makePaymentIntoAccount(amount,id)

    }






}
