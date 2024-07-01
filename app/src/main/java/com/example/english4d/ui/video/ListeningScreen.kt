package com.example.english4d.ui.video

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.outlined.PauseCircle
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.english4d.R
import com.example.english4d.ui.AppViewModelProvider
import com.example.english4d.ui.animation.ErrorScreen
import com.example.english4d.ui.animation.LoadingScreen
import com.example.english4d.ui.theme.TypeText
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun ListeningScreen(
    viewModel: ListeningViewModel = viewModel(factory = AppViewModelProvider.Factory),
    videoId: String,
    mode: Int,
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()
    // Lưu trữ tham chiếu tới YouTubePlayer
    var youtubePlayerRef by remember { mutableStateOf<YouTubePlayer?>(null) }
    var positionCaptionTrack by remember {
        mutableIntStateOf(0)
    }
    var videoPosition by remember {
        mutableStateOf(0f)
    }
    var maxPosition by remember {
        mutableIntStateOf(0)
    }
    var isListened = remember{false}
    var isListening by remember  { mutableStateOf(false) }
    LaunchedEffect(key1 = videoId) {
        if (mode == 0) {
            viewModel.getCaptionTrack(videoId)
        } else if (mode == 1) {
            viewModel.getCaptionTrackWithSelectWord(videoId)
        }
    }
    LaunchedEffect(positionCaptionTrack) {
        if (positionCaptionTrack>2) {
            listState.animateScrollToItem(positionCaptionTrack-2)
        }
    }
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier.padding(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding()
            )
        ) {
            YoutubePlayer(
                lifecycleOwner = LocalLifecycleOwner.current,
                onYouTubePlayerReady = { youtubePlayer ->
                    youtubePlayerRef = youtubePlayer
                    youtubePlayer.addListener(object : AbstractYouTubePlayerListener() {
                        override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
                            videoPosition = second
                        }

                        override fun onStateChange(
                            youTubePlayer: YouTubePlayer,
                            state: PlayerConstants.PlayerState
                        ) {
                            if (state == PlayerConstants.PlayerState.PLAYING) {
                                isListening = true
                            }else if (state == PlayerConstants.PlayerState.PAUSED){
                                isListening = false
                            }
                        }
                    })
                    youtubePlayer.loadVideo(videoId, 0f) // Khởi tạo video ban đầu
                }
            )
            when (uiState) {
                ListeningUiState.Error -> ErrorScreen() {
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
                            positionCaptionTrack = currentCaptionIndex
                            maxPosition = maxOf(maxPosition,positionCaptionTrack)
                        }else{
                            positionCaptionTrack = 0
                        }
                    }

                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        if (mode == 0) {
                            LazyColumn(
                                state = listState
                            ) {

                                val captionTracks =
                                    (uiState as ListeningUiState.Success).captionTrack.transcript
                                items(captionTracks.size) {
                                    ItemCaptionLayout(
                                        captionTrack = captionTracks[it].text,
                                        isPlay = (positionCaptionTrack == it) && isListening
                                    ) {
//                                        youtubePlayerRef?.seekTo(captionTracks[it].start) // Tua video đến thời gian này
//                                        positionCaptionTrack = it
                                        if (positionCaptionTrack == it) {
                                            if (isListening) {
                                                youtubePlayerRef?.pause()
                                            } else {
                                                youtubePlayerRef?.play()
                                            }
                                        }else{
                                            youtubePlayerRef?.play()
                                            youtubePlayerRef?.seekTo(captionTracks[it].start)
                                        }
                                    }
                                }
                            }
                        }
                        if (mode == 1) {
                            val itemCaptionTrackSeparation =
                                (uiState as ListeningUiState.Success).captionTrackSeparation
                            val captionTracks = itemCaptionTrackSeparation.transcript
                            LaunchedEffect(key1 = positionCaptionTrack) {
                                    if (positionCaptionTrack == maxPosition) {
                                        //youtubePlayerRef?.seekTo(captionTracks[positionCaptionTrack-1].start)
                                        youtubePlayerRef?.pause()
                                    } else {
                                        isListened = false
                                    }
                            }
                            LazyColumn(
                                state = listState
                            ) {
                                items(captionTracks.size) {
                                    ItemCaptionSeparationLayout(
                                        captionTrack = viewModel.getDisplayText(
                                            captionTracks[it]
                                        ), isPlay = itemCaptionTrackSeparation.indexItem == it
                                    ) {
//                                        isListened = false
//                                        youtubePlayerRef?.seekTo(captionTracks[it].start) // Tua video đến thời gian này
//                                        positionCaptionTrack = it
                                    }
                                }
                                item {
                                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_hight)))
                                }
                            }
                            val answers = (uiState as ListeningUiState.Success).answers
                            val correctAnswer =
                                captionTracks[itemCaptionTrackSeparation.indexItem].listWord[captionTracks[itemCaptionTrackSeparation.indexItem].posHide[captionTracks[itemCaptionTrackSeparation.indexItem].indexStop]]
                            LaunchedEffect(key1 = correctAnswer) {
                                viewModel.shuffleAnswer(correctAnswer)
                            }
                            if (answers.second.isNotEmpty()) {
                                Column(
                                    modifier = Modifier.align(Alignment.BottomCenter)
                                ) {
                                    SelectWordSubtitleLayout(
                                        correct = answers.first,
                                        answers = answers.second,
                                        refresh = {
                                            isListened = false
                                            youtubePlayerRef?.seekTo(captionTracks[itemCaptionTrackSeparation.indexItem].start)
                                            youtubePlayerRef?.play()
                                        },
                                        skip = {
                                            viewModel.nextQuestion()
                                        }) {isCorrect->
                                        if (isCorrect){
                                            viewModel.nextQuestion()
                                        }
                                    }
                                }
                            }
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

