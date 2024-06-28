package com.example.english4d.ui.fairytail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.english4d.R
import com.example.english4d.navigation.ExtensionGraphScreen
import com.example.english4d.ui.animation.ErrorScreen
import com.example.english4d.ui.animation.LoadingScreen
import com.example.english4d.ui.theme.TypeText

@Composable
fun FairyTailScreen(
    navController: NavController,
    viewmodel: FairyTailViewModel = viewModel()
) {
    val uiState by viewmodel.uiState.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_DESTROY) {
                viewmodel.stopAudio()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    Scaffold { paddingValues ->
        Surface(
            modifier = Modifier.padding(paddingValues)
        ) {
            when (uiState) {
                is FairyTailUiState.Error -> ErrorScreen()
                is FairyTailUiState.Loading -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    LoadingScreen()
                }
                is FairyTailUiState.Success -> {
                    LazyColumn {
                        items((uiState as FairyTailUiState.Success).fairyTail.size) {
                            ItemFairyLayout(title = (uiState as FairyTailUiState.Success).fairyTail[it].fairy_english.name, image = (uiState as FairyTailUiState.Success).fairyTail[it].fairy_english.href){
                                viewmodel.selectItem(it)
                                navController.navigate(ExtensionGraphScreen.ReadFairy.route)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemFairyLayout(
    title: String,
    image: String,
    onClick: ()->Unit
) {
    Card(
        modifier = Modifier
            .padding(
                horizontal = dimensionResource(id = R.dimen.padding_hight),
                vertical = dimensionResource(
                    id = R.dimen.padding_medium
                )
            )
            .fillMaxWidth(),
        onClick = onClick
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = TypeText.h6.copy(fontWeight = FontWeight.Medium),
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        dimensionResource(id = R.dimen.padding_hight)
                    )
            )
            AsyncImage(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_medium))
                    .clip(MaterialTheme.shapes.small)
                    .size(72.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image).build(), contentDescription = null,
                placeholder = painterResource(id = R.drawable.loading_img),
                contentScale = ContentScale.Crop
            )
        }
    }
}