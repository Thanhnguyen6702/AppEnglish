package com.example.english4d.ui.video

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PauseCircle
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.english4d.R
import com.example.english4d.ui.animation.ErrorScreen
import com.example.english4d.ui.animation.LoadingScreen
import com.example.english4d.ui.theme.TypeText
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun ListeningScreen(
    viewModel: ListeningViewModel = viewModel(),
    videoId: String,
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()
    // Lưu trữ tham chiếu tới YouTubePlayer
    var youtubePlayerRef by remember { mutableStateOf<YouTubePlayer?>(null) }
    var postionCaptionTrack by remember {
        mutableIntStateOf(0)
    }
    var videoPosition by remember {
        mutableStateOf(0f)
    }
    LaunchedEffect(key1 = videoId) {
        viewModel.getCaptionTrack(videoId)
    }
    LaunchedEffect(postionCaptionTrack) {
        listState.animateScrollToItem(postionCaptionTrack)
    }

    Column {
        YoutubePlayer(
            lifecycleOwner = LocalLifecycleOwner.current,
            onYouTubePlayerReady = { youtubePlayer ->
                youtubePlayerRef = youtubePlayer
                youtubePlayer.addListener(object : AbstractYouTubePlayerListener() {
                    override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
                        videoPosition = second
                    }
                })
                youtubePlayer.loadVideo(videoId, 0f) // Khởi tạo video ban đầu
            }
        )
        when(uiState){
            ListeningUiState.Error -> ErrorScreen(){
                viewModel.getCaptionTrack(videoId)
            }
            ListeningUiState.Loading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LoadingScreen()
                }
            }
            is ListeningUiState.Success -> {
                LaunchedEffect(videoPosition) {
                    val currentCaptionIndex =
                        (uiState as ListeningUiState.Success).captionTrack.transcript.indexOfLast { videoPosition >= it.start }

                    if (currentCaptionIndex > 0) {
                        postionCaptionTrack = currentCaptionIndex
                    }
                }
                LazyColumn(
                    state = listState
                ) {
                    val captionTracks = (uiState as ListeningUiState.Success).captionTrack.transcript
                    items(captionTracks.size) {
                        ItemCaptionLayout(
                            captionTrack = captionTracks[it].text,
                            isPlay = postionCaptionTrack == it
                        ) {
                            youtubePlayerRef?.seekTo(captionTracks[it].start) // Tua video đến thời gian này
                            postionCaptionTrack = it
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun YoutubePlayer(
    lifecycleOwner: LifecycleOwner,
    onYouTubePlayerReady: (YouTubePlayer) -> Unit // Callback khi YouTubePlayer sẵn sàng
) {
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp)),
        factory = { context ->
            YouTubePlayerView(context).apply {
                lifecycleOwner.lifecycle.addObserver(this)

                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        onYouTubePlayerReady(youTubePlayer) // Truyền tham chiếu tới YouTubePlayer
                    }
                })
            }
        }
    )
}


@Composable
fun ItemCaptionLayout(
    isPlay: Boolean = false,
    captionTrack: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .background(
                color = if (isPlay) {
                    colorResource(R.color.green_50)
                } else {
                    colorResource(id = R.color.green_100)
                }
            )
            .fillMaxWidth()
            .padding(vertical = dimensionResource(id = R.dimen.padding_medium)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = captionTrack,
            style = TypeText.h5.copy(
                fontWeight = FontWeight.Medium,
                color = if (isPlay) {
                    colorResource(R.color.white)
                } else {
                    colorResource(id = R.color.gray_50)
                }
            ),
            modifier = Modifier
                .padding(
                    start = dimensionResource(
                        id = R.dimen.padding_medium
                    ),
                    end = dimensionResource(id = R.dimen.padding_hight)
                )
                .weight(1f)
        )
        IconButton(
            onClick = onClick,
            modifier = Modifier.padding(end = dimensionResource(id = R.dimen.padding_medium))
        ) {
            Icon(
                imageVector = if (isPlay) {
                    Icons.Outlined.PauseCircle
                } else {
                    Icons.Outlined.PlayCircle
                },
                contentDescription = null,
                tint = if (isPlay) {
                    colorResource(R.color.white)
                } else {
                    colorResource(id = R.color.gray_50)
                }
            )
        }
    }
}
