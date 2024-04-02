package com.example.english4d.ui.theme.newspaper

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.english4d.R
import com.example.english4d.data.news.NewsContent
import com.example.english4d.ui.theme.TypeText
import com.example.english4d.ui.theme.animation.LoadingScreen

@Composable
fun ReadNewsScreen(
    topic: String,
    url: String
) {
    val newsViewmodel: NewsViewmodel = viewModel(factory = NewsViewmodel.Factory)
    newsViewmodel.getNewsPaper(url)
    ReadNewsLayout(uiState = newsViewmodel, topic = topic)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadNewsLayout(
    uiState: NewsViewmodel,
    topic: String,
) {
    val listItem = uiState.dataNews.contentNews
    val listQuestion = uiState.questionUiState.listQuestion
    val scrollState = rememberLazyListState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = topic) }
            )
        }
    ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                state = scrollState,
            ) {
                items(listItem) {
                    ItemLayout(
                        item = it, modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                vertical = dimensionResource(
                                    id = R.dimen.padding_hight
                                ),
                                horizontal = dimensionResource(id = R.dimen.padding_medium)
                            )
                    )
                }
                if (!listQuestion.isEmpty()){
                    items(listQuestion){
                        QuestionLayout(questions = it)
                    }
                } else{
                    item {
                        LoadingQuestion()
                    }
                }
            }
        }
    }

@Composable
fun ItemLayout(
    item: NewsContent,
    modifier: Modifier = Modifier
) {
    when (item.type) {
        "image" -> {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.content)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(id = R.drawable.loading_img),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = modifier.clip(shape = MaterialTheme.shapes.small)
            )
        }

        else -> {
            Text(
                text = item.content,
                style = TypeText.bodyLage,
                modifier = modifier,
                color = Color.Black
            )
        }
    }
}

@Composable
fun LoadingQuestion(){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.height(dimensionResource(id = R.dimen.height_item))
    ) {
        Text(text = stringResource(id = R.string.loading_question), style = TypeText.bodyMedium, modifier = Modifier.padding(
            dimensionResource(id = R.dimen.padding_hight)))
        LoadingScreen(
            circleSize = dimensionResource(id = R.dimen.size_loading_medium),
            travelDistance = dimensionResource(id = R.dimen.travel_distance)
        )
    }
}
