package com.example.hitlogger.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HitEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val hitType: String,
    val date: String
)
