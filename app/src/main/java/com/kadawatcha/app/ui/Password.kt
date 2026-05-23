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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PasswordScreen(
    modifier: Modifier = Modifier, // pour le style
    onNavigateToCreateAccount: () -> Unit // Callback: action vide (Unit) vers création
) {
    var username by rememberSaveable { mutableStateOf("") }
    var emptyUser by rememberSaveable { mutableStateOf(false) }
    var usernameError by rememberSaveable { mutableStateOf(false) }

    var password by rememberSaveable { mutableStateOf("") }
    var passwordError by rememberSaveable { mutableStateOf(false) }
    var emptyPassword by rememberSaveable { mutableStateOf(false) }


    // Si check est ok on autorise le login
    var checkPassword by rememberSaveable { mutableStateOf(false) }
    var checkUser by rememberSaveable { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = modifier
                .fillMaxSize()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            PageTitle(text = "FakeGrook\nLogin")
            Spacer(Modifier.height(25.dp))

            CustomInput(
                value = username,
                onValueChange = {
                    username = it
                    usernameError = false
                    emptyUser = false
                    checkUser = false
                },
                label = "Enter username",
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                isError = usernameError || emptyUser,

                )

            Spacer(Modifier.height(10.dp))

            CustomInput(
                value = password,
                onValueChange = {
                    password = it
                    passwordError = false
                    emptyPassword = false
                    checkPassword = false
                },
                label = "Enter password",
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                isError = passwordError || emptyPassword || (password.isNotEmpty() && password.length < 8),
                supportingText = {
                    if (password.isNotEmpty() && password.length < 8) {
                        Text(text = "Too short")
                    }
                }
            )

            Spacer(
                modifier = Modifier
                    .height(20.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(0.8f),
                horizontalArrangement = Arrangement.spacedBy(16.dp)

            ) {

                Button(
                    onClick = {
                        val trimmedPassword = password.trim()
                        val trimmedUsername = username.trim()
                        passwordError = false
                        emptyPassword = false
                        usernameError = false
                        emptyUser = false
                        checkPassword = false
                        checkUser = false

                        when {
                            trimmedUsername.isEmpty() -> {
                                emptyUser = true
                            }

                            trimmedPassword.isEmpty() -> {
                                emptyPassword = true
                            }

                            trimmedUsername == "User1" -> {
                                checkUser = true
                                if (trimmedPassword == "Pass2") {
                                    usernameError = false
                                    checkPassword = true
                                } else {
                                    checkUser = true
                                    usernameError = false
                                    passwordError = true
                                }
                            }

                            else -> { // MDP rentré mais user inconnu, on n'affiche pas l'erreur sur le mdp donc
                                // passwordError = true
                                usernameError = true
                            }
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Log in")
                }


            }


            Spacer(modifier = Modifier.height(10.dp))


            if (emptyUser) {
                Text(text = "Please enter a username", color = Color.Red)
            } else if (emptyPassword) {
                Text("Please enter a password", color = Color.Red)
            } else if (usernameError) {
                Text(
                    text = "Unknown user - Create an account",
                    color = Color.Red
                )
            } else if (passwordError) {
                Text(
                    text = "Correct user but password is wrong",
                    color = Color.Red
                )
            } else if (checkUser && checkPassword) {
                Text(
                    text = "Login success ! ",
                    color = Color.Blue
                )
            }

        }

        TextButton(
            onClick = onNavigateToCreateAccount,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Text("Create account ->")
        }
    }
}

@Composable
fun PageTitle(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Normal,
        lineHeight = 40.sp, // espace via les sauts a la ligne
        // textDecoration = TextDecoration.Underline,
        textAlign = TextAlign.Center, // Centrage horizontal des lignes
        modifier = modifier.fillMaxWidth() // Prend toute la largeur pour que le centrage fonctionne
    )
}


@Composable
fun CustomInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: String, // On passe juste le texte
    isError: Boolean = false,
    supportingText: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) }, // Le Text() est géré ici
        isError = isError,
        supportingText = supportingText,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        singleLine = true, // Fixé ici car "il ne bouge jamais"
        shape = RoundedCornerShape(30.dp), // arondis angle 
        modifier = Modifier
            .fillMaxWidth(0.8f),

        // gere largeur zone de texte
        colors = OutlinedTextFieldDefaults.colors(

            // bordures
            unfocusedBorderColor = Color.Gray,
            // focusedBorderColor = Color.White,

            // text tapé
            focusedTextColor = MaterialTheme.colorScheme.onSurface, // affiché quand on tape
            // adapte matérial theme (mode sombre)
            unfocusedTextColor = Color.Gray,

            // texte par défaut ("deja rentré")
            focusedLabelColor = Color.White,
            unfocusedLabelColor = Color.Gray,

            errorBorderColor = Color.Red,
            errorLabelColor = Color.Red,
        )
    )
}
