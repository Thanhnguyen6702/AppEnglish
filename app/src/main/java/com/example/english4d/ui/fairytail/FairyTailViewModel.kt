package com.example.english4d.ui.fairytail

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.english4d.network.fairytail.OnlineFairyTailDataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FairyTailViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<FairyTailUiState>(FairyTailUiState.Loading)
    val uiState = _uiState.asStateFlow()
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
}