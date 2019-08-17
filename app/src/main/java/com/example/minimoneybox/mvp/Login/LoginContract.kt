package com.example.minimoneybox.mvp.Login

import android.view.View
import com.example.minimoneybox.Activities.mainActivity
import com.example.minimoneybox.mvp.Account.AccountContract
import com.example.minimoneybox.mvp.BaseContract


interface LoginContract {

    interface Presenter : BaseContract.Presenter<View> {

        fun validateFields()

        fun login(userName: String, passWord: String)

        fun gotToProfile()

        fun attach(view: View, context: mainActivity)
    }

    interface View : BaseContract.View, AccountContract.View {

        val email: String

        val name: String

        val password: String

        fun validationToast()

        fun invalidToast()

        fun tilNameError()

        fun tilEmailError()

        fun tilPasswordError()


    }
}
