package com.kadawatcha.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kadawatcha.app.ui.NewAccountScreen
import com.kadawatcha.app.ui.PasswordScreen
import com.kadawatcha.app.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                val navController = rememberNavController() // Gestionnaire de nav
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "login", // Écran de départ
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("login") { // Route login
                            PasswordScreen(
                                onNavigateToCreateAccount = {
                                    navController.navigate("create_account") {
                                        // Évite de créer plusieurs fois l'écran s'il est déjà en haut
                                        launchSingleTop = true
                                        // Nettoie la pile pour éviter la surcharge mémoire
                                        popUpTo("login") { saveState = true }
                                    }
                                }
                            )
                        }
                        composable("create_account") { // Route création
                            NewAccountScreen(
                                onBackToLogin = {
                                    // popBackStack détruit l'écran actuel, libérant la RAM
                                    if (navController.previousBackStackEntry != null) {
                                        navController.popBackStack()
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

