package com.example.english4d.ui.video

import com.example.english4d.network.video.CaptionTrack


data class CaptionTrackSeparation(
    val transcript: List<ItemCaptionTrackSeparation>,
    val indexItem: Int = 0
)
data class ItemCaptionTrackSeparation(
    val listWord: List<String>,
    val posHide: List<Int>,
    val indexStop:Int = 0,
    val start: Float,
    val duration: Float,
)
sealed class ListeningUiState{
    data class Success (
        val captionTrack: CaptionTrack = CaptionTrack(listOf()),
        val captionTrackSeparation: CaptionTrackSeparation = CaptionTrackSeparation(listOf()),
        val answers: Pair<String,List<String>> = Pair("", listOf())
    ):ListeningUiState()
    object Loading : ListeningUiState()
    object Error : ListeningUiState()
}
