package com.kadawatcha.app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kadawatcha.app.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = viewModel(),
    onLoginSuccess: () -> Unit,
    onNavigateToCreateAccount: () -> Unit,
) {
    val scrollState = rememberScrollState()

    LaunchedEffect(viewModel.loginSuccess) {
        if (viewModel.loginSuccess) {
            onLoginSuccess()
        }
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(modifier = Modifier.fillMaxSize().imePadding()) {
            // Zone scrollable pour le contenu (Titre + Champs) centrée
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 100.dp) // Espace pour ne pas chevaucher les boutons fixes
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = maxHeight)
                        .verticalScroll(scrollState),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    PageTitle(text = "Atlas")

                Text(
                    text = "Welcome back!",
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
                            value = viewModel.username,
                            onValueChange = {
                                viewModel.username = it
                                viewModel.usernameError = false
                                viewModel.emptyUser = false
                            },
                            label = "Username",
                            modifier = Modifier.semantics {
                                contentType = ContentType.Username
                            },
                            leadingIcon = Icons.Default.Person,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            isError = viewModel.usernameError || viewModel.emptyUser
                        )

                        Spacer(Modifier.height(16.dp))

                        CustomInput(
                            value = viewModel.password,
                            onValueChange = {
                                viewModel.password = it
                                viewModel.passwordError = false
                                viewModel.emptyPassword = false
                            },
                            label = "Password",
                            modifier = Modifier.semantics {
                                contentType = ContentType.Password
                            },
                            leadingIcon = Icons.Default.Lock,
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            isError = viewModel.passwordError || viewModel.emptyPassword,
                        )

                        // Messages d'erreurs
                        if (viewModel.emptyUser || viewModel.emptyPassword || viewModel.usernameError || viewModel.passwordError) {
                            Spacer(Modifier.height(12.dp))
                            Text(
                                text = when {
                                    viewModel.emptyUser -> "Username is required"
                                    viewModel.emptyPassword -> "Password is required"
                                    viewModel.usernameError -> "User not found"
                                    viewModel.passwordError -> "Incorrect password"
                                    else -> ""
                                },
                                color = MaterialTheme.colorScheme.error,
                                fontSize = 14.sp
                            )
                        }
                        
                        Spacer(Modifier.height(16.dp))

                        Button(
                            onClick = { viewModel.onLoginClick() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text("Sign In", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        }

                    }
                }
            }
        }

            // Zone fixe en bas pour les boutons d'action
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                TextButton(onClick = onNavigateToCreateAccount) {
                    Text(
                        "Don't have an account ? Sign Up",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
