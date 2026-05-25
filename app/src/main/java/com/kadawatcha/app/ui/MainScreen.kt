package com.kadawatcha.app.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kadawatcha.app.viewmodel.MainViewModel


@Composable
fun MainScreen(
    modifier : Modifier = Modifier,
    viewModel : MainViewModel = viewModel()
){
    Surface(
        modifier = Modifier.fillMaxSize()// prend tt l'écran
    ) { }

}
