package com.example.hitlogger.di

import android.content.Context
import com.example.hitlogger.presentation.common.di.MultiVMFactory
import com.example.hitlogger.presentation.common.di.VMSModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        HitDbModule::class,
        ReposModule::class,
        DispatchersModule::class,
        VMSModule::class
    ]
)
interface AppComponent {

    val factory: MultiVMFactory

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}