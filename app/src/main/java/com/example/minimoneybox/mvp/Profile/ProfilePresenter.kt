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

    lateinit var userinfo:Login_



    private val modelInteractor = mymodelinteractor

    lateinit var myView: ProfileContract.View

    val userRealm = App.instance.getRealm("User")

    val user= Realm.getInstance(userRealm)

    val myUser = user.where<Login_>().findFirstAsync()


    override fun attach(view: ProfileContract.View) {

        Log.e("Profile", "inside profile attach")

        this.myView = view

        if(myUser.isValid){

            myView.setProfileInfo(myUser)

           myView.makeProfileVisible()
        }

        realmListener()

        //profile fragment only gets shown on resume if the token is active
        //at the point of attaching the viw presenter will check if user details
        //hae changed , if not to save processing and get this info from the cache

        }



    override fun detatchView() {
       myView.makeProfileVisible()
    }



      fun realmListener() {


          Log.e("REALM","Inside realm listener profile")

        userinfo = user.where<Login_>().findFirstAsync()

        userinfo.addChangeListener(RealmChangeListener<Login_>{


            user-> if(userinfo.isValid()) myView.setProfileInfo(user).also { myView.makeProfileVisible() }


            //The elements the presenter will update are in a frame that is currently invisible
            //once the change listener returns the user obect then we can populate this invisible view
            //and make it invisible



        })
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
