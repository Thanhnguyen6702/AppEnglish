package com.example.english4d.ui.wordstore.addtopic

import com.example.english4d.data.database.wordstore.DictionaryResponse

data class AddWordUiState (
    val contentSearch: String = "",
    val resultSearch: List<WordCard> = emptyList(),
    val wordResult: DictionaryResponse = DictionaryResponse(null,null,null,null)
)