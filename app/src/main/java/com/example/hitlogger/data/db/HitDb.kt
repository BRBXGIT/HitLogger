package com.example.hitlogger.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [HitEntity::class],
    version = 1
)
abstract class HitDb: RoomDatabase() {

    abstract fun hitDao(): HitDao
}