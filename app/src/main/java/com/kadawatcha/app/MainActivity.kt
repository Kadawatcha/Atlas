package com.kadawatcha.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
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
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "login",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        composable("login") {
                            LoginScreen(
                                onLoginSuccess = {
                                    navController.navigate("mainpage") {
                                        // On vide la pile pour ne pas pouvoir revenir au login avec "Retour"
                                        popUpTo("login") { inclusive = true }
                                    }
                                },
                                onNavigateToCreateAccount = {
                                    navController.navigate("create_account")
                                }
                            )
                        }
                        composable("create_account") {
                            NewAccountScreen(
                                onBackToLogin = {
                                    navController.popBackStack()
                                }
                            )
                        }
                        composable("mainpage") {
                            MainScreen()
                        }
                    }
                }
            }
        }
    }
}

