package com.example.minimoneybox.fragments

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.airbnb.lottie.LottieAnimationView
import com.example.minimoneybox.Activities.mainActivity
import com.example.minimoneybox.Dagger_Login.FragmentModule
import com.example.minimoneybox.R

import com.example.minimoneybox.application.App
import com.example.minimoneybox.model.objects.Login_
import com.example.minimoneybox.model.objects.ProductResponse
import com.example.minimoneybox.mvp.Activity.MainContract
import com.example.minimoneybox.mvp.Profile.ProfileContract
import javax.inject.Inject





class ProfileFragment : Fragment(), ProfileContract.View {

    private var unbinder: Unbinder? = null
    //main activity view
    lateinit var myActivityView : MainContract.View

    //Inject the presenter
    @Inject
    lateinit var presenter: ProfileContract.Presenter
    //loading screen
   // @BindView(R.id.loadingScreen)lateinit var myLoadingScreen : FrameLayout
    //profile frame
    @BindView(R.id.myProfileFrame)lateinit var myProfileFrame : ConstraintLayout
    //cardViews
    @BindView(R.id.lifetimeCard)lateinit var lifetimecard : CardView
    @BindView(R.id.generalCard)lateinit var generalcard : CardView
    @BindView(R.id.stocksCard)lateinit var stockscard : CardView

    //@BindView(R.id.animation2)lateinit var pigAnimation : LottieAnimationView
    @BindView(R.id.name)lateinit var name : TextView

    //total plan value
    @BindView(R.id.planvalue)lateinit var totalvalue : TextView
    //stocks and shares widget
    @BindView(R.id.Plan)lateinit var splan : TextView
    @BindView(R.id.Money)lateinit var smoney : TextView
    //General investment account
    @BindView(R.id.generalPlan)lateinit var gplan : TextView
    @BindView(R.id.generalMoney)lateinit var gmoney : TextView
    //Lifetime ISA
    @BindView(R.id.lifetimePlan)lateinit var lplan : TextView
    @BindView(R.id.lifetimeMoney)lateinit var lmoney : TextView

    @BindView (R.id.btn_sign_out) lateinit var btn_sign_out : Button

    lateinit var sharedpref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        sharedpref=App.instance.getSharedPreferences("NAME",Context.MODE_PRIVATE)
        injectDependencies()


        val view = inflater.inflate(R.layout.activity_profile, container, false)

        unbinder = ButterKnife.bind(this, view)

        presenter.attach(this)




        waitForData()

        attachListeners()

        return view
    }

    override fun onAttach(context: Activity) {
        super.onAttach(context)
        this.myActivityView=context as mainActivity

    }
    override fun onDetach() {
        super.onDetach()
        presenter.detatchView()
        unbinder?.unbind()
    }

    private fun attachListeners() {
        lifetimecard.setOnClickListener(clickListener)
        stockscard.setOnClickListener(clickListener)
        generalcard.setOnClickListener(clickListener)
        btn_sign_out.setOnClickListener(clickListener)
    }


    private val clickListener: View.OnClickListener = View.OnClickListener { view ->
        when (view.id) {
            stockscard.id -> presenter.activateStocks()

            generalcard.id -> presenter.activategeneral()

            lifetimecard.id -> presenter.activateLisa()

            btn_sign_out.id -> myActivityView.logout()
        }
    }

    private fun waitForData() {

        //pigAnimation.playAnimation()
    }

    private fun injectDependencies() {
        App.instance.component.plus(FragmentModule()).inject(this)
    }



    override fun setProfileInfo(user: Login_) {

        Log.e("Profile","setting profile info")

        val myname = sharedpref.getString("NAME","").orEmpty()

        if (myname.isNotEmpty()){

            name.text= "Hi  " + myname
        }


        val listOfProducts = user.productResponses

        var totalplanvalue=0

        if (listOfProducts != null) {



            for (myproduct: ProductResponse in listOfProducts) {




                when(myproduct.product?.name)
                {
                    "LISA"-> {lplan.text="Plan Value: £" + myproduct.planValue.toString()
                                        lmoney.text="MoneyBox: £"+ myproduct.moneybox.toString()
                                        totalplanvalue += myproduct.planValue?.toInt()!!
                    }
                    "GIA"-> {
                        gplan.text = "Plan Value: £" + myproduct.planValue.toString()
                        gmoney.text="MoneyBox: £"+ myproduct.moneybox.toString()
                        totalplanvalue += myproduct.planValue?.toInt()!!
                    }
                    "ISA"->{splan.text="Plan Value: £"+myproduct.planValue.toString()
                        smoney.text="MoneyBox: £"+ myproduct.moneybox.toString()
                        totalplanvalue += myproduct.planValue?.toInt()!!
                    }
                }
            }
            totalvalue.text="Total Plan Value: £" +totalplanvalue.toString()
            }
        }



    override fun makeProfileVisible() {

      //  Log.e("PROFILE", "making profile visible")

       // myLoadingScreen.visibility=View.INVISIBLE

      //  myProfileFrame.visibility=View.VISIBLE
    }

    override fun makeProfileInvisible() {

       // myLoadingScreen.visibility=View.VISIBLE

      //  myProfileFrame.visibility=View.INVISIBLE
    }


    override fun showStocksScreen() {


        myActivityView.showStocksFragment()



    }

    override fun showGeneralScreen() {

        myActivityView.showGeneralFragment()

    }

    override fun showLisaScreen() {
        myActivityView.showLisaFragment()

    }



    fun newInstance(): ProfileFragment {

        return ProfileFragment()
    }





}
