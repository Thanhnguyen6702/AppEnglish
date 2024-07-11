package com.example.english4d.ui.wordstore

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.english4d.R
import com.example.english4d.navigation.ExtensionGraphScreen
import com.example.english4d.ui.AppViewModelProvider
import com.example.english4d.ui.theme.TypeText
import com.example.english4d.utils.listColor
import com.example.english4d.utils.listImage
import com.example.englishe4.presentation.TopicScreen.component.ItemTopicCard
import com.example.englishe4.presentation.component.TopAppBar

@Composable
fun WordStoreScreen(
    navController: NavHostController,
    viewModel: WordStoreViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    var hasPermissions by remember {
        mutableStateOf(
            arrayOf(
                Manifest.permission.POST_NOTIFICATIONS,
            ).all {
                ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
            }
        )
    }
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        hasPermissions = permissions.all { it.value }
    }

    // Request permissions if not granted
    if (!hasPermissions) {
        LaunchedEffect(key1 = true) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                permissionLauncher.launch(arrayOf(Manifest.permission.POST_NOTIFICATIONS))
            }
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                type = "add",
                title = "Kho chủ đề",
                isShowTick = uiState.showRemoveTopic,
                onClickRight = {
                    navController.navigate(ExtensionGraphScreen.AddWord.route)
                },
                onClickFinish = {viewModel.showDeleteTopic(false)}
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
                itemsIndexed(topic) {index, it ->
                    Row(
                        Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ItemTopicCard(
                            modifier = Modifier.weight(1f),
                            topic = it,
                            colors = listColor[index % listColor.size],
                            img = listImage[index % listImage.size],
                            updateStudy = { viewModel.updateStudyTopic(it.id,it.is_study) },
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