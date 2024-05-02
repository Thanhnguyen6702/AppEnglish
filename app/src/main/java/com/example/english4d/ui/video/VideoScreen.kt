package com.example.english4d.ui.video

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun VideoScreen(
    navController: NavController,
    viewModel: VideoViewModel = viewModel()
){
    val uiState by viewModel.uiState.collectAsState()
    LazyColumn {
        items(uiState.channels){
            HorizontalScrollRowVideo(navController = navController, title = it.channel.title, listItem = it.videos, videoViewModel = viewModel)
        }
    }
}
