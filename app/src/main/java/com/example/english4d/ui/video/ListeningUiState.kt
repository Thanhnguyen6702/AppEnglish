package com.example.english4d.ui.video

import com.example.english4d.network.video.CaptionTrack

data class ListeningUiState (
    val captionTrack: CaptionTrack = CaptionTrack(listOf())
)