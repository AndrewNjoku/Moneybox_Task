package com.example.minimoneybox.DI.Dagger_Activity
import com.example.minimoneybox.Activities.mainActivity
import dagger.Subcomponent

/**
 * Created by ogulcan on 07/02/2018.
 */
@Subcomponent(modules = [ActivityModule::class])
@ActivityScope
interface ActivityComponent {

    fun inject(mainActivity: mainActivity)


}