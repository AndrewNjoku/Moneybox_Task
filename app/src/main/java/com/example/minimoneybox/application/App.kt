package com.example.minimoneybox.application

import android.app.Application
import com.example.minimoneybox.DI.Dagger_App.DaggerAppComponent
import com.example.minimoneybox.DI.Dagger_App.AppComponent
import com.example.minimoneybox.DI.Dagger_App.AppModule
import io.realm.Realm
import io.realm.RealmConfiguration

class App : Application(){

    lateinit var realmConfigurationUser: RealmConfiguration


    lateinit var component: AppComponent


    override fun onCreate() {
        super.onCreate()
        instance = this
        setup()


    }

    private fun setup() {
        component = DaggerAppComponent.builder()
                .appModule(AppModule(this)).build()
        component.inject(this)

        Realm.init(applicationContext)

        realmConfigurationUser = RealmConfiguration.Builder()
                .name("Lisa Realm")
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(realmConfigurationUser)
    }

    //dont need this using properties
    fun getApplicationComponent(): AppComponent {

        return component
    }

    fun getRealm(name: String): RealmConfiguration? {
        when (name) {

            "User" ->

                return realmConfigurationUser

        }
        return null
    }




    //use companion object instead of abstract method for lazy instantiation
    companion object {
        lateinit var instance: App private set
    }

}
