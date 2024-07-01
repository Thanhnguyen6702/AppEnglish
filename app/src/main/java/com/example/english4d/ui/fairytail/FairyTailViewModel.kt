package com.example.english4d.ui.fairytail

import android.content.Context
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.english4d.network.fairytail.OnlineFairyTailDataRepository
import com.example.english4d.utils.TextToSpeechManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FairyTailViewModel(context: Context) : ViewModel() {
    private val _uiState = MutableStateFlow<FairyTailUiState>(FairyTailUiState.Loading)
    val uiState = _uiState.asStateFlow()
    private val tts = TextToSpeechManager.getInstance(context)
    var optionSelected = mutableIntStateOf(0)
        private set
    private val onlineFairyTail = OnlineFairyTailDataRepository().fairyTailRepository

    init {
        viewModelScope.launch {
                val data = onlineFairyTail.getFairyTail()
                _uiState.value = FairyTailUiState.Success(fairyTail = data)
        }
    }
    fun selectItem(index: Int){
        optionSelected.intValue = index
    }
    fun setAudio(isAudio: Boolean){
        val currentState = (_uiState.value as FairyTailUiState.Success)
        _uiState.value = currentState.copy(isAudio = isAudio)
    }
    fun playAudio(text: String){
        tts.speak(text)
    }
    fun stopAudio(){
        tts.stop()
    }
    fun setTextSize(sizeLevel: Float){
        val currentState = (_uiState.value as FairyTailUiState.Success)
        _uiState.value = currentState.copy(sizeLevel = sizeLevel)
    }
}