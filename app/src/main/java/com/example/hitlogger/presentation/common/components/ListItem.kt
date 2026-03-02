package com.example.hitlogger.presentation.common.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.hitlogger.presentation.common.theme.mColors
import com.example.hitlogger.presentation.common.theme.mDimens
import com.example.hitlogger.presentation.common.theme.mShapes
import com.example.hitlogger.presentation.common.theme.mTypography

@Composable
fun LazyItemScope.ListItem(
    modifier: Modifier = Modifier,
    leadingText: String,
    trailingText: String? = null,
) {
    Surface(
        shape = mShapes.small,
        color = mColors.surfaceContainerHigh,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = mDimens.paddingMedium)
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
                style = mTypography.titleMedium
            )

            if (trailingText != null) {
                Text(
                    text = trailingText,
                    style = mTypography.titleMedium,
                    color = mColors.primary
                )
            }
        }
    }
}