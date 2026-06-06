package com.kadawatcha.atlas.ui

import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.remember
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
import com.kadawatcha.atlas.viewmodel.NewAccountViewModel
import androidx.compose.ui.platform.LocalAutofillManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.kadawatcha.atlas.utils.SessionManager

@Composable
fun NewAccountScreen(
    modifier: Modifier = Modifier,
    viewModel: NewAccountViewModel = viewModel(),
    onAccountCreated: () -> Unit,
    onBackToLogin: () -> Unit,
) {
    val scrollState = rememberScrollState()

    val context = LocalContext.current // recupérer données sauv
    val autofillManager = LocalAutofillManager.current
    val sessionManager = remember { SessionManager(context) }

    LaunchedEffect(viewModel.creationSuccess) {
        if (viewModel.creationSuccess) {
            autofillManager?.commit()
            sessionManager.saveSession(viewModel.username)

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
                                viewModel.usernameHadSpace = false
                            },
                            label = "Username",
                            leadingIcon = Icons.Default.Person,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            isError = viewModel.usernameEmpty || viewModel.usernameError || viewModel.usernameAlreadyTaken || viewModel.usernameHadSpace
                        )

                        Spacer(Modifier.height(16.dp))

                        CustomInput(
                            modifier = Modifier.semantics { contentType = ContentType.NewPassword },
                            value = viewModel.password,
                            onValueChange = {
                                viewModel.password = it
                                viewModel.passwordEmpty = false
                                viewModel.passwordError = false
                                viewModel.passwordHadSpace = false
                            },
                            label = "Password",
                            leadingIcon = Icons.Default.Lock,
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Next
                            ),
                            isError = viewModel.passwordEmpty || viewModel.passwordError || (viewModel.password.isNotEmpty() && viewModel.password.length < 8) || viewModel.passwordHadSpace,
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
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Done
                            ),
                            isError = viewModel.repeatEmpty || viewModel.repeatBad
                        )

                        // Messages d'erreurs
                        if (viewModel.usernameEmpty || viewModel.passwordEmpty || viewModel.repeatEmpty || viewModel.repeatBad || viewModel.passwordHadSpace || viewModel.usernameHadSpace || viewModel.usernameAlreadyTaken) {
                            Spacer(Modifier.height(12.dp))
                            Text(
                                text = when {
                                    viewModel.usernameEmpty -> "Username is required"
                                    viewModel.usernameHadSpace -> "No spaces in username please"
                                    viewModel.usernameAlreadyTaken -> "User already exists, maybe go to login"
                                    viewModel.passwordEmpty -> "Password is required"
                                    viewModel.passwordHadSpace -> "No spaces in password please"
                                    viewModel.repeatEmpty -> "Please repeat password"
                                    viewModel.repeatBad -> "Passwords do not match"
                                    else -> ""
                                },
                                modifier = if (viewModel.usernameAlreadyTaken) {
                                    Modifier.clickable { onBackToLogin() }
                                } else {
                                    Modifier
                                },
                                color = if (viewModel.usernameAlreadyTaken) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
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
