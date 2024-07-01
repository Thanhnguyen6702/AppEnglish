package com.example.english4d.ui.fairytail

import com.example.english4d.network.fairytail.FairyTail

sealed class FairyTailUiState {
    data class Success(
        val fairyTail: List<FairyTail>,
        val isAudio: Boolean = false,
        val sizeLevel: Float = 1f
    ) : FairyTailUiState()
    object Loading : FairyTailUiState()
    object Error : FairyTailUiState()
}