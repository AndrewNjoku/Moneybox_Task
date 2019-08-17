package com.example.minimoneybox.API


import com.example.minimoneybox.model.objects.*
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*
import java.lang.reflect.Type

interface MoneyBoxAPI {


    //TODO use Reactive pipeline

    //TODO when using reactive pipeline i can flatmap the returned data from the post call.


    //We need to login in order to retrieve the bearer token
    @Headers("AppId:3a97b932a9d449c981b595", "Content-Type:application/json", "appVersion:5.10.0", "apiVersion:3.0.0")
    @POST("users/login")
    fun Login(@Body login: Login): Single<UserToken>


    @Headers("AppId:3a97b932a9d449c981b595", "Content-Type:application/json", "appVersion:5.10.0", "apiVersion:3.0.0")
    @GET("investorproducts")
    fun getAccountInfo(@Header("Authorization") token: String): Single<Login_>


    @Headers("AppId:3a97b932a9d449c981b595", "Content-Type:application/json", "appVersion:5.10.0", "apiVersion:3.0.0")
    @POST("oneoffpayments")
    fun OneOffPayment(@Header("Authorization") token: String,@Body payment: Payment): Completable




    companion object {

        val CONNECTION_TIMEOUT: Long = 60
        val READ_TIMEOUT: Long = 60

        val BASE_URL = "https://api-test01.moneyboxapp.com/"


    }

    }





