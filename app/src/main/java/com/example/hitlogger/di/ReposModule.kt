package com.example.hitlogger.di

import com.example.hitlogger.domain.repos.hit.HitRepo
import com.example.hitlogger.domain.repos.hit.HitRepoImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface ReposModule {

    @Binds
    @Singleton
    fun bindHitRepo(impl: HitRepoImpl): HitRepo
}