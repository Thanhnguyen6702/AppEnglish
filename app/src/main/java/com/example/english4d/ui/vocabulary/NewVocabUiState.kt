package com.example.english4d.ui.vocabulary

import com.example.english4d.data.database.vocabulary.Definitions
import com.example.english4d.data.database.vocabulary.Examples
import com.example.english4d.data.database.vocabulary.Vocabulary

data class NewVocabUiState (
    val title: String = "",
    val vocabulary: Vocabulary = Vocabulary(1,"","","","",1),
    val examples: List<Examples> = listOf(),
    val definitions: List<Definitions> = listOf()
)