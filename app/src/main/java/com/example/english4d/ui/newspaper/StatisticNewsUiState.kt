package com.example.english4d.ui.newspaper

import com.example.english4d.data.database.question.Question

data class StatisticNewsUiState (
    val numberCorrect: Int = 0,
    val totalQuestion: Int = 1,
    val questionsFailed: List<Pair<Int,Question>> = listOf(),
    val posQuestion: Int = 0,
)