package com.example.english4d.ui.home

import com.example.english4d.data.database.vocabulary.Topics
import com.example.english4d.data.database.vocabulary.Vocabulary

data class HomeUiState (
    val numberLearned: Int = 0,
    var unlearned: List<Vocabulary> = listOf(),
    val learning: List<Vocabulary> = listOf(),
    val master: List<Vocabulary> = listOf(),
    val revise: List<Vocabulary> = listOf(),
    val newVocab: Pair<Topics,List<Vocabulary>> = Pair(Topics(0,"","",0),listOf()),
    val isRevise: Boolean = true
)