package com.example.hitlogger.di

import android.content.Context
import androidx.room.Room
import com.example.hitlogger.data.HitDb
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object HitDbModule {

    @Provides
    @Singleton
    fun provideHitDb(context: Context): HitDb {
        return Room.databaseBuilder(
            context = context,
            klass = HitDb::class.java,
            name = "HitDb"
        ).build()
    }

    @Provides
    @Singleton
    fun provideHitDao(hitDb: HitDb) = hitDb.hitDao()
}