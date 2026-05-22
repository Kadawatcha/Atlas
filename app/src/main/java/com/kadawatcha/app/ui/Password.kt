package com.kadawatcha.app.ui

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PasswordScreen(
    modifier: Modifier = Modifier, // pour le style
    onNavigateToCreateAccount: () -> Unit // Callback: action vide (Unit) vers création
) {
    var username by rememberSaveable { mutableStateOf("") }
    var emptyuser by rememberSaveable { mutableStateOf(false) }
    var usernameerror by remember { mutableStateOf(false) }

    var password by remember { mutableStateOf("") }
    var passworderror by remember { mutableStateOf(false) }
    var emptypassword by remember { mutableStateOf(false) }


    // Si check est ok on autorise le login
    var checkpassword by remember { mutableStateOf(false) }
    var checkuser by remember { mutableStateOf(false)}

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "FaceGrook",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            textDecoration = TextDecoration.Underline,
            fontStyle = FontStyle.Normal
        )

        Spacer(Modifier.height(25.dp))

        CustomInput(
            value = username,
            onValueChange = {
                username = it
                usernameerror = false
                emptyuser = false
                checkuser = false
            },
            label = "Enter username",
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            isError = usernameerror || emptyuser,

        )

        Spacer(Modifier.height(10.dp))

        CustomInput(
            value = password,
            onValueChange = {
                password = it
                passworderror = false
                emptypassword = false
                checkpassword = false
            },
            label = "Enter password",
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            isError = passworderror || emptypassword,
        )

        Spacer(modifier = Modifier
            .height(20.dp)
        )
        Row(modifier = Modifier.fillMaxWidth(0.8f),
            horizontalArrangement = Arrangement.spacedBy(16.dp)

        ) {

            Button(
                onClick = {
                    val trimmedPassword = password.trim()
                    val trimmedUsername = username.trim()
                    passworderror = false
                    emptypassword = false
                    usernameerror = false
                    emptyuser = false
                    checkpassword = false
                    checkuser = false

                    when {
                        trimmedUsername.isEmpty() -> {
                            emptyuser = true
                        }

                        trimmedPassword.isEmpty() -> {
                            emptypassword = true
                        }

                        trimmedUsername == "User1" -> {
                            checkuser = true
                            if (trimmedPassword == "Pass2") {
                                usernameerror = false
                                checkpassword = true
                            } else {
                                checkuser = true
                                usernameerror = false
                                passworderror = true
                            }
                        }

                        else -> { // MDP rentré mais user inconnu, on n'affiche pas l'erreur sur le mdp donc
                            // passworderror = true
                            usernameerror = true
                        }
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Log in")
            }

        Button(
            onClick = onNavigateToCreateAccount
        ) {
            Text("Create account")
        }

        }


        Spacer(modifier = Modifier.height(10.dp))


        if (emptyuser) {
            Text(text = "Please enter a username", color = Color.Red)
        } else if (emptypassword) {
            Text("Please enter a password", color = Color.Red)
        } else if (usernameerror) {
            Text(text = "Unknown user - Create an account",
                color = Color.Red)
        } else if (passworderror) {
            Text(text = "Correct user but password is wrong",
                color = Color.Red)
        } else if (checkuser && checkpassword) {
            Text(text = "Login success ! ",
                color = Color.Blue)
        }
    }
}




@Composable
fun CustomInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: String, // On passe juste le texte
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) }, // Le Text() est géré ici
        isError = isError,
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