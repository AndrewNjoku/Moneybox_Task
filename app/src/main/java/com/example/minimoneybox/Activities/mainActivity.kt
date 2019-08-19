package com.example.minimoneybox.Activities

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.minimoneybox.DI.Dagger_Activity.ActivityModule
import com.example.minimoneybox.R
import com.example.minimoneybox.application.App
import com.example.minimoneybox.fragments.AccountFragment
import com.example.minimoneybox.fragments.LoginActivityFragment
import com.example.minimoneybox.fragments.ProfileFragment
import com.example.minimoneybox.mvp.Activity.MainContract

import javax.inject.Inject





class mainActivity : AppCompatActivity(), MainContract.View, SharedPreferences.OnSharedPreferenceChangeListener {


    @Inject
    lateinit var presenter : MainContract.Presenter

    lateinit var _sharedPref: SharedPreferences

    lateinit var listener: SharedPreferences.OnSharedPreferenceChangeListener


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e("ERROR","In onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        injectDependency()

        _sharedPref= App.instance.getSharedPreferences("Token Store", Context.MODE_PRIVATE)
        presenter.attach(this,_sharedPref)
        _sharedPref.registerOnSharedPreferenceChangeListener(this)

    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {

       showProfileFragment()

    }




    override fun onPause() {
        super.onPause()
        _sharedPref.unregisterOnSharedPreferenceChangeListener(this)
        presenter.detatchView()

    }


    override fun onResume() {

        Log.e("ERROR","In onResume")
        super.onResume()
        _sharedPref.registerOnSharedPreferenceChangeListener(this)
        presenter.attach(this,_sharedPref)

    }

    private fun injectDependency() {

        App.instance.component
                .plus(ActivityModule(this))
                .inject(this)

    }

    override fun onBackPressed() {

        val count = supportFragmentManager.backStackEntryCount

        if (count == 0) {
            super.onBackPressed()
            //additional code
        } else {
            supportFragmentManager.popBackStack()
        }

    }

    override fun showLoginFragment() {

            supportFragmentManager.beginTransaction()
                    .replace(com.example.minimoneybox.R.id.frame,LoginActivityFragment().newInstance()).commit()
        }


    override fun showProfileFragment() {

        Log.e("PROFILE", "Showing profile fragment")

        if(supportFragmentManager.findFragmentById(R.id.frame)!=null) {
            supportFragmentManager.beginTransaction()
                    .detach(supportFragmentManager.findFragmentById(R.id.frame)!!).commit()
        }

            supportFragmentManager.beginTransaction()
                    .addToBackStack(null).replace(R.id.frame, ProfileFragment().newInstance()).commit()

    }


    override fun showAcountFragment(accounttype: String) {


            supportFragmentManager.beginTransaction()
                    .addToBackStack(null).replace(R.id.frame, AccountFragment().newInstance(accounttype)).commit()


    }
    override fun showStocksFragment() {

        showAcountFragment("Stocks")

    }

    override fun refreshAccountFragment() {

    }

    override fun showGeneralFragment() {

        showAcountFragment("General")

    }

    override fun showLisaFragment() {
        showAcountFragment("Lisa")

    }

    override fun logout() {

        Log.e("LOGOUT", "inside logout")


        //realm will be cleared on login

        if(supportFragmentManager.findFragmentById(R.id.frame)!=null) {
            supportFragmentManager.beginTransaction()
                    .detach(supportFragmentManager.findFragmentById(R.id.frame)!!).commit()
        }
        //clear shared pref which stores the token
        _sharedPref.edit().clear().apply()
        showLoginFragment()


    }







}
