package com.kadawatcha.app.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kadawatcha.app.viewmodel.MainViewModel


@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel()
) {
    Surface(
        modifier = modifier.fillMaxSize(),// prend tt l'écran
        color = MaterialTheme.colorScheme.background
    ) {
        // On remplace la Box par une Column toute simple
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp), // Marges de sécurité
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // TON TITRE : Il se place naturellement en haut
            PageTitle(text = "FaceGrook")

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Welcome to Kad's app",
                color = MaterialTheme.colorScheme.secondary
            )

            // Ici tu pourras ajouter la suite de ton contenu...
        }
    }
}