package com.example.hitlogger.presentation.common.di

import androidx.lifecycle.ViewModel
import com.example.hitlogger.presentation.home.screen.HomeVM
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface VMSModule {

    @Binds
    @IntoMap
    @VMKey(HomeVM::class)
    fun provideTodoViewModel(homeVM: HomeVM): ViewModel
}