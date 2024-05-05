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
    private val _uiState = MutableStateFlow<ListeningUiState>(ListeningUiState.Loading)
    val uiState: StateFlow<ListeningUiState> = _uiState.asStateFlow()
    private val captionTrackRepository = OnlineVideoRepository().videoRepository
    fun getCaptionTrack(videoId :String){
        viewModelScope.launch {
            try {
                _uiState.value = ListeningUiState.Loading
                val transcript = captionTrackRepository.getCaptionTrack(videoId).transcript.filterNot { it.text[0] =='[' }
                _uiState.value = ListeningUiState.Success(captionTrack = CaptionTrack(transcript = transcript))
            }catch (e:Exception){
                _uiState.value = ListeningUiState.Error
            }

        }
    }
}