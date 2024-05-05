package com.example.english4d.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.english4d.ui.extension.ExtensionScreen
import com.example.english4d.ui.home.HomeScreen
import com.example.english4d.ui.home.MyBottomBar
import com.example.english4d.ui.pronuciation.PronunciationStatisticScreen

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        bottomBar = {
            MyBottomBar(
                selectedItemIndex = uiState.selectedItemIndex,
                onClick = { viewModel.setIndex(it) })
        }
    ) {
        when (uiState.selectedItemIndex) {
            0 -> HomeScreen(
                navController = navController,
                modifier = Modifier.padding(bottom = it.calculateBottomPadding())
            )

            1 -> PronunciationStatisticScreen(
                navController = navController,
                modifier = Modifier.padding(bottom = it.calculateBottomPadding())
            )

            2 -> ExtensionScreen(navController = navController)
        }
    }
}