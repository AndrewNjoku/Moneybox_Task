package com.example.minimoneybox.DI.Dagger_App


import android.app.Application
import com.example.minimoneybox.DI.Dagger_Activity.ActivityComponent
import com.example.minimoneybox.DI.Dagger_Activity.ActivityModule
import com.example.minimoneybox.DI.Dagger_Fragment.FragmentComponent
import com.example.minimoneybox.Dagger_Login.FragmentModule

import javax.inject.Singleton

import dagger.Component

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(application : Application)

    fun plus(mySubModule: ActivityModule): ActivityComponent

    fun plus(mySubmodule2: FragmentModule): FragmentComponent


//A builder for a component. Components may have a single nested static abstract
// class or interface annotated with @Component.Builder. If they do,
// then the component's generated builder will match the API in the type
//This allows us to customise the way Dagger builds our component




}

