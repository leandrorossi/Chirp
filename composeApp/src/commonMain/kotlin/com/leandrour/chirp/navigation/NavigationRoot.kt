package com.leandrour.chirp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost

import androidx.navigation.compose.rememberNavController
import com.leandrour.auth.presentation.navigation.AuthGraphRoutes
import com.leandrour.auth.presentation.navigation.authGraph

@Composable
fun NavigationRoot() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AuthGraphRoutes.Graph
    ) {
        authGraph(
            navController = navController,
            onLoginSuccess = {

            }
        )
    }
}