package com.example.english4d.ui.theme.newspaper

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.english4d.R
import com.example.english4d.data.news.NewsContent
@Composable
fun ReadNewsScreen(
    topic: String,
    url: String
){
    val newsViewmodel: NewsViewmodel = viewModel(factory = NewsViewmodel.Factory)
    newsViewmodel.getNewsPaper(url)
    val uiState = newsViewmodel.newsContent
    ReadNewsLayout(uiState =uiState , topic = topic )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadNewsLayout(
    uiState: ContentUiState,
    topic: String,
) {
    val listItem = uiState.contentNews
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
                .fillMaxSize()
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
                style = MaterialTheme.typography.bodyMedium,
                modifier = modifier,
                color = Color.Black
            )
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun PreviewLayout() {
//    val listItem = listOf(
//        "The Vietnamese restaurant stands among six eateries across the United States selected for the 2024 America's Classics Award, given to \"locally owned restaurants that serve quality food, have timeless appeal, and reflect the character of their communities.\"",
//        "https://i1-english.vnecdn.net/2024/03/14/392856095-18413839876040437-54-5934-5267-1710408514.jpg?w=680&h=0&q=100&dpr=1&fit=crop&s=-PA2hhUqXE1__9n_6D5xjA",
//        "Established by Thuyen Luu and Nhu Lai in 1984, Vietnam Restaurant sprouted its roots just five years after the couple immigrated to the U.S. from Vietnam with only $30, as noted by The Philadelphia Inquirer.",
//        "The restaurant is known for its wide range of selections, including barbecue platters, crispy spring rolls, vermicelli noodle bowls, soups and stir-fries.",
//        "The James Beard Foundation hailed it as \"one of Philly’s favorite date-night destinations for classic Vietnamese cuisine,\" and \"a model of consistency for decades\", as reported by Eater Philly."
//    )
//    ReadNewsScreen("Du lịch", listItem = listItem)
//}