package com.example.minimoneybox.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.example.minimoneybox.Activities.mainActivity
import com.example.minimoneybox.DI.Dagger_Fragment.FragmentComponent
import com.example.minimoneybox.Dagger_Login.FragmentModule
import com.example.minimoneybox.R

import com.example.minimoneybox.application.App
import com.example.minimoneybox.model.objects.Login_
import com.example.minimoneybox.model.objects.ProductResponse
import com.example.minimoneybox.mvp.Account.AccountContract
import io.realm.Realm
import io.realm.kotlin.isLoaded
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_account_g.*
import javax.inject.Inject


class AccountFragment : Fragment(), AccountContract.View {

    val myRealm = App.instance.getRealm("User")

    val realm = Realm.getInstance(myRealm)

    private var unbinder: Unbinder? = null

    private var accountId: Int = 0

    lateinit var myActivity: mainActivity

    //Inject the presenter
    @Inject
    lateinit var presenter: AccountContract.Presenter

    //stocks and shares widget
    @BindView(R.id.Plan)
    lateinit var plan: TextView
    @BindView(R.id.Money)
    lateinit var money: TextView

    lateinit var accountType: String

    //buttons
    @BindView(R.id.five)
    lateinit var five: Button
    @BindView(R.id.ten)
    lateinit var ten: Button
    @BindView(R.id.fifteen)
    lateinit var fifteen: Button
    @BindView(R.id.button)
    lateinit var button: Button


    override fun updateAccountInfo(user: Login_) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {


        //we need to check which account fragment this is by accessing the budle info and inflating the
        //neccessery layout

        accountType = arguments?.getString("Account").toString()

        val view: View

        view = when (accountType) {

            "Stocks" -> inflater.inflate(R.layout.activity_account_s, container, false)
            "Lisa" -> inflater.inflate(R.layout.activity_account_l, container, false)
            "General" -> inflater.inflate(R.layout.activity_account_g, container, false)
            else -> { // Note the block
                inflater.inflate(R.layout.activity_account_s, container, false)
            }
        }
        unbinder = ButterKnife.bind(this, view)

        injectDependencies()

        presenter.attach(this, myActivity, accountType)

        setupListeners()

        Log.e("id", accountId.toString())

        return view
    }

    private fun injectDependencies() {
        App.instance.component.plus(FragmentModule()).inject(this)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        this.myActivity = context as mainActivity

    }

    override fun onDetach() {
        super.onDetach()

        unbinder?.unbind()
    }


    private val clickListener: View.OnClickListener = View.OnClickListener { view ->
        when (view.id) {

            button.id-> { myActivity.showProfileFragment()}


            five.id -> {
                presenter.makePayment(5, accountId)

                myActivity.showAcountFragment(accountType)
            }

            ten.id -> {
                presenter.makePayment(10, accountId)
                myActivity.showAcountFragment(accountType)


            }

            fifteen.id -> {
                presenter.makePayment(15, accountId)
                myActivity.showAcountFragment(accountType)


            }

        }


    }

    private fun setupListeners() {
        five.setOnClickListener(clickListener)
        ten.setOnClickListener(clickListener)
        fifteen.setOnClickListener(clickListener)
        button.setOnClickListener(clickListener)

    }

    override fun refreshAccountFragment() {

        myActivity.refreshAccountFragment()


    }


    override fun updateDetails() {

        var myUser: Login_

        when (accountType) {

            "Stocks" -> {
                realm.executeTransactionAsync { realm ->
                    myUser = realm
                            .where<Login_>().findFirst().also { container ->
                                for (product: ProductResponse in container?.productResponses!!) {
                                    if (product.product?.name == "ISA") {
                                        updateDetailFields(product)
                                        //set the account id
                                        accountId = product.id!!

                                        Log.e("id", accountId.toString())

                                        Log.e("ASYNC", "Inside realm setting product stocks")
                                        Log.e("Product", product.product?.name)
                                    }
                                }
                            }!!
                }
            }
            "Lisa" -> {
                realm.executeTransactionAsync { realm ->
                    myUser = realm
                            .where<Login_>().findFirst().also { container ->
                                for (product: ProductResponse in container?.productResponses!!) {
                                    if (product.product?.name == "LISA") {
                                        updateDetailFields(product)
                                        accountId = product.id!!
                                        Log.e("id", accountId.toString())
                                        Log.e("ASYNC", "Inside realm setting product LISA")
                                    }
                                }
                            }!!
                }
            }
            "General" -> {
                realm.executeTransactionAsync { realm ->
                    myUser = realm
                            .where<Login_>().findFirst()?.also { container ->
                                for (product: ProductResponse in container.productResponses!!) {
                                    if (product.product?.name == "GIA") {
                                        updateDetailFields(product)
                                        accountId = product.id!!
                                        Log.e("id", accountId.toString())
                                        Log.e("ASYNC", "Inside realm setting product General")
                                    }
                                }
                            }!!
                }

            }
            else -> { // Note the block

                Log.e("Error", "Inside else of when block")
            }


        }
    }


    private fun updateDetailFields(product: ProductResponse) {

        plan.text = "Plan Value: £" + product.planValue.toString()

        money.text = "Money Box: £" + product.moneybox.toString()




    }


    fun newInstance(type: String): AccountFragment {


        val accountFragment = AccountFragment()

        val bundle = Bundle().apply {
            putString("Account", type)
        }

        accountFragment.arguments = bundle

        return accountFragment
    }
}




