package com.example.minimoneybox.DI.Dagger_App

import android.app.Application
import com.example.minimoneybox.API.MoneyBoxAPI
import com.example.minimoneybox.model.ApplicationModel
import com.example.minimoneybox.model.ApplicationModelContract
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

import javax.inject.Named
import javax.inject.Singleton
import java.util.concurrent.TimeUnit


@Module
class AppModule(private var myApplication: Application) {

    @Provides
    @Singleton
    @Named("OkHttpCacheSize")
    fun provideOkHttpCacheSize(): Int {
        return 5 * 1024 * 1024 //5 MB
    }

    @Provides
    @Singleton
    fun provideApplication(): Application {

        return myApplication
    }


    @Provides
    @Singleton
    fun provideOkHttpCache(context: Application, @Named("OkHttpCacheSize") cacheSize: Int): Cache {
        return Cache(context.cacheDir, cacheSize.toLong())
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(cache: Cache): OkHttpClient {
        return OkHttpClient.Builder()
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .cache(cache)
                .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
    }

    @Provides
    @Singleton
    fun provideMoneyBoxService(retrofit: Retrofit): MoneyBoxAPI {
        return retrofit.create(MoneyBoxAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideApplicationModelInteractor(myservice: MoneyBoxAPI): ApplicationModelContract {

        return ApplicationModel(myservice)
    }



    companion object {

        val CONNECTION_TIMEOUT: Long = 60
        val READ_TIMEOUT: Long = 60
        const val BASE_URL = "https://api-test01.moneyboxapp.com/"


    }

}
