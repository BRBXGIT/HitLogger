@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)

package com.example.hitlogger.presentation.home.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.hitlogger.R
import com.example.hitlogger.domain.models.hit.HitType
import com.example.hitlogger.presentation.common.components.LC
import com.example.hitlogger.presentation.common.components.ListItem
import com.example.hitlogger.presentation.common.theme.mColors
import com.example.hitlogger.presentation.home.components.DevicesBS
import com.example.hitlogger.presentation.home.components.DevicesFab
import com.example.hitlogger.presentation.home.components.TopBar

@Composable
fun Home(
    homeVM: HomeVM,
    context: Context = LocalContext.current
) {
    val state by homeVM.state.collectAsStateWithLifecycle()

    val topBarScrollBehaviour = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        floatingActionButton = { DevicesFab(homeVM::sendIntent) },
        topBar = {
            TopBar(
                date = state.dateString,
                scrollBehavior = topBarScrollBehaviour,
                onIntent = homeVM::sendIntent
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        LaunchedEffect(state.errorMessage) {
            state.errorMessage?.let { message ->
                Toast.makeText(
                    context,
                    message,
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        LaunchedEffect(state.isConnected) {
            if (state.isConnected) {
                Toast.makeText(
                    context,
                    "Connected!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        if (state.isDevicesBSVisible) {
            DevicesBS(
                scannedDevices = state.scannedDevices,
                pairedDevices = state.pairedDevices,
                onIntent = homeVM::sendIntent
            )
        }

        if (state.isConnecting) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.6f)),
                contentAlignment = Alignment.Center
            ) {
                ContainedLoadingIndicator()
            }
        } else {
            LC(
                modifier = Modifier
                    .fillMaxSize()
                    .background(mColors.background)
                    .padding(innerPadding)
            ) {
                items(
                    items = state.hits,
                    key = { hit -> hit.id }
                ) { hit ->
                    ListItem(
                        leadingText = hit.date,
                        trailingText = stringResource(hit.hitType.toLabelRes())
                    )
                }
            }
        }
    }
}

private fun HitType.toLabelRes(): Int {
    return when(this) {
        HitType.Stab -> R.string.cut_hit_type
        HitType.Slash -> R.string.thrust_hit_type
        HitType.Unknown -> R.string.unknown_hit_type
    }
}