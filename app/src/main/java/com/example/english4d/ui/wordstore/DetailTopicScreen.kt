package com.example.english4d.ui.wordstore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import com.example.english4d.R
import com.example.english4d.navigation.ExtensionGraphScreen
import com.example.english4d.ui.customdialog.DialogAddWord
import com.example.english4d.ui.customdialog.DialogRename
import com.example.english4d.utils.listColor
import com.example.englishe4.presentation.component.ItemWordCardScreen
import com.example.englishe4.presentation.component.TopAppBar

@Composable
fun DetailTopicScreen(
    navController: NavController,
    viewModel: WordStoreViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    var isShowDialogRename by remember {
        mutableStateOf(false)
    }
    var isShowDialogAddWord by remember {
        mutableStateOf(false)
    }
    if (isShowDialogRename) {
        DialogRename(onDismiss = { isShowDialogRename = false }) {
            viewModel.renameTopic(it)
        }
    }
    if (isShowDialogAddWord) {
        DialogAddWord(onDismiss = { isShowDialogAddWord = false }) {
            viewModel.addWord(it, uiState.topicWithWords.topic.id)
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                type = "modifier",
                title = uiState.topicWithWords.topic.name,
                isShowTick = uiState.showRemoveWord,
                onClickRight = {
                    isShowDialogRename = true
                },
                onClickFinish = { viewModel.showDeleteWord(false) }) {
                navController.popBackStack()
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = colorResource(id = R.color.green_100),
                onClick = {
                    isShowDialogAddWord = true
                },
                contentColor = Color.White
            ) {
                Icon(Icons.Outlined.Add, contentDescription = "Add Item")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemsIndexed(uiState.listItemDetail) { index, it ->
                Row(
                    Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ItemWordCardScreen(
                        modifier = Modifier.weight(1f),
                        data = it.myword.english,
                        onLongClick = {
                            viewModel.showDeleteWord(true)
                        },
                        colors = listColor[index % listColor.size],
                        onClickNav = {
                            viewModel.setPosition(index)
                            navController.navigate(ExtensionGraphScreen.DetailWord.route)
                        })
                    if (uiState.showRemoveWord) {
                        IconButton(onClick = { viewModel.deleteItem(it.myword.id) }) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
            item {
                for (i in 0..<uiState.numberLoading2) {
                    ItemWordCardScreen(data = "",
                        colors = listColor[i % listColor.size],
                        onLongClick = { }) {
                    }
                }
            }
        }
    }
}