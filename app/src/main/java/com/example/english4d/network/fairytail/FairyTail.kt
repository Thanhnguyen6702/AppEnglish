package com.example.english4d.network.fairytail

import kotlinx.serialization.Serializable

@Serializable
data class FairyTail (
    val fairy_english: DataFairy,
    val fairy_vietnamese: DataFairy
)
@Serializable
data class DataFairy(
    val name: String,
    val content: List<String>,
    val href: String
)
