package com.example.hitlogger.presentation.start.screen

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.hitlogger.R
import com.example.hitlogger.presentation.common.theme.mDimens
import com.example.hitlogger.presentation.home.navigation.HomeRoute
import com.example.hitlogger.presentation.start.navigation.StartRoute

@Composable
fun Start(navController: NavController) {
    val notificationsPermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { result ->
            if (result.values.all { it }) {
                navController.navigate(HomeRoute) {
                    popUpTo(StartRoute) { inclusive = true }
                }
            }
        }
    )
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = mDimens.paddingMedium),
    ) {
        Button(
            onClick = {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    notificationsPermissionResultLauncher.launch(
                        input = arrayOf(
                            Manifest.permission.BLUETOOTH_SCAN,
                            Manifest.permission.BLUETOOTH_CONNECT
                        )
                    )
                }
            }
        ) {
            Text(
                text = stringResource(R.string.permissions_label)
            )
        }
    }
}