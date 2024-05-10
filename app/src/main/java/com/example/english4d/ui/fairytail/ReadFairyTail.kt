package com.example.english4d.ui.fairytail

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.english4d.R
import com.example.english4d.ui.animation.ErrorScreen
import com.example.english4d.ui.animation.LoadingScreen
import com.example.english4d.ui.theme.TypeText

@SuppressLint("MutableCollectionMutableState")
@Composable
fun ReadFairyTail(
    navController: NavController,
    viewmodel: FairyTailViewModel
) {
    val uiState by viewmodel.uiState.collectAsState()
    when (uiState) {
        is FairyTailUiState.Error -> ErrorScreen()
        is FairyTailUiState.Loading -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LoadingScreen()
        }

        is FairyTailUiState.Success -> {
            val fairyTail =
                (uiState as FairyTailUiState.Success).fairyTail[viewmodel.optionSelected.intValue]
            val selected = remember {
                mutableStateListOf(*Array(fairyTail.fairy_english.content.size) { false })
            }
            Scaffold {
                Column(
                    modifier = Modifier.padding(
                        top = it.calculateTopPadding(),
                        start = dimensionResource(id = R.dimen.padding_hight),
                        end = dimensionResource(id = R.dimen.padding_hight)
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = dimensionResource(id = R.dimen.padding_hight),
                                end = dimensionResource(id = R.dimen.padding_hight),
                                bottom = dimensionResource(id = R.dimen.padding_small)
                            ),
                        text = fairyTail.fairy_english.name,
                        style = TypeText.h4.copy(fontWeight = FontWeight.Medium),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    LazyColumn {
                        item {
                            AsyncImage(
                                modifier = Modifier
                                    .padding(vertical = dimensionResource(id = R.dimen.padding_hight))
                                    .clip(MaterialTheme.shapes.small)
                                    .fillMaxWidth()
                                    .height(200.dp),
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(fairyTail.fairy_english.href).build(),
                                contentDescription = null,
                                placeholder = painterResource(id = R.drawable.loading_img),
                                contentScale = ContentScale.Crop
                            )
                        }
                        items(fairyTail.fairy_english.content.size) {
                            ItemReadFairyLayout(
                                contentE = fairyTail.fairy_english.content[it],
                                contentV = fairyTail.fairy_vietnamese.content[it],
                                isSelected = selected[it]
                            ) {
                                selected[it] = !selected[it]
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemReadFairyLayout(
    contentE: String,
    contentV: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column {
        Text(text = contentE, style = TypeText.h6, modifier = Modifier.clickable(onClick = onClick))
        Box(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))) {
            if (isSelected)
                Text(
                    text = contentV,
                    style = TypeText.h7
                )
        }
    }
}