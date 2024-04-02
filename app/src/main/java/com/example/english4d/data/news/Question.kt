package com.example.english4d.data.news

import kotlinx.serialization.Serializable

@Serializable
data class Question(
    val question: String,
    val options: Map<String,String>,
    val answer: String,
    val explanation: String
)
