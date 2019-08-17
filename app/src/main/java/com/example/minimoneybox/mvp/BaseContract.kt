package com.example.minimoneybox.mvp

import com.example.minimoneybox.Activities.mainActivity

class BaseContract {


    interface Presenter<in T> {

        fun attach(view: T)

        fun detatchView()
    }

    interface View {

    }

}