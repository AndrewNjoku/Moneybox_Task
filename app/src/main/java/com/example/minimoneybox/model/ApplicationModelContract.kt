package com.example.minimoneybox.model


import com.example.minimoneybox.API.MoneyBoxAPI
import com.example.minimoneybox.Activities.mainActivity
import com.example.minimoneybox.mvp.Login.LoginContract

interface ApplicationModelContract {



    fun makePaymentIntoAccount(accountId: Int, amount: Int)

    fun createInstance(service: MoneyBoxAPI): ApplicationModelContract

    fun setUserLoginAccessToken(username: String, password: String, context: LoginContract.Presenter, activity: mainActivity)
}
