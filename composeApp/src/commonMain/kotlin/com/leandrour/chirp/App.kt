package com.leandrour.chirp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.leandrour.auth.presentation.navigation.AuthGraphRoutes
import com.leandrour.chirp.navigation.DeepLinkListener
import com.leandrour.chirp.navigation.NavigationRoot
import com.leandrour.core.designsystem.theme.ChirpTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App(
    onAuthenticationChecked: () -> Unit = { },
    viewModel: MainViewModel = koinViewModel()
) {
    val navController = rememberNavController()
    DeepLinkListener(navController)

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.isCheckingAuth) {
        if (!state.isCheckingAuth) {
            onAuthenticationChecked()
        }
    }

    ChirpTheme {
        NavigationRoot(
            navController,
            startDestination = if (state.isLoggedIn) {
                Unit
            } else {
                AuthGraphRoutes.Graph
            }
        )
    }
}