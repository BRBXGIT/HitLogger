package com.example.hitlogger.di

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Module
object DispatchersModule {

    @Provides
    @Dispatcher(HitLoggerDispatcher.Io)
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Dispatcher(HitLoggerDispatcher.Default)
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @Dispatcher(HitLoggerDispatcher.Main)
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val hitLoggerDispatcher: HitLoggerDispatcher)

enum class HitLoggerDispatcher {
    Io, Default, Main
}