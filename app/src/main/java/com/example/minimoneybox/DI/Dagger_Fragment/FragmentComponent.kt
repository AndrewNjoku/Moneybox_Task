package com.example.minimoneybox.DI.Dagger_Fragment

import com.example.minimoneybox.Dagger_Login.FragmentModule
import com.example.minimoneybox.fragments.AccountFragment
import com.example.minimoneybox.fragments.LoginActivityFragment
import com.example.minimoneybox.fragments.ProfileFragment
import dagger.Subcomponent



@Subcomponent(modules = [FragmentModule::class])
interface FragmentComponent {

    fun inject(loginActivityFragment: LoginActivityFragment)

    fun inject(profileFragment: ProfileFragment)

    fun inject(accountFragment: AccountFragment)
}
