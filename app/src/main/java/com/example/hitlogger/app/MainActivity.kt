package com.example.hitlogger.app

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import com.example.hitlogger.presentation.common.theme.HitLoggerTheme
import com.example.hitlogger.presentation.home.screen.Home
import com.example.hitlogger.presentation.home.screen.HomeVM

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HitLoggerTheme {
                val notificationsPermissionResultLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestMultiplePermissions(),
                    onResult = { _ -> }
                )
                LaunchedEffect(Unit) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        notificationsPermissionResultLauncher.launch(
                            input = arrayOf(
                                Manifest.permission.BLUETOOTH_SCAN,
                                Manifest.permission.BLUETOOTH_CONNECT
                            )
                        )
                    }
                }

                val appComponent = (applicationContext as DaggerApp).appComponent
                val homeVM by viewModels<HomeVM> { appComponent.factory }

                Home(homeVM)
            }
        }
    }
}