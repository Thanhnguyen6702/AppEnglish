package com.example.english4d.ui.video

import com.example.english4d.network.video.ChannelInfo

data class VideosUiState(
    val channels: List<ChannelInfo> = listOf()
)
