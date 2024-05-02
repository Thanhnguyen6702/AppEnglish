package com.example.english4d.network.video

import kotlinx.serialization.Serializable

@Serializable
data class ChannelInfo (
    val channel: Channel,
    val videos: List<Video>
)
@Serializable
data class Channel(
    val id: String,
    val title: String,
    val image: String
)
@Serializable
data class Video(
    val videoId: String,
    val title: String,
    val imageVideo: String
)