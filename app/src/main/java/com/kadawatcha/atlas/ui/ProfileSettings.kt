package com.kadawatcha.atlas.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kadawatcha.atlas.viewmodel.ProfileViewModel


@Composable
fun ProfileSettings(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = viewModel(),
){
    Column(modifier = modifier) {
        Text("Edit your profile")
    }
}