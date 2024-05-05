package com.example.english4d.ui.video

import com.example.english4d.network.video.CaptionTrack

sealed class ListeningUiState{
    data class Success (
        val captionTrack: CaptionTrack = CaptionTrack(listOf())
    ):ListeningUiState()
    object Loading : ListeningUiState()
    object Error : ListeningUiState()
}
