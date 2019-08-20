package com.example.minimoneybox.mvp.Activity

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.minimoneybox.Activities.mainActivity
import com.example.minimoneybox.application.App
import com.example.minimoneybox.model.objects.Login_
import com.example.minimoneybox.mvp.Activity.MainContract.*
import io.realm.Realm
import io.realm.RealmChangeListener
import io.realm.RealmObject.*
import io.realm.kotlin.where

class MainPresenter(myContext: Activity): MainContract.Presenter,SharedPreferences.OnSharedPreferenceChangeListener ,RealmChangeListener<Realm>{

    val myRealm = App.instance.getRealm("User")

    val realm = Realm.getInstance(myRealm)

    override fun attach(view: View) {

    }

    var currentTime: Long = 0

    var tokenTime: Long = 0

    lateinit var sharedPref: SharedPreferences

    private lateinit var view: View


    override fun loggedIn(): Boolean {

        val myToken: String? = sharedPref.getString("BEARER_TOKEN", "")

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
        sharedPref.registerOnSharedPreferenceChangeListener(this)
        //if the user has a bearer token that is active (5 mins)


        //This is a check when the presenter is created and the view is attached, meaning

        if (checkifTokenActive()) {

            view.showProfileFragment()

        } // as default
        else {
                //ive added change listener after clearing realm , which essentially means the user has resumed the app and is no longer logged in
                //based on the bearer token, in this case we actvate the listener after this transaction so the realmchangelistener does not get activated
                Log.e("LOGIN", "Showing login fragment")
                view.showLoginFragment()


        }
    }

    override fun onChange(t: Realm) {

        Log.e("REALM","in profile on change")
        //onChange from the login call once the data has been retrofitted we want to show the profile fragment which
        //will automatically update the profile details on attach
        view.showProfileFragment()

        realm.removeChangeListener(this)
    }

    override fun registerRealmListener(){

        realm.addChangeListener(this)

    }


    override fun detatchView() {
        sharedPref.unregisterOnSharedPreferenceChangeListener(this)
        realm.removeChangeListener(this)
        // user.removeAllChangeListeners()
    }


    override fun showProfileFragment() {
        view.showProfileFragment()
    }

    override fun checkifTokenActive(): Boolean {
        //Get the current time
        currentTime = System.currentTimeMillis()

        //get the time of the bearer token issuing
        tokenTime = sharedPref.getLong("TIME", 0)

        val howLong = (currentTime - tokenTime)

        //if token is still active can be used to stay logged in
        if (howLong < 300000) {

            return true

        }
        return false


    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        Log.e("SHARED_PREFERENCES", "inside shared preferences listener")
        if (p1 == "BEARER_TOKEN") {

            Log.e("SHARED_PREFERENCES", "Token store changed")
            // showProfileFragment()
        }

        if (p1 =="ACCOUNT_REFRESH"){
            view.refreshCurrentFragment()
        }
    }
}








