package com.kadawatcha.atlas.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kadawatcha.atlas.viewmodel.profileViewModel


@Composable
fun ProfileScreen(
    userId: String,
    modifier: Modifier = Modifier,
    viewModel: profileViewModel = viewModel(),
) {
    LaunchedEffect(userId) {
        viewModel.loadUserProfile(userId)
    }

    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Edit your profile",
            style = MaterialTheme.typography.headlineSmall
        )

        if (viewModel.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center,
            ){
                CircularProgressIndicator()
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        CustomInput(
            value = viewModel.username,
            onValueChange = { viewModel.username = it },
            label = "Username"
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomInput(
            value = password,
            onValueChange = { password = it },
            label = "Password"
        )


        CustomInput(
            value = repeatPassword,
            onValueChange = { repeatPassword = it },
            label = "Repeat password"
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                viewModel.saveUserProfile()
                viewModel.loadUserProfile(userId)

            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !viewModel.isLoading
        ) {
            Text("Sauvegarder")
        }
    }
}
