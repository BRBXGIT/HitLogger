package com.example.hitlogger.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface HitDao {

    @Query("SELECT * FROM hitentity")
    fun getHits(): Flow<List<HitEntity>>

    @Upsert
    suspend fun upsertHit(hitEntity: HitEntity)
}