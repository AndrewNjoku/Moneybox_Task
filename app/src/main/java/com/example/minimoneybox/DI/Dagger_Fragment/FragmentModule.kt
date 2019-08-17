package com.example.minimoneybox.Dagger_Login

import com.example.minimoneybox.model.ApplicationModelContract
import com.example.minimoneybox.mvp.Account.AccountContract
import com.example.minimoneybox.mvp.Account.AccountPresenter
import com.example.minimoneybox.mvp.Login.LoginContract
import com.example.minimoneybox.mvp.Login.LoginPresenter
import com.example.minimoneybox.mvp.Profile.ProfileContract
import com.example.minimoneybox.mvp.Profile.ProfilePresenter
import dagger.Module
import dagger.Provides

//The application midel contract is an interface for ineracting with the model, retrieving model data and caching

//The mainActivity contract is an interface which contains the view methods for updating the view from the presenter


@Module
class FragmentModule {


    @Provides
    internal fun provideLoginPresenter(mymodelinteractor:ApplicationModelContract): LoginContract.Presenter {

        return LoginPresenter(mymodelinteractor)
    }
    @Provides
    internal fun provideProfilePresenter(mymodelinteractor: ApplicationModelContract): ProfileContract.Presenter {

        return ProfilePresenter(mymodelinteractor)
    }

    @Provides
    internal fun provideAccountPresenter(mymodelinteractor: ApplicationModelContract): AccountContract.Presenter {

        return AccountPresenter(mymodelinteractor)
    }

}