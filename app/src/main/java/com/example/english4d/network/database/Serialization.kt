package com.example.english4d.network.database

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VocabularyAPI (
    val id: Int,
    val english: String,
    val vietnamese: String,
    val pronunciation: String,
    val image: String,
    val id_topic: Int
)

@Serializable
data class TopicsAPI(
    @SerialName(value = "id")
    val id: Int,
    @SerialName(value = "topic")
    val topic: String,
    @SerialName(value = "image")
    val image: String,
    @SerialName(value = "id_theme")
    val id_theme: Int,
)
@Serializable
data class ThemeAPI(
    @SerialName(value = "id")
    val id: Int,
    @SerialName(value = "title")
    val title: String
)
