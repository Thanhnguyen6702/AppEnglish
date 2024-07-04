package com.example.english4d.ui.wordstore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.english4d.navigation.ExtensionGraphScreen
import com.example.englishe4.presentation.component.ItemWordCardScreen
import com.example.englishe4.presentation.component.TopAppBar

@Composable
fun DetailTopicScreen(
    navController: NavController,
    viewModel: WordStoreViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(type = "modifier", title = uiState.topicWithWords.topic.name, onClickRight = {

            }) {
                navController.popBackStack()
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(uiState.topicWithWords.words) {

                Row(
                    Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ItemWordCardScreen(
                        modifier = Modifier.weight(1f),
                        data = it.english,
                        onLongClick = {
                            viewModel.showDeleteWord(true)
                        },
                        onClickNav = {
                            navController.navigate(ExtensionGraphScreen.DetailWord.route)

                        })
                    if (uiState.showRemoveWord) {
                        IconButton(onClick = { viewModel.deleteItem(it.id) }) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    }
}