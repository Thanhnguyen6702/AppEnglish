package com.example.english4d.ui.video

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.english4d.ui.animation.ErrorScreen
import com.example.english4d.ui.animation.LoadingScreen

@Composable
fun VideoScreen(
    navController: NavController,
    viewModel: VideoViewModel = viewModel()
){
    val uiState by viewModel.uiState.collectAsState()
    when(uiState){
        is VideoUiState.Error -> ErrorScreen()
        is VideoUiState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LoadingScreen()
            }
        }
        is VideoUiState.Success -> LazyColumn {
            items((uiState as VideoUiState.Success).channels){
                HorizontalScrollRowVideo(navController = navController, title = it.channel.title, listItem = it.videos, videoViewModel = viewModel)
            }
        }
    }

}
