package com.example.minimoneybox.Activities

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.minimoneybox.DI.Dagger_Activity.ActivityModule
import com.example.minimoneybox.R
import com.example.minimoneybox.application.App
import com.example.minimoneybox.fragments.AccountFragment
import com.example.minimoneybox.fragments.LoginActivityFragment
import com.example.minimoneybox.fragments.ProfileFragment
import com.example.minimoneybox.model.objects.Login_
import com.example.minimoneybox.mvp.Activity.MainContract
import io.realm.Realm


import javax.inject.Inject


class mainActivity : AppCompatActivity(), MainContract.View{


    @Inject
    lateinit var presenter : MainContract.Presenter

    lateinit var _sharedPref: SharedPreferences

    var accoutFragType="Lisa"




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

        Log.e("LOGIN", "created login fragment")

            supportFragmentManager.beginTransaction()
                    .replace(R.id.frame,LoginActivityFragment().newInstance(),"login")
                    .commit()
        presenter.registerRealmListener()

        }



    override fun showProfileFragment() {


                supportFragmentManager.beginTransaction()
                        .detach(supportFragmentManager
                                .findFragmentById(R.id.frame)!!)
                                .commit()

            //brand new profile we save to backstack for later
            supportFragmentManager.beginTransaction()
                    .replace(R.id.frame, ProfileFragment()
                            .newInstance(),"profile")
                            .commit()

        }



    override fun showAcountFragment(accounttype: String) {

        Log.e("ACCOUNT", "Showing account fragment")

        supportFragmentManager.beginTransaction()
                .detach(supportFragmentManager
                        .findFragmentById(R.id.frame)!!)
                        .commit()

            val accountinstance = AccountFragment().newInstance(accounttype)
            supportFragmentManager.beginTransaction()
                    .replace(R.id.frame, accountinstance,accounttype)
                    .commit()
        }






    override fun reattachRealmListener() {
       // presenter.realmListener()
    }


    override fun showStocksFragment() {

        showAcountFragment("Stocks")
        this.accoutFragType="Stocks"

    }

    override fun showGeneralFragment() {

        showAcountFragment("General")
        this.accoutFragType="General"

    }

    override fun showLisaFragment() {
        showAcountFragment("Lisa")
        this.accoutFragType="Lisa"

    }

    override fun refreshCurrentFragment() {

        Log.e("REFRESH", "refreshing account frag")

        showAcountFragment(accoutFragType)

    }


    override fun logout() {

        Log.e("LOGOUT", "inside logout")


        //realm will be cleared on login

        if(supportFragmentManager.findFragmentById(R.id.frame)!=null) {
            supportFragmentManager.beginTransaction()
                    .detach(supportFragmentManager
                            .findFragmentById(R.id.frame)!!).commit()
        }
        //clear shared pref which stores the token
        _sharedPref.edit().clear().apply()

        //clear realm

        val myRealm = App.instance.getRealm("User")
        Realm.getInstance(myRealm).executeTransaction { realm -> realm.deleteAll() }
        showLoginFragment()


    }


    fun verifyAvailableNetwork():Boolean{
        val connectivityManager=this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo=connectivityManager.activeNetworkInfo
        return  networkInfo!=null && networkInfo.isConnected
    }

    override fun showPaymentSuccessToast() {
        Toast.makeText(this,"Payment Successfull!",Toast.LENGTH_SHORT).show()
    }

    override fun showPaymentFailToast() {
        Toast.makeText(this,"Payment Unsuccessfull, Try again",Toast.LENGTH_SHORT).show()
    }



}
