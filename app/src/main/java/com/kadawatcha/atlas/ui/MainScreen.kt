package com.kadawatcha.atlas.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kadawatcha.atlas.ui.theme.AppTheme
import com.kadawatcha.atlas.viewmodel.MainViewModel
import com.kadawatcha.atlas.viewmodel.MainViewModelFactory


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    username: String,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel(factory = MainViewModelFactory)
) {

    val isDarkTheme by viewModel.isDarkTheme.collectAsStateWithLifecycle()

    AppTheme(
        darkTheme = isDarkTheme,
        dynamicColor = true
    ) { // false pour utiliser nos couleurs personnalisées
        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        PageTitle(
                            text = "Atlas",
                            color = MaterialTheme.colorScheme.secondary,
                            textAlign = TextAlign.Start,
                            fontSize = 30.sp,
                        )
                    },
                    actions = {
                        MainTopMenu(
                            isDarkTheme = isDarkTheme,
                            onToggleTheme = { viewModel.toggleTheme() },
                            onLogoutClick = {
                                onLogout()
                            }
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            },


            bottomBar = {
                NavigationBar(
                    windowInsets = NavigationBarDefaults.windowInsets,
                ) {

                    CustomNavItem(
                        selected = true,
                        icon = Icons.Default.Home,
                        contentDescription = "home",
                        onClick = { /* TODO: Navigate to Home */ }
                    )

                    CustomNavItem(
                        selected = false,
                        icon = Icons.Default.ChatBubbleOutline,
                        contentDescription = "chat",
                        onClick = {}
                    )

                    CustomNavItem(
                        selected = false,
                        icon = Icons.Default.LocalFireDepartment,
                        contentDescription = "friends",
                        onClick = {}
                    )
                    CustomNavItem(
                        selected = false,
                        icon = Icons.Default.AccountCircle,
                        contentDescription = "profile",
                        onClick = { /* TODO: Navigate to Profile */ }
                    )

                }
            }


        ) { innerPadding ->

            Surface( // obligé en dessous des btns sinon ça cacherai
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                color = MaterialTheme.colorScheme.background
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.height(8.dp))

                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Welcome to Kad's app $username ",
                            color = MaterialTheme.colorScheme.secondary,
                            textAlign = TextAlign.Start
                        )

                    }

                }
            }
        }
    }
}

@Composable
private fun MainTopMenu(
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit,
    onLogoutClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings"
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            shape = RoundedCornerShape(16.dp),
            // border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
        ) {
            DropdownMenuItem(
                text = { Text("Settings") },
                onClick = { expanded = false },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = null
                    )
                }
            )

            DropdownMenuItem(
                text = {
                    ThemeToggleRow(
                        isDark = isDarkTheme,
                        onToggle = onToggleTheme
                    )
                },
                onClick = onToggleTheme,
                leadingIcon = {
                    Icon(
                        imageVector = if (isDarkTheme) Icons.Default.DarkMode else Icons.Default.WbSunny,
                        contentDescription = null
                    )
                }
            )

            HorizontalDivider(modifier = Modifier.padding(horizontal = 12.dp))

            DropdownMenuItem(
                text = { Text("Logout", color = MaterialTheme.colorScheme.error) },
                onClick = {
                    expanded = false
                    onLogoutClick()
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Logout,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            )
        }
    }
}


@Composable
fun ThemeToggleRow(
    isDark: Boolean,
    onToggle: () -> Unit // Une fonction "callback"
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = if (isDark) "Dark" else "Light")
        Spacer(Modifier.width(8.dp))
        Switch(checked = isDark, onCheckedChange = { onToggle() })
    }
}
