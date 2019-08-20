package com.example.minimoneybox.model.Callback

import android.os.Looper
import android.util.Log
import com.example.minimoneybox.Activities.mainActivity

import com.example.minimoneybox.application.App
import com.example.minimoneybox.model.objects.Login_
import com.example.minimoneybox.mvp.Activity.MainContract
import io.reactivex.observers.DisposableSingleObserver
import io.realm.Realm
import io.realm.RealmChangeListener
import io.realm.RealmObject

class userCallback internal constructor() : DisposableSingleObserver<Login_>() {

    val realm = Realm.getInstance(App.instance.getRealm("User")!!)

    override fun onSuccess(t: Login_) {

        //what thread
        if(Looper.myLooper() == Looper.getMainLooper())
        {

            Log.e("THREAD", "in main thread creating the realm objects")
        }
        else{
            Log.e("THREAD", "not in main ui thread oops")


        }
       createInRealm(t)
    }
    private fun createInRealm(objectFactory: Login_) {
        realm.executeTransaction{
            realm ->
           val obj = objectFactory
           realm.copyToRealm(obj)

        }
    }


    override fun onError(e: Throwable) {

        Log.e("ERROR","there has been a bad error")
    }





}
