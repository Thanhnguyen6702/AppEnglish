package com.example.english4d.ui.newspaper

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.english4d.R
import com.example.english4d.data.news.NewsTopic
import com.example.english4d.ui.AppViewModelProvider
import com.example.english4d.ui.animation.ErrorScreen
import com.example.english4d.ui.animation.LoadingScreen

@Composable
fun NewsScreen(
    navController: NavController
) {
    val newsViewmodel: NewsViewmodel = viewModel(factory = AppViewModelProvider.Factory)
    NewsTopicLayout(
        navController = navController,
        newsViewmodel = newsViewmodel
    )
}

@Composable
fun NewsTopicLayout(
    navController: NavController,
    newsViewmodel: NewsViewmodel
) {
    val newsUiState by newsViewmodel.uiState.collectAsState()
    when (newsUiState) {
        is NewsUiState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LoadingScreen()
            }
        }

        is NewsUiState.Success -> {
            val listTopic: List<NewsTopic> = listOf(
                (newsUiState as NewsUiState.Success).travel,
                (newsUiState as NewsUiState.Success).education,
                (newsUiState as NewsUiState.Success).business,
                (newsUiState as NewsUiState.Success).sports,
                (newsUiState as NewsUiState.Success).trend,
                (newsUiState as NewsUiState.Success).world
            )
            ResultScreen(navController = navController,listTopic = listTopic, newsViewmodel = newsViewmodel)
        }

        else -> {
            ErrorScreen()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(
    navController: NavController,
    listTopic: List<NewsTopic>,
    newsViewmodel: NewsViewmodel
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = {
                Text(
                    text = stringResource(id = R.string.news),
                    style = MaterialTheme.typography.headlineMedium
                )
            })
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            items(listTopic) { items ->
                HorizontalScrollRow(navController = navController,items.title, listItem = items.listItem, newsViewmodel = newsViewmodel )
            }
        }
    }
}


