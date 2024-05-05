package com.example.english4d.ui.video

import com.example.english4d.network.video.ChannelInfo

sealed class VideoUiState{
    data class Success(
        val channels: List<ChannelInfo> = listOf()
    ): VideoUiState()
    object Loading : VideoUiState()
    object Error : VideoUiState()
}

