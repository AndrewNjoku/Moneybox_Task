package com.example.minimoneybox.mvp.Activity

import android.app.Activity
import android.content.SharedPreferences
import android.util.Log
import com.example.minimoneybox.Activities.mainActivity
import com.example.minimoneybox.mvp.Activity.MainContract.*


class MainPresenter(myContext: Activity): MainContract.Presenter {


    override fun attach(view: View) {

    }

    override fun detatchView() {


    }

    private val context =myContext as mainActivity

    var currentTime:Long = 0

    var tokenTime : Long = 0

    lateinit var sharedPref: SharedPreferences

    private lateinit var view: View



    override fun loggedIn(): Boolean {

        val myToken: String? = sharedPref.getString("BEARER_TOKEN","")

        if (myToken != null) {

            return true
        }

        return false
    }

    // pass context also on attach
    override fun attach(view: View, _sharedPref: SharedPreferences) {


        Log.e("CHECK", "Inside attach")
        this.sharedPref = _sharedPref
        this.view = view
        //if the user has a bearer token that is active (5 mins)

        //This is a check when the presenter is created and the view is attached, meaning

        if (checkifTokenActive()) {

            view.showProfileFragment()

        } // as default
        else {

            view.showLoginFragment()
        }
    }

    override fun showProfileFragment() {

        view.showProfileFragment()
    }

    override fun checkifTokenActive(): Boolean {

        //Get the current time
        currentTime=System.currentTimeMillis()

        //get the time of the bearer token issuing
        tokenTime = sharedPref.getLong("TIME",0)

        val howLong = (currentTime-tokenTime)

        //if token is still active can be used to stay logged in
        if (howLong<300000){

            return true

        }
       return false


    }





}
