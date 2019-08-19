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
import io.realm.Realm


import javax.inject.Inject


class mainActivity : AppCompatActivity(), MainContract.View{


    @Inject
    lateinit var presenter : MainContract.Presenter

    lateinit var _sharedPref: SharedPreferences




    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e("ERROR","In onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        injectDependency()
        _sharedPref= App.instance.getSharedPreferences("Token Store", Context.MODE_PRIVATE)
        presenter.attach(this,_sharedPref)


    }



    override fun onPause() {
        super.onPause()
        presenter.detatchView()
    }


    override fun onResume() {

        Log.e("ERROR","In onResume")
        super.onResume()
        presenter.attach(this,_sharedPref)

    }

    override fun onDestroy() {
        super.onDestroy()

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

        //reactivate realm listener
        presenter.realmListener()

            supportFragmentManager.beginTransaction()
                    .replace(R.id.frame,LoginActivityFragment().newInstance())
                    .commit()
        }



    override fun showProfileFragment() {


        val fragment = supportFragmentManager.findFragmentByTag("profile")

        if (fragment !=null){

            supportFragmentManager.beginTransaction()
                    .replace(R.id.frame,fragment).commit()
        }
        else {
            //detatch login fragment we dont need to add to stack
            if (supportFragmentManager.findFragmentById(R.id.frame) != null) {
                supportFragmentManager.beginTransaction()
                        .detach(supportFragmentManager.findFragmentById(R.id.frame)!!).commit()
            }

            //brand new profile we save to backstack for later
            supportFragmentManager.beginTransaction()
                    .addToBackStack("profile")
                    .replace(R.id.frame, ProfileFragment()
                            .newInstance())
                            .commit()

        }
    }


    override fun showAcountFragment(accounttype: String) {

        val fragment = supportFragmentManager.findFragmentByTag(accounttype)

        if (fragment !=null) {

            supportFragmentManager.beginTransaction()
                    .replace(R.id.frame, fragment).commit()
        }

                    else{

            val accountinstance = AccountFragment().newInstance(accounttype)
            supportFragmentManager.beginTransaction()
                    .replace(R.id.frame,accountinstance)
                    .addToBackStack(accounttype)
                    .commit()

            }






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

        //clear realm

        val myRealm = App.instance.getRealm("User")
        Realm.getInstance(myRealm).executeTransaction { realm -> realm.deleteAll() }
        showLoginFragment()


    }







}
