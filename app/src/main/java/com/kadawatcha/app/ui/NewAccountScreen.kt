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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kadawatcha.app.viewmodel.NewAccountViewModel

@Composable
fun NewAccountScreen(
    modifier: Modifier = Modifier,
    viewModel: NewAccountViewModel = viewModel(),
    onAccountCreated: () -> Unit,
    onBackToLogin: () -> Unit,
) {
    val scrollState = rememberScrollState()

    LaunchedEffect(viewModel.creationSuccess) {
        if (viewModel.creationSuccess) {
            onAccountCreated()
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
                    PageTitle(text = "Join Us")

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
                            modifier = Modifier.semantics {
                                contentType = ContentType.Username
                            },
                            value = viewModel.username,
                            onValueChange = {
                                viewModel.username = it
                                viewModel.usernameEmpty = false
                                viewModel.usernameError = false
                            },
                            label = "Username",
                            leadingIcon = Icons.Default.Person,
                            isError = viewModel.usernameEmpty || viewModel.usernameError || viewModel.usernameAlreadyTaken
                        )

                        Spacer(Modifier.height(16.dp))

                        CustomInput(
                            modifier = Modifier.semantics { contentType = ContentType.NewPassword },
                            value = viewModel.password,
                            onValueChange = {
                                viewModel.password = it
                                viewModel.passwordEmpty = false
                                viewModel.passwordError = false
                            },
                            label = "Password",
                            leadingIcon = Icons.Default.Lock,
                            visualTransformation = PasswordVisualTransformation(),
                            isError = viewModel.passwordEmpty || viewModel.passwordError || (viewModel.password.isNotEmpty() && viewModel.password.length < 8),
                            supportingText = if (viewModel.password.isNotEmpty() && viewModel.password.length < 8) {
                                { Text(text = "Must be 8+ characters") }
                            } else null
                        )

                        Spacer(Modifier.height(16.dp))

                        CustomInput(
                            modifier = Modifier.semantics { contentType = ContentType.NewPassword },
                            value = viewModel.repeatPassword,
                            onValueChange = {
                                viewModel.repeatPassword = it
                                viewModel.repeatEmpty = false
                                viewModel.repeatBad = false
                            },
                            label = "Repeat Password",
                            leadingIcon = Icons.Default.Lock,
                            visualTransformation = PasswordVisualTransformation(),
                            isError = viewModel.repeatEmpty || viewModel.repeatBad
                        )

                        // Messages d'erreurs
                        if (viewModel.usernameEmpty || viewModel.passwordEmpty || viewModel.repeatEmpty || viewModel.repeatBad) {
                            Spacer(Modifier.height(12.dp))
                            Text(
                                text = when {
                                    viewModel.usernameEmpty -> "Username is required"
                                    viewModel.passwordEmpty -> "Password is required"
                                    viewModel.repeatEmpty -> "Please repeat password"
                                    viewModel.repeatBad -> "Passwords do not match"
                                    else -> ""
                                },
                                color = MaterialTheme.colorScheme.error,
                                fontSize = 14.sp
                            )
                        }
                        Spacer(Modifier.height(16.dp))

                        Button(
                            onClick = { viewModel.onCreateAccountClick() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text("Create Account", fontWeight = FontWeight.Bold, fontSize = 18.sp)
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

                TextButton(onClick = onBackToLogin) {
                    Text(
                        "Already have an account ? Sign In",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
