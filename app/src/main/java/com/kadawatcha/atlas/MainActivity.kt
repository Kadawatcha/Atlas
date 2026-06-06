package com.kadawatcha.atlas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kadawatcha.atlas.ui.LoginScreen
import com.kadawatcha.atlas.ui.MainScreen
import com.kadawatcha.atlas.ui.NewAccountScreen
import com.kadawatcha.atlas.ui.theme.AppTheme
import com.kadawatcha.atlas.utils.SessionManager
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                val navController = rememberNavController()

                // recup datas
                val context = LocalContext.current
                val sessionManager = remember { SessionManager(context) }
                val loggedInUser by sessionManager.loggedInUser.collectAsState(null)

                // check si data
                val startDest = if (loggedInUser != null) {
                    "mainpage/$loggedInUser" // S'il y a un pseudo: main page
                } else {
                    "login"                  // Sinon on demande de se connecter
                }

                NavHost(
                    navController = navController,
                    startDestination = startDest,
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
                        MainScreen(
                            username = username,
                            onLogout = {
                                lifecycleScope.launch {
                                    sessionManager.clearSession()
                                }
                                navController.navigate("login") {
                                    popUpTo(0) { inclusive = true }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

