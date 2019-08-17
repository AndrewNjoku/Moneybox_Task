package com.example.minimoneybox.mvp.Profile

import com.example.minimoneybox.model.objects.Login_
import com.example.minimoneybox.mvp.BaseContract


interface ProfileContract {

    interface Presenter: BaseContract.Presenter<View> {
        fun activateStocks()
        fun activategeneral()
        fun activateLisa()

    }

    interface View : BaseContract.View {

        fun setProfileInfo(user: Login_)

        fun makeProfileVisible()

        fun makeProfileInvisible()
        fun showStocksScreen()
        fun showGeneralScreen()
        fun showLisaScreen()
    }
}
