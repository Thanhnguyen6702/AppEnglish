package com.example.english4d.ui.video

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.english4d.R
import com.example.english4d.navigation.Screen
import com.example.english4d.ui.theme.TypeText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeeMoreVideoScreen(
    navController: NavController,
    viewModel: VideoViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val channel = (uiState as VideoUiState.Success).channels[viewModel.channelSelected]
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                            contentDescription = null
                        )
                    }
                    Text(
                        modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_hight)).fillMaxWidth(),
                        text = channel.channel.title,
                        style = TypeText.h4.copy(fontWeight = FontWeight.Medium),
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            })
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {
            items(channel.videos) {
                ItemSeeMoreVideoLayout(image = it.imageVideo, title = it.title) {
                    navController.navigate(Screen.Video.passId(it.videoId))
                }
            }
        }
    }
}

@Composable
fun ItemSeeMoreVideoLayout(
    image: String,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = dimensionResource(id = R.dimen.padding_hight))
            .fillMaxWidth()
            .height(96.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.padding_hight))
                .size(width = 100.dp, height = 80.dp)
                .clip(MaterialTheme.shapes.medium),
            model = ImageRequest.Builder(LocalContext.current).data(image).build(),
            contentDescription = null,
            placeholder = painterResource(
                id = R.drawable.loading_img
            ),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .padding(end = dimensionResource(id = R.dimen.padding_hight))
                .height(80.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = title,
                style = TypeText.h5.copy(fontWeight = FontWeight.Medium),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "Gần đây", style = TypeText.h7.copy(
                    fontWeight = FontWeight.Medium, color = colorResource(
                        id = R.color.gray_100
                    )
                )
            )
        }
    }
}
