package com.example.english4d.ui.topic

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.english4d.R
import com.example.english4d.data.database.vocabulary.CompletionRate
import com.example.english4d.navigation.HomeGraphScreen
import com.example.english4d.ui.AppViewModelProvider
import com.example.english4d.ui.theme.TypeText


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TopicScreen(
    topicViewModel: TopicViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Filled.Close, contentDescription = null)
                    }
                    Text(
                        text = stringResource(id = R.string.change_topic),
                        style = TypeText.bodyMedium.copy(
                            color = colorResource(
                                id = R.color.purple_200
                            )
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            })
        }
    ) {
        val topicUiState by topicViewModel.uiState.collectAsState()
        LazyColumn(
            modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_medium), top = it.calculateTopPadding())
        ) {
            items(topicUiState.listTopic) {
                ItemThemeLayout(
                    navController,
                    title = it.first,
                    listTopic = it.second,
                    completionRates = topicUiState.completionRates
                )
            }
        }
    }
}

@Composable
fun ItemThemeLayout(
    navController: NavHostController,
    title: String,
    listTopic: List<ItemTopic>,
    completionRates: Map<Int, CompletionRate>
) {
    Column(
        modifier = Modifier
            .background(color = colorResource(id = R.color.gray_10))
            .padding(
                vertical = dimensionResource(
                    id = R.dimen.padding_medium
                )
            )
    ) {
        Text(
            text = title, style = TypeText.bodyMedium, modifier = Modifier.padding(
                bottom = dimensionResource(
                    id = R.dimen.padding_hight
                )
            )
        )
        LazyRow {
            items(listTopic) {
                ItemTopicLayout(
                    navController = navController,
                    itemTopic = it,
                    completionRate = completionRates[it.id]!!
                )
            }
        }
    }
}

@Composable
fun ItemTopicLayout(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    itemTopic: ItemTopic,
    completionRate: CompletionRate
) {
    Surface(
        modifier = modifier
            .size(
                width = dimensionResource(id = R.dimen.size_item_topic_width),
                height = dimensionResource(
                    id = R.dimen.size_item_topic_height
                )
            )
            .padding(end = dimensionResource(id = R.dimen.padding_medium))
            .clickable {
                navController.navigate(HomeGraphScreen.Home.passData(itemTopic.id)) {
                    popUpTo(HomeGraphScreen.TopicsVocab.route) { inclusive = true }
                }
            },
        tonalElevation = 10.dp,
        shape = MaterialTheme.shapes.large,
        color = colorResource(id = R.color.white)
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = itemTopic.topic,
                style = TypeText.h7.copy(fontWeight = FontWeight.Medium),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = dimensionResource(id = R.dimen.padding_hight),
                        top = dimensionResource(id = R.dimen.padding_medium),
                        end = dimensionResource(id = R.dimen.padding_hight)

                    )
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .weight(1f)
                        .padding(
                            start = dimensionResource(id = R.dimen.padding_hight),
                            end = dimensionResource(id = R.dimen.padding_medium),
                            bottom = dimensionResource(id = R.dimen.padding_hight)
                        )
                ) {
                    Text(
                        text = stringResource(
                            id = R.string.number_word,
                            completionRate.totalVocabulary
                        ),
                        style = TypeText.h7.copy(color = colorResource(id = R.color.gray_100)),
                        modifier = Modifier.padding(
                            end = dimensionResource(
                                id = R.dimen.padding_small
                            )
                        )
                    )
                    ProgressBarCustom(progress = (completionRate.totalVocabulary - completionRate.unlearnedVocabulary).toFloat() / completionRate.totalVocabulary)

                }
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(itemTopic.image).build(), contentDescription = null,
                    placeholder = painterResource(id = R.drawable.loading_img),
                    modifier = Modifier.size(60.dp, 60.dp)
                )
            }
        }
    }
}

@Composable
fun ProgressBarCustom(progress: Float = 0f) {
    LinearProgressIndicator(
        progress = { progress },
        modifier = Modifier
            .size(150.dp, 10.dp)
            .clip(shape = RoundedCornerShape(16.dp)),
        color = colorResource(id = R.color.green_100),
        trackColor = colorResource(id = R.color.green_50),
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewItem1() {
    ItemTopicLayout1(
        itemTopic = ItemTopic("Vocabulary", 10, "Hoạt động hằng ngày nhé", "", 1),
        completionRate = CompletionRate(1, 10, 10)
    )
}

@Composable
fun ItemTopicLayout1(
    modifier: Modifier = Modifier,
    itemTopic: ItemTopic,
    completionRate: CompletionRate
) {
    Surface(
        color = Color.Green,
        modifier = modifier
            .size(width = 160.dp, height = 130.dp)
            .padding(all = dimensionResource(id = R.dimen.padding_small)),
        tonalElevation = 10.dp,
        shape = MaterialTheme.shapes.large


    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = itemTopic.topic,
                style = TypeText.h7.copy(fontWeight = FontWeight.Medium),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = dimensionResource(id = R.dimen.padding_hight),
                        top = dimensionResource(id = R.dimen.padding_medium),
                        end = dimensionResource(id = R.dimen.padding_hight)

                    )
            )
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .weight(1f)
                        .padding(
                            start = dimensionResource(id = R.dimen.padding_hight),
                            end = dimensionResource(id = R.dimen.padding_medium),
                            bottom = dimensionResource(id = R.dimen.padding_hight)
                        )
                ) {
                    Text(
                        text = completionRate.unlearnedVocabulary.toString() + " từ",
                        style = TypeText.h7.copy(color = colorResource(id = R.color.gray_100)),
                        modifier = Modifier.padding(
                            end = dimensionResource(
                                id = R.dimen.padding_small
                            )
                        )
                    )
                    ProgressBarCustom(progress = (completionRate.totalVocabulary - completionRate.unlearnedVocabulary).toFloat() / completionRate.totalVocabulary)

                }
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(itemTopic.image).build(), contentDescription = null,
                    placeholder = painterResource(id = R.drawable.loading_img),
                    modifier = Modifier.size(70.dp, 70.dp)
                )
            }
        }
    }
}

