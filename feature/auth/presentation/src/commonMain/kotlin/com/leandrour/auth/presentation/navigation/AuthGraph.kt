package com.leandrour.auth.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.leandrour.auth.presentation.email_verification.EmailVerificationRoot
import com.leandrour.auth.presentation.forgot_password.ForgotPasswordRoot
import com.leandrour.auth.presentation.login.LoginRoot
import com.leandrour.auth.presentation.register.RegisterRoot
import com.leandrour.auth.presentation.register_success.RegisterSuccessRoot
import com.leandrour.auth.presentation.reset_password.ResetPasswordRoot

fun NavGraphBuilder.authGraph(
    navController: NavController,
    onLoginSuccess: () -> Unit
) {
    navigation<AuthGraphRoutes.Graph>(
        startDestination = AuthGraphRoutes.Login
    ) {
        composable<AuthGraphRoutes.Login> {
            LoginRoot(
                onLoginSuccess = onLoginSuccess,
                onForgotPasswordClick = {
                    navController.navigate(AuthGraphRoutes.ForgotPassword)
                },
                onCreateAccountClick = {
                    navController.navigate(AuthGraphRoutes.Register) {
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<AuthGraphRoutes.Register> {
            RegisterRoot(
                onRegisterSuccess = {
                    navController.navigate(AuthGraphRoutes.RegisterSuccess(it))
                },
                onLoginClick = {
                    navController.navigate(AuthGraphRoutes.Login) {
                        popUpTo(AuthGraphRoutes.Register) {
                            inclusive = true
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
        composable<AuthGraphRoutes.RegisterSuccess> {
            RegisterSuccessRoot(
                onLoginClick = {
                    navController.navigate(AuthGraphRoutes.Login) {
                        popUpTo(AuthGraphRoutes.RegisterSuccess) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<AuthGraphRoutes.EmailVerification>(
            deepLinks = listOf(
                navDeepLink {
                    this.uriPattern = "http://10.0.2.2:8080/api/auth/verify?token={token}"
                },
                navDeepLink {
                    this.uriPattern = "chirp://10.0.2.2:8080/api/auth/verify?token={token}"
                }
            )
        ) {
            EmailVerificationRoot(
                onLoginClick = {
                    navController.navigate(AuthGraphRoutes.Login) {
                        popUpTo(AuthGraphRoutes.EmailVerification) {
                            inclusive = true
                        }
                    }
                },
                onCLoseClick = {
                    navController.navigate(AuthGraphRoutes.Login) {
                        popUpTo(AuthGraphRoutes.EmailVerification) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<AuthGraphRoutes.ForgotPassword> {
            ForgotPasswordRoot()
        }
        composable<AuthGraphRoutes.ResetPassword>(
            deepLinks = listOf(
                navDeepLink {
                    this.uriPattern = "http://10.0.2.2:8080/api/auth/reset-password?token={token}"
                },
                navDeepLink {
                    this.uriPattern = "chirp://10.0.2.2:8080/api/auth/reset-password?token={token}"
                }
            )
        ) {
            ResetPasswordRoot()
        }
    }
}