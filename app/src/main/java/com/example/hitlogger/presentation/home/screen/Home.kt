@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.hitlogger.presentation.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.hitlogger.R
import com.example.hitlogger.domain.models.hit.Hit
import com.example.hitlogger.domain.models.hit.HitType
import com.example.hitlogger.presentation.common.theme.mColors
import com.example.hitlogger.presentation.common.theme.mDimens
import com.example.hitlogger.presentation.common.theme.mShapes
import com.example.hitlogger.presentation.common.theme.mTypography
import com.example.hitlogger.presentation.home.components.TopBar

@Composable
fun Home(homeVM: HomeVM) {
    val state by homeVM.state.collectAsStateWithLifecycle()

    val topBarScrollBehaviour = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = {
            TopBar(
                date = state.date,
                scrollBehavior = topBarScrollBehaviour,
                onIntent = homeVM::sendIntent
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(mDimens.spacingMedium),
            contentPadding = PaddingValues(vertical = mDimens.paddingMedium),
            modifier = Modifier
                .fillMaxSize()
                .background(mColors.background)
                .padding(innerPadding)
        ) {
            items(
                items = state.hits,
                key = { hit -> hit.id }
            ) { hit ->
                HitItem(hit)
            }
        }
    }
}

@Composable
private fun LazyItemScope.HitItem(hit: Hit) {
    Surface(
        shape = mShapes.small,
        color = mColors.surfaceContainerHigh,
        modifier = Modifier
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
                text = hit.date,
                style = mTypography.titleMedium
            )

            Text(
                text = stringResource(hit.hitType.toLabelRes()),
                style = mTypography.titleMedium,
                color = mColors.primary
            )
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