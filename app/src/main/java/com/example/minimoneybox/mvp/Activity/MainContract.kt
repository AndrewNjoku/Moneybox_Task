package com.example.minimoneybox.mvp.Activity

import android.content.SharedPreferences
import com.example.minimoneybox.model.objects.Login_
import com.example.minimoneybox.mvp.BaseContract


interface MainContract {

    interface Presenter: BaseContract.Presenter<View> {

        //check if logged in on the mainActivity
        fun loggedIn():Boolean

        fun showProfileFragment()

        fun checkifTokenActive(): Boolean

        fun attach(view: View, _sharedPref: SharedPreferences)

        fun registerRealmListener()
    }

    interface View : BaseContract.View {

        fun showLoginFragment()

        fun showProfileFragment()

        fun showAcountFragment(accounttype:String)

        fun showStocksFragment()
        fun showGeneralFragment()
        fun showLisaFragment()

        fun logout()
        fun reattachRealmListener()
        fun refreshCurrentFragment()
        fun showPaymentSuccessToast()
        fun showPaymentFailToast()


    }
}
