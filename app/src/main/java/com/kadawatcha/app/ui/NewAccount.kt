package com.kadawatcha.app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun NewAccountScreen(
    onBackToLogin: () -> Unit,
) {
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var repeatpassword by rememberSaveable { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PageTitle(text = "FakeGrook\nCreate account")

            Spacer(Modifier.height(25.dp))

            CustomInput(
                value = username,
                onValueChange = { username = it },
                label = "Username"
            )

            Spacer(Modifier.height(16.dp))

            CustomInput(
                value = password,
                onValueChange = { password = it },
                label = "Password",
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(Modifier.height(16.dp))

            CustomInput(
                value = repeatpassword,
                onValueChange = { repeatpassword = it },
                label = "Repeat Password",
                visualTransformation = PasswordVisualTransformation(),
                isError = password.isNotEmpty() && repeatpassword.isNotEmpty() && password != repeatpassword
            )

            Spacer(Modifier.height(24.dp))

            Button(
                modifier = Modifier.fillMaxWidth(0.8f),
                onClick = { /* TODO */ }
            ) {
                Text("Create account")
            }
        }

        Button(
            onClick = onBackToLogin,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Text("<- Back to login")
        }
    }
}
