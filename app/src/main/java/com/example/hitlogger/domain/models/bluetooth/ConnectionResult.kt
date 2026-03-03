package com.example.hitlogger.domain.models.bluetooth

sealed interface ConnectionResult {

    data object ConnectionEstablished: ConnectionResult
    data class Error(val message: String): ConnectionResult
}