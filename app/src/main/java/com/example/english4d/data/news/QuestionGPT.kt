package com.example.english4d.data.news

import kotlinx.serialization.Serializable

@Serializable
data class QuestionGPT(
    val question: String,
    val options: Map<String,String>,
    val answer: String,
    val explanation: String
)
