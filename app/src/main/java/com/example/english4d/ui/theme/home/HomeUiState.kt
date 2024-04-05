package com.example.english4d.ui.theme.home

import com.example.english4d.data.database.vocabulary.Vocabulary

data class HomeUiState (
    val numberLearned: Int = 0,
    val unlearned: List<Vocabulary> = listOf() ,
    val learning: List<Vocabulary> = listOf() ,
    val master: List<Vocabulary> = listOf(),
)