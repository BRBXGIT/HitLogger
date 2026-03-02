package com.example.hitlogger.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.hitlogger.presentation.common.theme.HitLoggerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HitLoggerTheme {
                val appComponent = (applicationContext as DaggerApp).appComponent
                NavGraph(appComponent.factory)
            }
        }
    }
}