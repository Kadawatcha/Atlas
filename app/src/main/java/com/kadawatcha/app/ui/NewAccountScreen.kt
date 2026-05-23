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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kadawatcha.app.viewmodel.NewAccountViewModel

@Composable
fun NewAccountScreen(
    viewModel: NewAccountViewModel = viewModel(),
    onBackToLogin: () -> Unit,
) {

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

                        Spacer(Modifier.height(24.dp))

                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = RoundedCornerShape(16.dp),
                            onClick = {
                                viewModel.onCreateAccountCLick()
                            },
                        ) {
                            Text("Create Account", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        }

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
