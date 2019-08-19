package com.example.minimoneybox.model.Callback

import android.util.Log

import com.example.minimoneybox.application.App
import com.example.minimoneybox.model.objects.Lisa
import com.example.minimoneybox.model.objects.Login_
import com.example.minimoneybox.model.objects.ProductResponse
import io.reactivex.observers.DisposableSingleObserver
import io.realm.Realm

class userCallback internal constructor() : DisposableSingleObserver<Login_>() {


    override fun onSuccess(t: Login_) {

        val realm = Realm.getInstance(App.instance.getRealm("User")!!)

        realm.executeTransactionAsync { realm ->

            //clear realm

            Log.e("REALM", "copying to realm in async")

            realm.copyToRealm(t)



        }



    }

    override fun onError(e: Throwable) {

        Log.e("ERROR",e.localizedMessage)
    }





}
