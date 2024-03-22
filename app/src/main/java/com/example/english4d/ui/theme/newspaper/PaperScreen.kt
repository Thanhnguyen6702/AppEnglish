package com.example.english4d.ui.theme.newspaper

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.english4d.R
import com.example.english4d.data.news.NewsTopic
import kotlinx.coroutines.delay

@Composable
fun NewsScreen(
    navController: NavController
) {
    val newsViewmodel: NewsViewmodel = viewModel(factory = NewsViewmodel.Factory)
    val newsUiState = newsViewmodel.newsUiState
    PaperLayout(
        navController = navController,
        newsUiState = newsUiState
    )
}

@Composable
fun PaperLayout(
    navController: NavController,
    newsUiState: NewsUiState
) {
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
                newsUiState.travel,
                newsUiState.education,
                newsUiState.business,
                newsUiState.sports,
                newsUiState.trend,
                newsUiState.world
            )
            ResultScreen(navController = navController,listTopic = listTopic)
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
    listTopic: List<NewsTopic>
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
                HorizontalScrollRow(navController = navController,items.title, listItem = items.listItem)
            }
        }
    }
}

@Composable
fun ErrorScreen() {

}

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier,
    circleSize: Dp = 25.dp,
    circleColor: Color = MaterialTheme.colorScheme.primary,
    spaceBetween: Dp = 10.dp,
    travelDistance: Dp = 20.dp
) {
    val circles = listOf(
        remember { Animatable(initialValue = 0f) },
        remember { Animatable(initialValue = 0f) },
        remember { Animatable(initialValue = 0f) }
    )

    circles.forEachIndexed { index, animatable ->
        LaunchedEffect(key1 = animatable) {
            delay(index * 100L)
            animatable.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 1200
                        0.0f at 0 using LinearOutSlowInEasing
                        1.0f at 300 using LinearOutSlowInEasing
                        0.0f at 600 using LinearOutSlowInEasing
                        0.0f at 1200 using LinearOutSlowInEasing
                    },
                    repeatMode = RepeatMode.Restart
                )
            )
        }
    }

    val circleValues = circles.map { it.value }
    val distance = with(LocalDensity.current) { travelDistance.toPx() }
    val lastCircle = circleValues.size - 1

    Row(modifier = modifier) {
        circleValues.forEachIndexed { index, value ->
            Box(modifier = Modifier
                .size(circleSize)
                .graphicsLayer { translationY = -value * distance }
                .background(color = circleColor, shape = CircleShape)
            )
            if (index != lastCircle) Spacer(modifier = Modifier.width(spaceBetween))
        }
    }
}