package com.example.english4d.ui.wordstore

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.english4d.R
import com.example.english4d.navigation.ExtensionGraphScreen
import com.example.english4d.ui.AppViewModelProvider
import com.example.english4d.ui.theme.TypeText
import com.example.englishe4.presentation.TopicScreen.component.ItemTopicCard
import com.example.englishe4.presentation.component.TopAppBar

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WordStoreScreen(
    navController: NavHostController,
    viewModel: WordStoreViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                type = "add",
                title = "Kho chủ đề",
                onClickRight = {
                    navController.navigate(ExtensionGraphScreen.AddWord.route)
                }
            ) {
                navController.popBackStack()
            }
        }
    ) {
        val topic by viewModel.topic.collectAsState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {
            if (topic.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        modifier = Modifier.clickable { navController.navigate(ExtensionGraphScreen.AddWord.route) },
                        painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = null
                    )
                    Text(
                        text = "Chưa có chủ đề nào",
                        style = TypeText.h4.copy(fontWeight = FontWeight.Medium)
                    )
                }
            }

            LazyColumn {
                items(topic) {
                    Row(
                        Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ItemTopicCard(
                            modifier = Modifier.weight(1f),
                            title = it.name,
                            onClick = {
                                viewModel.getItem(it.id)
                                navController.navigate(ExtensionGraphScreen.DetailTopic.route)
                            },
                            onLongClick = {
                                viewModel.showDeleteTopic(true)
                            }
                        )
                        if (uiState.showRemoveTopic) {
                            IconButton(onClick = { viewModel.deleteTopic(it.id) }) {
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
}

@Preview
@Composable
fun WordStoreScreenPreview() {
    WordStoreScreen(navController = NavHostController(LocalContext.current))
}