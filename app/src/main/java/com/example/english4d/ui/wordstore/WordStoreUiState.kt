package com.example.english4d.ui.wordstore

import com.example.english4d.data.database.wordstore.DictionaryResponse
import com.example.english4d.data.database.wordstore.MyWordTopic
import com.example.english4d.data.database.wordstore.MyWordWithDetails
import com.example.english4d.data.database.wordstore.TopicWithWords

data class WordStoreUiState (
    val showRemoveTopic: Boolean = false,
    val showRemoveWord: Boolean = false,
    val showRemoveWordResult: Boolean = false,
    val title: String = "",
    val contentSearch: String = "",
    val topic: String = "",
    val numberLoading1: Int = 0,
    val numberLoading2: Int = 0,
    val resultSearch: List<String> = listOf(),
    val wordResult: List<DictionaryResponse> = listOf(),
    val topicWithWords: TopicWithWords = TopicWithWords( MyWordTopic(name = ""), listOf()),
    val position: Int = 0,
    val listItemDetail: List<MyWordWithDetails> = listOf()
)