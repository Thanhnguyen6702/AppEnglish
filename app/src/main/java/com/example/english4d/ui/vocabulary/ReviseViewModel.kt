package com.example.english4d.ui.vocabulary

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ReviseViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ReviseUiState())
    val uiState: StateFlow<ReviseUiState> = _uiState.asStateFlow()


    init {

    }
    fun initVocab(){

    }
}