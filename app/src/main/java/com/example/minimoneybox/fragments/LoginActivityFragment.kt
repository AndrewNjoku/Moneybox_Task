package com.example.minimoneybox.fragments

import android.content.Context
import android.os.Bundle
import com.google.android.material.textfield.TextInputLayout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.makeText
import butterknife.BindView
import butterknife.ButterKnife
import com.airbnb.lottie.LottieAnimationView
import com.example.minimoneybox.DI.Dagger_Fragment.FragmentComponent
import com.example.minimoneybox.Dagger_Login.FragmentModule

import com.example.minimoneybox.application.App
import com.example.minimoneybox.mvp.Login.LoginContract
import javax.inject.Inject
import butterknife.Unbinder
import com.example.minimoneybox.Activities.mainActivity
import com.example.minimoneybox.R
import com.example.minimoneybox.model.objects.Login_


class LoginActivityFragment : Fragment(), LoginContract.View {



    private lateinit var myActivity: mainActivity
    private lateinit var fragmentComponent: FragmentComponent
    //Inject the presenter
    @Inject
    lateinit var presenter: LoginContract.Presenter

    private var unbinder: Unbinder? = null

    @BindView (R.id.btn_sign_in) lateinit var btn_sign_in : Button
    @BindView (R.id.til_email) lateinit var til_email : TextInputLayout
    @BindView (R.id.et_email) lateinit var et_email : EditText
    @BindView (R.id.til_password) lateinit var til_password : TextInputLayout
    @BindView (R.id.et_password) lateinit var et_password : EditText
    @BindView (R.id.til_name) lateinit var til_name : TextInputLayout
    @BindView (R.id.et_name)lateinit var et_name : EditText
    @BindView (R.id.animation2)lateinit var pigAnimation : LottieAnimationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        injectDependencies()

        presenter.attach(this,myActivity)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.activity_login, container, false)

        unbinder = ButterKnife.bind(this, view)

        setupListeners()

        return view
    }

    private fun injectDependencies() {
        App.instance.component.plus(FragmentModule()).inject(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        this.myActivity=context as mainActivity
    }

    override fun onStart() {
        super.onStart()
        setupAnimation()
    }

    private fun setupListeners() {

        // Here originally was the view initialisation logic. I have used butterknife to simplify things
        // I have changed the name of this method to reflect changes in the logic
        //Now this method is responsible for configuring view listeners.
        btn_sign_in.setOnClickListener {

            presenter.validateFields()

        }

    }


    private fun setupAnimation() {
        pigAnimation.playAnimation()
    }


  //view methods

    override val email: String
        get() = et_email.text.toString()
    override val name: String
        get() = et_name.text.toString()
    override val password: String
        get() = et_password.text.toString()


    //View methods, to be called from the presenter

    override fun validationToast() {
        Toast.makeText(activity, R.string.input_valid, Toast.LENGTH_LONG).show()
    }

    override fun invalidToast() {

        makeText(activity,"Input is invalid", Toast.LENGTH_LONG).show()

    }


    override fun tilEmailError() {

        til_email.error= getString(R.string.email_address_error)
    }

    override fun tilPasswordError() {

        til_password.error= getString(R.string.password_error)
    }

    override fun tilNameError() {

        til_name.error = getString(R.string.full_name_error)

    }

    override fun onDestroyView() {
        super.onDestroyView()

        unbinder?.unbind()

    }

    fun newInstance(): LoginActivityFragment {
        return LoginActivityFragment()
    }

    override fun updateDetails() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateAccountInfo(user: Login_) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun refreshAccountFragment() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    companion object {
        const val EMAIL_REGEX = "[^@]+@[^.]+\\..+"
        const val NAME_REGEX = "[a-zA-Z]{6,30}"
        const val PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[A-Z]).{10,50}$"
        val firstAnim = 0 to 109
        val secondAnim = 131 to 158
    }





}
