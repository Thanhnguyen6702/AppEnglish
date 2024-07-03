package com.example.english4d.ui.wordstore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.english4d.navigation.ExtensionGraphScreen
import com.example.englishe4.presentation.component.ItemWordCardScreen

@Composable
fun DetailTopicScreen(
    navController: NavController,
    viewModel: WordStoreViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    Scaffold { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                OutlinedTextField(
                    value = uiState.topic,
                    onValueChange = {
                        viewModel.onChangeTopic(it)
                    },
                    label = { Text(text = "Chủ đề") }
                )
            }
            items(uiState.listItem) {
                ItemWordCardScreen(data = it.english) {
                    navController.navigate(ExtensionGraphScreen.DetailWord.route)
                }
            }
        }
    }
}