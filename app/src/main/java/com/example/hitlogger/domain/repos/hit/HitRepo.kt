package com.example.hitlogger.domain.repos.hit

import com.example.hitlogger.domain.models.hit.Hit
import kotlinx.coroutines.flow.Flow

interface HitRepo {

    fun getHits(): Flow<List<Hit>>

    suspend fun upsertHit(hit: Hit)
}