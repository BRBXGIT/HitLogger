package com.example.hitlogger.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.hitlogger.presentation.common.theme.HitLoggerTheme
import com.example.hitlogger.presentation.home.screen.Home
import com.example.hitlogger.presentation.home.screen.HomeVM

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HitLoggerTheme {
                val appComponent = (applicationContext as DaggerApp).appComponent
                val homeVM by viewModels<HomeVM> { appComponent.factory }

                Home(homeVM)
            }
        }
    }
}