package com.example.minimoneybox.DI.Dagger_Activity

import android.app.Activity
import com.example.minimoneybox.mvp.Activity.MainContract
import com.example.minimoneybox.mvp.Activity.MainPresenter
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private var activity: Activity) {

    @Provides
    @ActivityScope
    fun provideActivity(): Activity {
        return activity
    }


    @Provides
    @ActivityScope
     fun providePresenter(context: Activity): MainContract.Presenter {
        return MainPresenter(context)
    }

}