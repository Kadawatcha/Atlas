package com.kadawatcha.app.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Join Us",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary,
                    letterSpacing = (-1).sp
                )

                Text(
                    text = "Create your account",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.secondary
                )

                Spacer(Modifier.height(32.dp))

                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(24.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CustomInput(
                            value = username,
                            onValueChange = {
                                username = it
                                usernameEmpty = false
                                usernameError = false
                            },
                            label = "Username",
                            leadingIcon = Icons.Default.Person,
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
                            leadingIcon = Icons.Default.Lock,
                            visualTransformation = PasswordVisualTransformation(),
                            isError = passwordEmpty || passwordError || (password.isNotEmpty() && password.length < 8),
                            supportingText = if (password.isNotEmpty() && password.length < 8) {
                                { Text(text = "Must be 8+ characters") }
                            } else null
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
                            leadingIcon = Icons.Default.Lock,
                            visualTransformation = PasswordVisualTransformation(),
                            isError = repeatEmpty || repeatBad
                        )

                        Spacer(Modifier.height(24.dp))

                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = RoundedCornerShape(16.dp),
                            onClick = {
                                val trimmedUsername = username.trim()
                                val trimmedPassword = password.trim()
                                val trimmedRepeatPassword = repeatPassword.trim()

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
                                    trimmedPassword.length < 8 -> {}
                                    trimmedRepeatPassword.isEmpty() -> repeatEmpty = true
                                    trimmedPassword != trimmedRepeatPassword -> repeatBad = true
                                    else -> {
                                        // Succès !
                                    }
                                }
                            }
                        ) {
                            Text("Create Account", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        }

                        if (usernameEmpty || passwordEmpty || repeatEmpty || repeatBad) {
                            Spacer(Modifier.height(12.dp))
                            Text(
                                text = when {
                                    usernameEmpty -> "Username is required"
                                    passwordEmpty -> "Password is required"
                                    repeatEmpty -> "Please repeat password"
                                    repeatBad -> "Passwords do not match"
                                    else -> ""
                                },
                                color = MaterialTheme.colorScheme.error,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }

            TextButton(
                onClick = onBackToLogin,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp)
            ) {
                Text(
                    "Already have an account ? Sign In",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
