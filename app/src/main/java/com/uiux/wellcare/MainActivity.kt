package com.uiux.wellcare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.uiux.wellcare.ui.theme.WellcareTheme

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import com.uiux.wellcare.composables.onboarding.OnboardingScreen


const val LOGIN_ROUTE = "/login"
const val REGISTER_ROUTE = "/register"
const val FORGOT_PASSWORD_ROUTE = "/forgot-password"
const val RESET_PASSWORD_ROUTE = "/reset-password"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContent {
//            val navController = rememberNavController()
//
//            WellcareTheme {
//                NavHost(navController = navController, startDestination = LOGIN_ROUTE) {
//                    composable(LOGIN_ROUTE) { LoginScreen(navController) }
//                    composable(REGISTER_ROUTE) { RegisterScreen(navController) }
//                    composable(FORGOT_PASSWORD_ROUTE) { ForgotPasswordScreen(navController) }
//                    composable(RESET_PASSWORD_ROUTE) { ResetPasswordScreen(navController) }
//                }
//            }
//        }

        setContent {
            WellcareTheme {
                OnboardingScreen()
            }
        }
    }
}

