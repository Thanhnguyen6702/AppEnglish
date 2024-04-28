package com.example.english4d.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.english4d.R
import com.example.english4d.data.database.vocabulary.Vocabulary
import com.example.english4d.ui.theme.TypeText


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun WordsBookScreen(
    index: Int,
    navController: NavController,
    viewModel: HomeViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(topBar = {
        TopAppBar(title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = null
                    )
                }
                Text(
                    text = "Sổ từ của bạn", style = TypeText.bodyMedium.copy(
                        color = colorResource(
                            id = R.color.purple_200
                        )
                    ), textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()
                )
            }
        })
    }) { paddingValues ->
        val tabItems = listOf("Từ chưa thuộc", "Từ sắp thuộc", "Từ đã thuộc")
        var selectedTabIndex by remember { mutableIntStateOf(index) }
        val pagerState = rememberPagerState (
            initialPage = index,
            pageCount = {tabItems.size}
        )
        Column(
            verticalArrangement = Arrangement.Top
        ) {
            LaunchedEffect(selectedTabIndex) {
                pagerState.animateScrollToPage(selectedTabIndex)
            }
            LaunchedEffect(pagerState.currentPage ) {
                selectedTabIndex = pagerState.currentPage
            }
            TabRow(selectedTabIndex = selectedTabIndex, modifier = Modifier.padding(paddingValues)) {
                tabItems.forEachIndexed { index, title ->
                    Tab(
                        modifier = Modifier.fillMaxWidth(1f / tabItems.size),
                        selected = index == selectedTabIndex, onClick = {
                            selectedTabIndex = index
                        }) {
                        Text(text = title)
                    }
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) { index ->
                TabContent(
                    items = when (index) {
                        0 -> uiState.unlearned
                        1 -> uiState.learning
                        else -> uiState.master
                    },
                    type = index
                )
            }
        }

    }
}

@Composable
fun ItemWordBook(
    vocabulary: Vocabulary,
    type: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                contentDescription = null,
                tint = colorResource(
                    id = R.color.green_100
                ),
                modifier = Modifier.padding(
                    top = dimensionResource(id = R.dimen.padding_hight),
                    bottom = dimensionResource(id = R.dimen.padding_hight),
                    end = dimensionResource(id = R.dimen.padding_medium),
                    start = dimensionResource(id = R.dimen.padding_hight)
                )
            )
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = vocabulary.english,
                    style = TypeText.h6.copy(fontWeight = FontWeight.Bold)
                )
                Text(text = vocabulary.vietnamese, style = TypeText.h7)
            }
        }
        Icon(
            imageVector = Icons.Filled.CheckCircle, contentDescription = null,
            modifier = Modifier.padding(end = dimensionResource(id = R.dimen.padding_hight)),
            tint = colorResource(
                id = when (type) {
                    0 -> R.color.orange_red
                    1 -> R.color.orange_50
                    else -> R.color.green_100
                }
            )
        )
    }
}

@Composable
fun TabContent(modifier:Modifier = Modifier,items: List<Vocabulary>, type: Int) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.Top
    ) {
        items(items) { item ->
            ItemWordBook(vocabulary = item, type = type)
        }
    }
}