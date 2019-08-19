package com.example.minimoneybox.mvp.Profile

import android.util.Log
import com.example.minimoneybox.application.App
import com.example.minimoneybox.model.ApplicationModelContract
import com.example.minimoneybox.model.objects.Login_
import io.realm.Realm
import io.realm.RealmChangeListener
import io.realm.kotlin.where

class ProfilePresenter (mymodelinteractor: ApplicationModelContract): ProfileContract.Presenter {



    //Application context


    val userRealm = App.instance.getRealm("User")

    val user= Realm.getInstance(userRealm)

    lateinit var userDetails: Login_


    lateinit var myView: ProfileContract.View




    override fun attach(view: ProfileContract.View) {

        Log.e("Profile", "inside profile attach")
        this.myView = view

            user.executeTransaction{

                userDetails = user.where<Login_>().findFirst()!!

                if (userDetails.isValid) {

                myView.setProfileInfo(userDetails)

            }
                else{

                    Log.e("PROFILE","Realm is empty so cannot udate user details")
                }
        }



        //profile fragment only gets shown on resume if the token is active
        //at the point of attaching the viw presenter will check if user details
        //hae changed , if not to save processing and get this info from the cache

        }



    override fun detatchView() {

    }


    override fun activateStocks() {
        myView.showStocksScreen()
    }

    override fun activategeneral() {
        myView.showGeneralScreen()
    }

    override fun activateLisa() {

        myView.showLisaScreen()
    }


}
