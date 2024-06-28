package com.example.english4d.ui.fairytail

import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.english4d.network.fairytail.FairyTail

sealed class FairyTailUiState {
    data class Success(
        val fairyTail: List<FairyTail>,
        val isAudio: Boolean = false,
        val textSize:TextUnit = 18.sp
    ) : FairyTailUiState()
    object Loading : FairyTailUiState()
    object Error : FairyTailUiState()
}