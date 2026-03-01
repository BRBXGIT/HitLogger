package com.example.hitlogger.domain.repos

import com.example.hitlogger.domain.models.Hit
import kotlinx.coroutines.flow.Flow

interface HitRepo {

    fun getHits(): Flow<List<Hit>>

    suspend fun upsertHit(hit: Hit)
}