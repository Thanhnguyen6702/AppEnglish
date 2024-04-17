package com.example.english4d.ui.topic

import com.example.english4d.data.database.vocabulary.CompletionRate

data class TopicUiState(
    val listTopic: List<Pair<String,List<ItemTopic>>> = listOf(),
    val completionRates : Map<Int,CompletionRate> = mapOf()
)

data class ItemTopic(
    val title: String,
    val id: Int,
    val topic: String,
    val image: String,
    val id_theme: Int,
)