package com.example.english4d.ui.newspaper

data class StatisticNewsUiState (
    val numberCorrect: Int = 0,
    val totalQuestion: Int = 1,
    val questionsFailed: List<Pair<Int,String>> = listOf()
)