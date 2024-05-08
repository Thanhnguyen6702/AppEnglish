package com.example.english4d.ui.newspaper

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Quiz
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.english4d.R
import com.example.english4d.data.news.NewsContent
import com.example.english4d.ui.AppViewModelProvider
import com.example.english4d.ui.animation.LoadingScreen
import com.example.english4d.ui.theme.TypeText

@Composable
fun ReadNewsScreen(
    topic: String,
    url: String
) {
    val newsViewmodel: NewsViewmodel = viewModel(factory = AppViewModelProvider.Factory)
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
    val listQuestion = uiState.questionUiState.listQuestionGPT
    val scrollState = rememberLazyListState()
    var openQuestion by remember {
        mutableStateOf(false)
    }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    openQuestion = true
                },
                contentColor = Color.White
            ) {
                Icon(Icons.Outlined.Quiz, contentDescription = "Add Item")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(top = paddingValues.calculateTopPadding())
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = dimensionResource(id = R.dimen.padding_hight),
                        end = dimensionResource(id = R.dimen.padding_hight),
                        bottom = dimensionResource(
                            id = R.dimen.padding_small
                        )
                    ),
                text = topic,
                style = TypeText.h5.copy(fontWeight = FontWeight.Bold),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            LazyColumn(
                modifier = Modifier
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
            }
        }
        if (openQuestion) {
            ModalBottomSheet(
                onDismissRequest = { openQuestion = false },
                sheetState = sheetState
            ) {
                if (listQuestion.isEmpty()) {
                    LoadingQuestion()
                } else {
                    QuestionLayout(
                        questions = listQuestion
                    )
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
                style = TypeText.h4,
                modifier = modifier,
                color = Color.Black
            )
        }
    }
}

@Composable
fun LoadingQuestion() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.height(dimensionResource(id = R.dimen.height_item))
    ) {
        Text(
            text = stringResource(id = R.string.loading_question),
            style = TypeText.h6,
            modifier = Modifier.padding(
                dimensionResource(id = R.dimen.padding_hight)
            )
        )
        LoadingScreen(
            circleSize = dimensionResource(id = R.dimen.size_loading_medium),
            travelDistance = dimensionResource(id = R.dimen.travel_distance)
        )
    }
}
