package com.example.hitlogger.presentation.start.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.hitlogger.presentation.start.screen.Start
import kotlinx.serialization.Serializable

@Serializable
data object StartRoute

fun NavGraphBuilder.start(navController: NavController) = composable<StartRoute> {
    Start(navController)
}