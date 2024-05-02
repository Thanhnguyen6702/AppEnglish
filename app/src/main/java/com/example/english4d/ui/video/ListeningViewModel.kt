package com.example.english4d.ui.video

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.english4d.network.video.CaptionTrack
import com.example.english4d.network.video.OnlineVideoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ListeningViewModel(): ViewModel() {
    private val _uiState = MutableStateFlow(ListeningUiState())
    val uiState: StateFlow<ListeningUiState> = _uiState.asStateFlow()
    private val captionTrackRepository = OnlineVideoRepository().videoRepository
    fun getCaptionTrack(videoId :String){
        viewModelScope.launch {
            val transcript = captionTrackRepository.getCaptionTrack(videoId).transcript.filterNot { it.text[0] =='[' }
            _uiState.value = _uiState.value.copy(captionTrack = CaptionTrack(transcript = transcript))
        }
    }
}