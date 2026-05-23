package com.kadawatcha.app.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PasswordScreen(
    modifier: Modifier = Modifier,
    onNavigateToCreateAccount: () -> Unit
) {
    var username by rememberSaveable { mutableStateOf("") }
    var emptyUser by rememberSaveable { mutableStateOf(false) }
    var usernameError by rememberSaveable { mutableStateOf(false) }

    var password by rememberSaveable { mutableStateOf("") }
    var passwordError by rememberSaveable { mutableStateOf(false) }
    var emptyPassword by rememberSaveable { mutableStateOf(false) }

    // Utilisation d'une Surface pour un fond doux
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
                // Titre plus moderne
                Text(
                    text = "FaceGrook",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary,
                    letterSpacing = (-1).sp
                )
                
                Text(
                    text = "Welcome back!",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.secondary
                )

                Spacer(Modifier.height(32.dp))

                // Carte pour regrouper les champs
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth(0.9f),
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
                                usernameError = false
                                emptyUser = false
                            },
                            label = "Username",
                            leadingIcon = Icons.Default.Person,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            isError = usernameError || emptyUser
                        )

                        Spacer(Modifier.height(16.dp))

                        CustomInput(
                            value = password,
                            onValueChange = {
                                password = it
                                passwordError = false
                                emptyPassword = false
                            },
                            label = "Password",
                            leadingIcon = Icons.Default.Lock,
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            isError = passwordError || emptyPassword )

                        Spacer(Modifier.height(24.dp))

                        Button(
                            onClick = {
                                val trimmedPassword = password.trim()
                                val trimmedUsername = username.trim()
                                passwordError = false
                                emptyPassword = false
                                usernameError = false
                                emptyUser = false

                                when {
                                    trimmedUsername.isEmpty() -> emptyUser = true
                                    trimmedPassword.isEmpty() -> emptyPassword = true
                                    trimmedUsername == "User1" -> {
                                        if (trimmedPassword == "Pass2") {
                                            usernameError = false
                                        } else {
                                            passwordError = true
                                        }
                                    }
                                    else -> usernameError = true
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text("Sign In", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        }

                        // Messages d'erreurs intégrés plus proprement
                        if (emptyUser || emptyPassword || usernameError || passwordError) {
                            Spacer(Modifier.height(12.dp))
                            Text(
                                text = when {
                                    emptyUser -> "Username is required"
                                    emptyPassword -> "Password is required"
                                    usernameError -> "User not found"
                                    passwordError -> "Incorrect password"
                                    else -> ""
                                },
                                color = MaterialTheme.colorScheme.error,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }

            // Bouton de navigation discret en bas
            TextButton(
                onClick = onNavigateToCreateAccount,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp)
            ) {
                Text(
                    "Don't have an account ? Sign Up",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun PageTitle(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
fun CustomInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isError: Boolean = false,
    leadingIcon: ImageVector? = null,
    supportingText: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = leadingIcon?.let {
            { Icon(imageVector = it, contentDescription = null, modifier = Modifier.size(20.dp)) }
        },
        isError = isError,
        supportingText = supportingText,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            errorBorderColor = MaterialTheme.colorScheme.error
        )
    )
}
