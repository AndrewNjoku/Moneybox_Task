package com.example.minimoneybox.mvp.Account

import com.example.minimoneybox.Activities.mainActivity
import com.example.minimoneybox.model.objects.Login_
import com.example.minimoneybox.mvp.BaseContract


interface AccountContract {

    interface Presenter: BaseContract.Presenter<View> {
        fun makePayment(i: Int, accountType: Int)

        fun attach(view: View, myActivity: mainActivity, accountType: String)
        fun registerRealmListener()


    }

    interface View : BaseContract.View {
        fun updateDetails()

        fun updateAccountInfo(user: Login_)



    }
}
