@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.hitlogger.presentation.home.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import com.example.hitlogger.presentation.common.theme.HitLoggerIcons
import com.example.hitlogger.presentation.common.theme.mColors
import com.example.hitlogger.presentation.common.theme.mDimens
import com.example.hitlogger.presentation.common.theme.mShapes
import com.example.hitlogger.presentation.common.theme.mTypography
import com.example.hitlogger.presentation.home.screen.HomeIntent
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarSelection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    date: String,
    scrollBehavior: TopAppBarScrollBehavior,
    onIntent: (HomeIntent) -> Unit,
) {
    val calendarDialogState = rememberUseCaseState()

    CalendarDialog(
        state = calendarDialogState,
        selection = CalendarSelection.Date { selectedDate ->
            onIntent(HomeIntent.OnSelectDate(selectedDate))
        }
    )

    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Left arrow
                NavIconButton(
                    iconRes = HitLoggerIcons.ArrowLeft,
                    onClick = { onIntent(HomeIntent.OnMinusDay) }
                )

                // Center block with date
                DateSelector(
                    date = date,
                    onClick = { calendarDialogState.show() }
                )

                // Right arrow
                NavIconButton(
                    iconRes = HitLoggerIcons.ArrowRight,
                    onClick = { onIntent(HomeIntent.OnPlusDay) }
                )
            }
        }
    )
}

@Composable
private fun NavIconButton(
    @DrawableRes iconRes: Int,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            tint = mColors.onSurface
        )
    }
}

@Composable
private fun DateSelector(
    date: String,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(mDimens.spacingSmall),
        modifier = Modifier
            .clip(mShapes.small)
            .clickable(onClick = onClick)
            .padding(vertical = mDimens.paddingSmall, horizontal = mDimens.paddingMedium)
    ) {
        Icon(
            painter = painterResource(id = HitLoggerIcons.Calendar),
            contentDescription = null,
            tint = mColors.primary
        )

        Text(
            text = date,
            style = mTypography.titleMedium,
            color = mColors.onSurface
        )
    }
}