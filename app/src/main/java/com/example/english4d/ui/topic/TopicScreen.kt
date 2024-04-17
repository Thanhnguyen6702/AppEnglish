package com.example.english4d.ui.topic

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.english4d.R
import com.example.english4d.data.database.vocabulary.CompletionRate
import com.example.english4d.navigation.Screen
import com.example.english4d.ui.AppViewModelProvider
import com.example.english4d.ui.theme.TypeText

@Composable
fun TopicScreen(
    topicViewModel: TopicViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navController: NavHostController
) {
    Scaffold { paddingValues ->
        val topicUiState by topicViewModel.uiState.collectAsState()
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
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
            text = title, style = TypeText.headLarge, modifier = Modifier.padding(
                bottom = dimensionResource(
                    id = R.dimen.padding_hight
                )
            )
        )
        LazyRow {
            items(listTopic) {
                ItemTopicLayout(navController = navController,itemTopic = it, completionRate = completionRates[it.id]!!)
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
    Box(
        modifier = modifier
            .size(
                width = dimensionResource(id = R.dimen.size_item_topic_width),
                height = dimensionResource(
                    id = R.dimen.size_item_topic_height
                )
            )
            .padding(all = dimensionResource(id = R.dimen.padding_small))
            .clip(RoundedCornerShape(20.dp))
            .background(color = colorResource(id = R.color.white))
            .shadow(
                elevation = 5.dp,
                shape = RoundedCornerShape(20.dp),
                clip = false,
            )
            .clickable { navController.navigate(Screen.Home.passData(itemTopic.id)) }

    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = dimensionResource(id = R.dimen.padding_medium),
                        start = dimensionResource(id = R.dimen.padding_medium),
                        end = dimensionResource(id = R.dimen.padding_small)
                    )
            ) {
                Text(text = itemTopic.id.toString() + ". ", style = TypeText.h7)
                Text(text = itemTopic.topic, style = TypeText.h7)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = dimensionResource(id = R.dimen.padding_small))
                ) {
                    Text(
                        text = completionRate.unlearnedVocabulary.toString(),
                        style = TypeText.h8,
                        modifier = Modifier.padding(
                            end = dimensionResource(
                                id = R.dimen.padding_medium
                            )
                        )
                    )
                    ProgressBarCustom(progress = (completionRate.totalVocabulary - completionRate.unlearnedVocabulary).toFloat() / completionRate.totalVocabulary)

                }
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(itemTopic.image).build(), contentDescription = null,
                    placeholder = painterResource(id = R.drawable.loading_img),
                    modifier = Modifier.size(80.dp, 80.dp)
                )
            }
        }
    }
}

@Composable
fun ProgressBarCustom(progress: Float = 0f) {
    LinearProgressIndicator(
        progress = progress,
        color = colorResource(id = R.color.green_100),
        trackColor = colorResource(id = R.color.green_50),
        modifier = Modifier
            .size(150.dp, 10.dp)
            .clip(shape = RoundedCornerShape(16.dp))
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewItem1() {
    ItemTopicLayout1(
        itemTopic = ItemTopic("Vocabulary", 10, "HIHIHI", "", 1),
        completionRate = CompletionRate(1, 10, 10)
    )
}

@Composable
fun ItemTopicLayout1(
    modifier: Modifier = Modifier,
    itemTopic: ItemTopic,
    completionRate: CompletionRate
) {
    Box(
        modifier = modifier
            .size(width = 200.dp, height = 120.dp)
            .padding(all = dimensionResource(id = R.dimen.padding_small))
            .clip(RoundedCornerShape(20.dp))
            .background(color = colorResource(id = R.color.white))
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(20.dp),
                clip = false,
                spotColor = colorResource(id = R.color.gray_10)
            )


    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = dimensionResource(
                            id = R.dimen.padding_medium
                        )
                    )
            ) {
                Text(text = itemTopic.id.toString() + ". ", style = TypeText.h7)
                Text(text = itemTopic.topic, style = TypeText.h7)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = dimensionResource(id = R.dimen.padding_small))
                ) {
                    Text(
                        text = completionRate.unlearnedVocabulary.toString(),
                        style = TypeText.h8,
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
                    modifier = Modifier.size(80.dp, 80.dp)
                )
            }
        }
    }
}

