package com.example.hitlogger.presentation.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.hitlogger.presentation.common.di.MultiVMFactory
import com.example.hitlogger.presentation.home.screen.Home
import com.example.hitlogger.presentation.home.screen.HomeVM
import kotlinx.serialization.Serializable
import androidx.lifecycle.viewmodel.compose.viewModel

@Serializable
data object HomeRoute

fun NavGraphBuilder.home(
    factory: MultiVMFactory
) = composable<HomeRoute> {
    val homeVM = viewModel<HomeVM>(factory = factory)

    Home(homeVM)
}