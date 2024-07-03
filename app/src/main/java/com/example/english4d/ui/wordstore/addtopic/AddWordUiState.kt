package com.example.english4d.ui.wordstore.addtopic

import com.example.english4d.data.database.wordstore.DictionaryResponse
import com.example.english4d.data.database.wordstore.MyWord
import com.example.english4d.data.database.wordstore.MyWordWithDetails
import com.example.english4d.ui.wordstore.WordCard

data class AddWordUiState (
    val title: String = "",
    val contentSearch: String = "",
    val topic: String = "",
    val resultSearch: List<WordCard> = listOf(),
    val wordResult: List<DictionaryResponse> = listOf(),
    val listItem: List<MyWord> = emptyList(),
    val position: Int = 0,
    val listItemDetail: List<MyWordWithDetails> = listOf()
)