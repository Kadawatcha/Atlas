package com.kadawatcha.app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NewAccountScreen(
    onBackToLogin: () -> Unit // Callback: action vide (Unit) vers login
) {
    var username by rememberSaveable { mutableStateOf( "") }
    var password by rememberSaveable {mutableStateOf("")}
    var repeatpassword by rememberSaveable {mutableStateOf("")}

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PageTitle(
            text = "FaceGrook\nCreate account",
            )

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

        Row(
            modifier = Modifier.fillMaxWidth(0.8f),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = onBackToLogin
            ) {
                Text("Log in")
            }
            Button(
                modifier = Modifier.weight(1f),
                onClick = { /* TODO: Implement account creation */ }
            ) {
                Text("Create account")
            }
        }
    }
}