@Composable
fun ItemCaptionSeparationLayout(
    isPlay: Boolean = false,
    captionTrack: AnnotatedString,
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

@Composable
fun SelectWordSubtitleLayout(
    answers: List<String>,
    correct: String,
    refresh: () -> Unit,
    skip: () -> Unit,
    onClick: (Boolean) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green_80)),
                onClick = refresh
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Filled.Refresh, contentDescription = null)
                    Text(
                        text = "Nghe lại",
                        style = TypeText.h8.copy(fontWeight = FontWeight.Medium)
                    )
                }
            }
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green_80)),
                onClick = skip
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Bỏ qua", style = TypeText.h8.copy(fontWeight = FontWeight.Medium))
                    Icon(imageVector = Icons.Filled.SkipNext, contentDescription = null)
                }
            }
        }
        Row(
            modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_small))
        ) {
            Row {
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green_50)),
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = dimensionResource(id = R.dimen.padding_small)),
                    onClick = {
                        onClick(answers[0] == correct)
                    },
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = answers[0],
                        style = TypeText.h7.copy(fontWeight = FontWeight.Medium)
                    )
                }
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green_50)),
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = dimensionResource(id = R.dimen.padding_small)),
                    onClick = {
                        onClick(answers[1] == correct)
                    },
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = answers[1],
                        style = TypeText.h7.copy(fontWeight = FontWeight.Medium)
                    )
                }
            }
        }
        Row {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green_50)),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = dimensionResource(id = R.dimen.padding_small)),
                onClick = {
                    onClick(answers[2] == correct)
                },
                shape = MaterialTheme.shapes.small
            ) {
                Text(text = answers[2], style = TypeText.h7.copy(fontWeight = FontWeight.Medium))
            }
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green_50)),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = dimensionResource(id = R.dimen.padding_small)),
                onClick = {
                    onClick(answers[3] == correct)
                },
                shape = MaterialTheme.shapes.small
            ) {
                Text(text = answers[3], style = TypeText.h7.copy(fontWeight = FontWeight.Medium))
            }
        }
    }
}
//@Preview(showBackground = true)
//@Composable
//fun PreviewListeningItem() {
//    ItemCaptionSeparationLayout(
//        captionTrack = getDisplayText(ItemCaptionTrackSeparation(
//            listWord = listOf(
//                "hello",
//                "hi",
//                "thanh",
//                "ne",
//                "cac",
//                "ban"
//            ), posHide = listOf(0, 2, 4), indexStop = 3, start = 0f, duration = 1f
//        )
//        )
//    ){
//
//    }
//}
