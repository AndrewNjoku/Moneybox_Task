package com.example.minimoneybox.mvp.Activity

import android.app.Activity
import android.content.SharedPreferences
import android.util.Log
import com.example.minimoneybox.Activities.mainActivity
import com.example.minimoneybox.application.App
import com.example.minimoneybox.model.objects.Login_
import com.example.minimoneybox.mvp.Activity.MainContract.*
import io.realm.Realm
import io.realm.RealmChangeListener
import io.realm.RealmObject.removeAllChangeListeners
import io.realm.kotlin.where


class MainPresenter(myContext: Activity): MainContract.Presenter,SharedPreferences.OnSharedPreferenceChangeListener {


    val userRealm = App.instance.getRealm("User")

    val user = Realm.getInstance(userRealm)

    val myUser = user.where<Login_>().findFirstAsync()


    override fun attach(view: View) {

    }

    private val context = myContext as mainActivity

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
        realmListener()
        sharedPref.registerOnSharedPreferenceChangeListener(this)
        //if the user has a bearer token that is active (5 mins)

        //This is a check when the presenter is created and the view is attached, meaning

        if (checkifTokenActive()) {

            view.showProfileFragment()

        } // as default
        else {

        }
        val myRealm = App.instance.getRealm("User")

        Realm.getInstance(myRealm).executeTransaction { realm ->
            realm.deleteAll()

            Log.e("LOGIN", "Showing login fragment")

            view.showLoginFragment()
        }
    }


    override fun detatchView() {

        sharedPref.unregisterOnSharedPreferenceChangeListener(this)


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

    override fun realmListener() {

        user.addChangeListener {

            realm -> if (!realm.isEmpty) view.showProfileFragment().also { user.removeAllChangeListeners() }
        }
        //and make it invisible
    }



    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        Log.e("SHARED_PREFERENCES", "inside shared preferences listener")
        if (p1=="BEARER_TOKEN") {

            Log.e("SHARED_PREFERENCES", "Token store changed")
            // showProfileFragment()
        }
    }






}
