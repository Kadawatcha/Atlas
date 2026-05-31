package com.kadawatcha.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kadawatcha.app.ui.LoginScreen
import com.kadawatcha.app.ui.MainScreen
import com.kadawatcha.app.ui.NewAccountScreen
import com.kadawatcha.app.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "login",
                    modifier = Modifier.fillMaxSize()
                ) {
                    composable("login") {
                        LoginScreen(
                            onLoginSuccess = { loggedInUsername ->
                                navController.navigate("mainpage/$loggedInUsername"){
                                    popUpTo("login") {inclusive = true}
                                    launchSingleTop = true
                                }
                            },
                            
                            onNavigateToCreateAccount = {
                                // launchSingleTop évite d'empiler plusieurs fois la même page si on clique vite
                                navController.navigate("create_account") {
                                    launchSingleTop = true
                                }
                            }
                        )
                    }
                    composable("create_account") {
                        NewAccountScreen(
                            onAccountCreated = {
                                navController.popBackStack("login", inclusive = false)
                            },
                            onBackToLogin = {
                                if (navController.previousBackStackEntry != null) {
                                    navController.popBackStack()
                                }
                            }
                        )
                    }
                    composable("mainpage/{username}") { backStackEntry ->
                        val username = backStackEntry.arguments?.getString("username") ?: ""
                        MainScreen(username = username)
                    }
                }
            }
        }
    }
}

