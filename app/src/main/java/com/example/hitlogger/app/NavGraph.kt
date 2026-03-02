package com.example.hitlogger.app

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.hitlogger.presentation.common.di.MultiVMFactory
import com.example.hitlogger.presentation.home.navigation.HomeRoute
import com.example.hitlogger.presentation.home.navigation.home
import com.example.hitlogger.presentation.start.navigation.StartRoute
import com.example.hitlogger.presentation.start.navigation.start

@Composable
fun NavGraph(
    vmsFactory: MultiVMFactory
) {
    val navController = rememberNavController()

    val context = LocalContext.current
    val startDestination = remember {
        if (context.hasBluetoothPermissions()) HomeRoute else StartRoute
    }
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        home(vmsFactory)

        start(navController)
    }
}

private fun Context.hasBluetoothPermissions(): Boolean {
    val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        arrayOf(Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT)
    } else {
        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
    }
    return permissions.all {
        ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }
}