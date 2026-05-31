package com.kadawatcha.app.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kadawatcha.app.viewmodel.MainViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    username : String,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel()
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    PageTitle(
                        text = "Atlas",
                        textAlign = TextAlign.Start,
                        fontSize = 30.sp
                    )
                },
                actions = {
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings"
                        )
                    }
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
                    onClick = { /* TODO: Navigate to Home */ },
                    icon = Icons.Default.Home,
                    contentDescription = "home"
                )

                CustomNavItem(
                    selected = false,
                    onClick = {},
                    icon = Icons.Default.ChatBubbleOutline,
                    contentDescription = "chat"
                )

                CustomNavItem(
                    selected = false,
                    onClick = {},
                    icon = Icons.Default.LocalFireDepartment,
                    contentDescription = "friends"
                )
                CustomNavItem(
                    selected = false,
                    onClick = { /* TODO: Navigate to Profile */ },
                    icon = Icons.Default.AccountCircle,
                    contentDescription = "profile"
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