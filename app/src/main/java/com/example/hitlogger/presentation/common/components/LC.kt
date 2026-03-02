package com.example.hitlogger.presentation.common.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.hitlogger.presentation.common.theme.mDimens

@Composable
fun LC(
    modifier: Modifier = Modifier,
    content: LazyListScope.() -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(mDimens.spacingMedium),
        contentPadding = PaddingValues(vertical = mDimens.paddingMedium),
        modifier = modifier,
        content = content
    )
}