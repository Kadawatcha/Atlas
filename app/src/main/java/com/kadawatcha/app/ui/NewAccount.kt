package com.kadawatcha.app.ui

import android.graphics.Color
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
    var usernameError by rememberSaveable { mutableStateOf(false) }
    var usernameEmpty by rememberSaveable { mutableStateOf(false) }
    var usernameAlreadyTaken by rememberSaveable { mutableStateOf(false) }


    var password by rememberSaveable { mutableStateOf("") }
    var passwordError by rememberSaveable { mutableStateOf(false) }
    var passwordEmpty by rememberSaveable { mutableStateOf(false) }
    var passwordShort by rememberSaveable {mutableStateOf(false) }

    var repeatPassword by rememberSaveable { mutableStateOf("") }
    var repeatEmpty by rememberSaveable { mutableStateOf(false) }
    var repeatBad by rememberSaveable { mutableStateOf(false) }

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
                onValueChange = { 
                    username = it
                    usernameEmpty = false
                    usernameError = false
                },
                label = "Username",
                isError = usernameEmpty || usernameError || usernameAlreadyTaken
            )

            Spacer(Modifier.height(16.dp))

            CustomInput(
                value = password,
                onValueChange = { 
                    password = it
                    passwordEmpty = false
                    passwordError = false
                },
                label = "Password",
                visualTransformation = PasswordVisualTransformation(),
                isError = passwordEmpty || passwordError || (password.isNotEmpty() && password.length < 8),
                supportingText = {
                    if (password.isNotEmpty() && password.length < 8) {
                        Text(text = "Too short",
                            color =  androidx.compose.ui.graphics.Color.Magenta)
                    }
                }
            )

            Spacer(Modifier.height(16.dp))

            CustomInput(
                value = repeatPassword,
                onValueChange = { 
                    repeatPassword = it
                    repeatEmpty = false
                    repeatBad = false
                },
                label = "Repeat Password",
                visualTransformation = PasswordVisualTransformation(),
                isError = repeatEmpty || repeatBad
            )

            Spacer(Modifier.height(24.dp))

            Button(
                modifier = Modifier.fillMaxWidth(0.8f),
                onClick = {
                    val trimmedUsername = username.trim()
                    val trimmedPassword = password.trim()
                    val trimmedRepeatPassword = repeatPassword.trim()

                    // Reset des erreurs
                    usernameEmpty = false
                    usernameError = false
                    usernameAlreadyTaken = false
                    passwordError = false
                    passwordEmpty = false
                    repeatEmpty = false
                    repeatBad = false

                    when {
                        trimmedUsername.isEmpty() -> usernameEmpty = true
                        trimmedPassword.isEmpty() -> passwordEmpty = true
                        trimmedPassword.length < 8 -> {
                            // nada couleur et txt auto
                        }
                        trimmedRepeatPassword.isEmpty() -> repeatEmpty = true
                        trimmedPassword != trimmedRepeatPassword -> repeatBad = true
                        else -> {
                            // Ici, tout est bon !
                            // Logique de création de compte
                        }
                    }
                }
            ) {
                Text("Create account")
            }

            Spacer(Modifier.height(16.dp))

            // Affichage des messages d'erreurs
            if (usernameEmpty) {
                Text("Please enter a username", color = androidx.compose.ui.graphics.Color.Red)
            } else if (passwordEmpty) {
                Text("Please enter a password", color = androidx.compose.ui.graphics.Color.Red)
            } else if (repeatEmpty) {
                Text("Please repeat your password", color = androidx.compose.ui.graphics.Color.Red)
            } else if (repeatBad) {
                Text("Passwords do not match", color = androidx.compose.ui.graphics.Color.Red)
            }
        }

        TextButton(
            onClick = onBackToLogin,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Text("<- Back to login")
        }
    }
}
