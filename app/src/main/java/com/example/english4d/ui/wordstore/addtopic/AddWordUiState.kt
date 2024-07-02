package com.example.english4d.ui.wordstore.addtopic

import com.example.english4d.data.database.wordstore.DictionaryResponse

data class AddWordUiState (
    val title: String = "",
    val contentSearch: String = "",
    val resultSearch: List<WordCard> = emptyList(),
    val wordResult: List<DictionaryResponse> = listOf()
)