package com.example.minimoneybox.mvp.Login

import android.content.Context
import android.content.SharedPreferences
import com.example.minimoneybox.Activities.mainActivity
import com.example.minimoneybox.application.App
import java.util.regex.Pattern
import com.example.minimoneybox.fragments.LoginActivityFragment.Companion.EMAIL_REGEX
import com.example.minimoneybox.fragments.LoginActivityFragment.Companion.NAME_REGEX
import com.example.minimoneybox.fragments.LoginActivityFragment.Companion.PASSWORD_REGEX
import com.example.minimoneybox.model.ApplicationModelContract

class LoginPresenter (mymodelinteractor: ApplicationModelContract): LoginContract.Presenter {
    override fun attach(view: LoginContract.View) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private val modelInteractor=mymodelinteractor

    lateinit var myView: LoginContract.View


    private lateinit var context: mainActivity

    lateinit var sharedPref: SharedPreferences


    override fun gotToProfile() {
       // myView.goToProfile()
    }

    override fun attach(view: LoginContract.View, context: mainActivity) {

        sharedPref= App.instance.getSharedPreferences("NAME", Context.MODE_PRIVATE)

        this.context=context

        this.myView=view
    }

    override fun detatchView() {
      // this.myView=null
    }


    //Application context


    override fun login(userName: String, passWord: String) {


        modelInteractor.setUserLoginAccessToken(userName,passWord,this,context)
                //.findUser(myService,userName,passWord,name,my)

    }


    override fun validateFields() {


        if (allFieldsValid()!!) {
            myView.validationToast()

            login(myView.email,myView.password)

        } else {
            myView.invalidToast()
        }
    }


    private fun allFieldsValid(): Boolean? {


        if (Pattern.matches(EMAIL_REGEX, myView.email)) {

            if (Pattern.matches(PASSWORD_REGEX, myView.password)) {

                if (myView.name =="") {

                    return true
                }
                if (Pattern.matches(NAME_REGEX, myView.name)) {

                   sharedPref.edit().putString("NAME",myView.name).apply()
                    return true

                } else {
                    myView.tilNameError()
                }


            } else {
                myView.tilPasswordError()


            }
        }
            else {

            myView.tilEmailError()

        }

        return false
    }


}
