package com.example.english4d.ui.newspaper

data class ItemQuestionUiState(
    val answerSelected: MutableMap<Int,String> = mutableMapOf(),
    val questionNumber:Int = 0
)