@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.hitlogger.presentation.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.hitlogger.R
import com.example.hitlogger.domain.models.bluetooth.BluetoothDevice
import com.example.hitlogger.presentation.common.components.LC
import com.example.hitlogger.presentation.common.components.ListItem
import com.example.hitlogger.presentation.home.screen.HomeIntent

@Composable
fun DevicesBS(
    scannedDevices: List<BluetoothDevice>,
    pairedDevices: List<BluetoothDevice>,
    onIntent: (HomeIntent) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = { onIntent(HomeIntent.ToggleDevicesBS) }
    ) {
        LC {
            item(
                key = "scanned_devices"
            ) {
                Text(
                    text = stringResource(R.string.scanned_devices)
                )
            }

            items(
                items = scannedDevices,
                key = { device -> device.address }
            ) {
                ListItem(
                    leadingText = it.name ?: it.address,
                    modifier = Modifier.clickable(onClick = {})
                )
            }

            item(
                key = "paired_devices"
            ) {
                Text(
                    text = stringResource(R.string.paired_devices)
                )
            }

            items(
                items = pairedDevices,
                key = { device -> device.address }
            ) {
                ListItem(
                    leadingText = it.name ?: it.address,
                    modifier = Modifier.clickable(onClick = {})
                )
            }
        }
    }
}