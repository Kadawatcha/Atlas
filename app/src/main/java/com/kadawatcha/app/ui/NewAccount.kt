package com.kadawatcha.app.ui

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
                value = repeatPassword,
                onValueChange = { repeatPassword = it },
                label = "Repeat Password",
                visualTransformation = PasswordVisualTransformation(),
                isError = password.isNotEmpty() && repeatPassword.isNotEmpty() && password != repeatPassword
            )

            Spacer(Modifier.height(24.dp))

            Button(
                modifier = Modifier.fillMaxWidth(0.8f),
                onClick = {
                    var trimmedUsername = username.trim()
                    var trimmedPassword = password.trim()
                    var trimmedRepeatPassword = repeatPassword.trim()

                    usernameEmpty = false
                    usernameError = false
                    usernameAlreadyTaken = false

                    passwordError = false
                    passwordEmpty = false

                    repeatEmpty = false
                    repeatBad = false

                    when {
                        trimmedUsername.isEmpty() -> {
                            usernameEmpty = true
                        }

                        trimmedPassword.isEmpty() -> {
                            usernameEmpty = true
                        }

                        trimmedRepeatPassword.isEmpty() -> {
                            repeatEmpty = true
                        }



                    }


                }
            ) {
                Text("Create account")
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
