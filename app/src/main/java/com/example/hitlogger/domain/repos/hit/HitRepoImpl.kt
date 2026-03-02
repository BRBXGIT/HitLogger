package com.example.hitlogger.domain.repos.hit

import com.example.hitlogger.data.db.HitDao
import com.example.hitlogger.data.db.HitEntity
import com.example.hitlogger.domain.models.hit.Hit
import com.example.hitlogger.domain.models.hit.HitType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HitRepoImpl @Inject constructor(
    private val hitDao: HitDao
): HitRepo {

    override fun getHits(): Flow<List<Hit>> {
        return hitDao.getHits().map { list ->
            list.map { entity -> entity.toHit() }
        }
    }

    override suspend fun upsertHit(hit: Hit) =
        hitDao.upsertHit(hit.toEntity())

    // --- Mappers ---
    private fun Hit.toEntity(): HitEntity {
        return HitEntity(
            hitType = this.hitType.name,
            date = this.date
        )
    }

    private fun HitEntity.toHit(): Hit {
        return Hit(
            id = this.id,
            hitType = this.hitType.toHitType(),
            date = this.date
        )
    }

    private fun String.toHitType(): HitType {
        return when(this) {
            "Stab" -> HitType.Stab
            "Slash" -> HitType.Slash
            else -> HitType.Unknown
        }
    }
}