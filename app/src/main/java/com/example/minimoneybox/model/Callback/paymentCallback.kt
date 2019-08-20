package com.example.minimoneybox.model.Callback

import android.util.Log

import com.example.minimoneybox.mvp.Activity.MainContract
import io.reactivex.observers.DisposableCompletableObserver

class paymentCallback internal constructor(myActivityView: MainContract.View) : DisposableCompletableObserver() {

    val activityView = myActivityView
    override fun onComplete() {

        activityView.showPaymentSuccessToast()

    }

    override fun onError(e: Throwable) {

        Log.e("ERROR in payment",e.message)
        activityView.showPaymentFailToast()
    }





}
