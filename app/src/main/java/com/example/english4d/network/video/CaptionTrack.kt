package com.example.english4d.network.video

import kotlinx.serialization.Serializable

@Serializable
data class CaptionTrack(
    val transcript: List<ItemCaptionTrack>
)

@Serializable
data class ItemCaptionTrack(
    val text: String,
    val start: Float,
    val duration: Float
)
