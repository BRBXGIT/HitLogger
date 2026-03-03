package com.example.hitlogger.presentation.common.components

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.example.hitlogger.presentation.common.theme.mColors
import com.example.hitlogger.presentation.common.theme.mDimens
import com.example.hitlogger.presentation.common.theme.mShapes
import com.example.hitlogger.presentation.common.theme.mTypography

@Composable
fun LazyItemScope.ListItem(
    onClick: () -> Unit = {},
    leadingText: String,
    trailingText: String? = null,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = mDimens.paddingMedium)
            .clip(mShapes.small)
            .background(mColors.surfaceContainerHigh)
            .clickable(
                onClick = onClick,
                indication = if (onClick == {}) null else LocalIndication.current,
                interactionSource = remember { MutableInteractionSource() }
            )
            .animateItem()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(mDimens.paddingMedium)
        ) {
            Text(
                text = leadingText,
                style = mTypography.bodyLarge
            )

            if (trailingText != null) {
                Text(
                    text = trailingText,
                    style = mTypography.bodyLarge,
                    color = mColors.primary
                )
            }
        }
    }
}