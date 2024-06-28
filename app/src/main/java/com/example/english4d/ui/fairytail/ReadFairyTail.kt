package com.example.english4d.ui.fairytail

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
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

@OptIn(ExperimentalMaterial3Api::class)
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
            var openSetting by remember {
                mutableStateOf(false)
            }
            val sheetState = rememberModalBottomSheetState(
                skipPartiallyExpanded = true
            )
            val fairyTail =
                (uiState as FairyTailUiState.Success).fairyTail[viewmodel.optionSelected.intValue]
            val selected = remember {
                mutableStateListOf(*Array(fairyTail.fairy_english.content.size) { false })
            }
            DisposableEffect(key1 = Unit) {
                onDispose {
                    viewmodel.stopAudio()
                }
            }
            Scaffold(

                topBar = {
                    TopAppBar(
                        title = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(onClick = {
                                    navController.popBackStack()
                                }) {
                                    Icon(
                                        imageVector = Icons.Filled.ArrowBackIosNew,
                                        contentDescription = null
                                    )
                                }
                                Text(text = "Truyện ngụ ngôn")
                                IconButton(onClick = { openSetting = true }) {
                                    Icon(
                                        imageVector = Icons.Filled.Settings,
                                        contentDescription = null
                                    )
                                }
                            }
                        })
                }
            ) {
                Column(
                    modifier = Modifier.padding(
                        top = it.calculateTopPadding(),
                        start = dimensionResource(id = R.dimen.padding_hight),
                        end = dimensionResource(id = R.dimen.padding_hight),
                        bottom = it.calculateTopPadding()
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
                                viewmodel = viewmodel,
                                contentE = fairyTail.fairy_english.content[it],
                                contentV = fairyTail.fairy_vietnamese.content[it],
                                isSelected = selected[it],
                                isAudio = (uiState as FairyTailUiState.Success).isAudio
                            ) {
                                selected[it] = !selected[it]
                            }
                        }
                    }
                }
            }
            if (openSetting) {
                ModalBottomSheet(
                    onDismissRequest = { openSetting = false },
                    sheetState = sheetState
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(dimensionResource(id = R.dimen.padding_hight))
                    ) {
                        Text(
                            text = "Cài đặt",
                            style = TypeText.h4.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_hight))
                        )
                        Text(
                            text = "Cỡ chữ", style = TypeText.h6, modifier = Modifier.padding(
                                top = dimensionResource(
                                    id = R.dimen.padding_hight
                                ),
                                bottom = dimensionResource(id = R.dimen.padding_medium)
                            )
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = dimensionResource(id = R.dimen.padding_medium)),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Card(
                                colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.green_100)),
                                shape = MaterialTheme.shapes.small
                            ) {
                                Text(
                                    text = "1X",
                                    style = TypeText.h6.copy(
                                        fontWeight = FontWeight.Medium,
                                        color = colorResource(id = R.color.white)
                                    ),
                                    modifier = Modifier.padding(
                                        vertical = dimensionResource(id = R.dimen.padding_medium),
                                        horizontal = dimensionResource(
                                            id = R.dimen.padding_hight
                                        )
                                    )
                                )
                            }
                            Card(
                                colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.green_100)),
                                shape = MaterialTheme.shapes.small
                            ) {
                                Text(
                                    text = "2X",
                                    style = TypeText.h6.copy(
                                        fontWeight = FontWeight.Medium,
                                        color = colorResource(id = R.color.white)
                                    ),
                                    modifier = Modifier.padding(
                                        vertical = dimensionResource(id = R.dimen.padding_medium),
                                        horizontal = dimensionResource(
                                            id = R.dimen.padding_hight
                                        )
                                    )
                                )
                            }
                            Card(
                                colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.green_100)),
                                shape = MaterialTheme.shapes.small
                            ) {
                                Text(
                                    text = "3X",
                                    style = TypeText.h6.copy(
                                        fontWeight = FontWeight.Medium,
                                        color = colorResource(id = R.color.white)
                                    ),
                                    modifier = Modifier.padding(
                                        vertical = dimensionResource(id = R.dimen.padding_medium),
                                        horizontal = dimensionResource(
                                            id = R.dimen.padding_hight
                                        )
                                    )
                                )
                            }
                            Card(
                                colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.green_100)),
                                shape = MaterialTheme.shapes.small
                            ) {
                                Text(
                                    text = "4X",
                                    style = TypeText.h6.copy(
                                        fontWeight = FontWeight.Medium,
                                        color = colorResource(id = R.color.white)
                                    ),
                                    modifier = Modifier.padding(
                                        vertical = dimensionResource(id = R.dimen.padding_medium),
                                        horizontal = dimensionResource(
                                            id = R.dimen.padding_hight
                                        )
                                    )
                                )
                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = dimensionResource(id = R.dimen.padding_hight)),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Đọc truyện bằng giọng máy", style = TypeText.h6)
                            Switch(
                                checked = (uiState as FairyTailUiState.Success).isAudio,
                                onCheckedChange = {
                                    viewmodel.setAudio(it)
                                })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemReadFairyLayout(
    viewmodel: FairyTailViewModel,
    contentE: String,
    contentV: String,
    isSelected: Boolean,
    isAudio: Boolean = false,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = contentE, style = TypeText.h6, modifier = Modifier.clickable(onClick = onClick))
        Box(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))) {
            if (isSelected)
                Text(
                    text = contentV,
                    style = TypeText.h7
                )
        }
        if (isAudio) {
            IconButton(onClick = { viewmodel.playAudio(contentE) }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                    contentDescription = null,
                    tint = colorResource(
                        id = R.color.green_100
                    )
                )
            }
        }
    }
}



