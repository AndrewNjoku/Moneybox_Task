package com.example.minimoneybox.model.Callback

import android.util.Log

import com.example.minimoneybox.application.App
import com.example.minimoneybox.model.objects.Lisa
import com.example.minimoneybox.model.objects.Login_
import com.example.minimoneybox.model.objects.ProductResponse
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableSingleObserver
import io.realm.Realm

class paymentCallback internal constructor() : DisposableCompletableObserver() {


    override fun onComplete() {



    }





    override fun onError(e: Throwable) {

        Log.e("ERROR",e.message)
    }





}
