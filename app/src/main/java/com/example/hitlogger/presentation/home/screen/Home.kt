@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.hitlogger.presentation.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
fun Home(homeVM: HomeVM) {
    val state by homeVM.state.collectAsStateWithLifecycle()

    val topBarScrollBehaviour = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        floatingActionButton = { DevicesFab(homeVM::sendIntent) },
        topBar = {
            TopBar(
                date = state.date,
                scrollBehavior = topBarScrollBehaviour,
                onIntent = homeVM::sendIntent
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        if (state.isDevicesBSVisible) {
            DevicesBS(
                scannedDevices = state.scannedDevices,
                pairedDevices = state.pairedDevices,
                onIntent = homeVM::sendIntent
            )
        }

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

private fun HitType.toLabelRes(): Int {
    return when(this) {
        HitType.Stab -> R.string.cut_hit_type
        HitType.Slash -> R.string.thrust_hit_type
        HitType.Unknown -> R.string.unknown_hit_type
    }
}