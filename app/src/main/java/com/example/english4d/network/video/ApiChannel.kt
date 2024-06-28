package com.example.english4d.network.video

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiChannel {
    @GET("getVideo.php")
    suspend fun getChannelInfo(
        @Query("channelId") channelId: String
    ): ChannelInfo

    @GET("getChannel.php")
    suspend fun getChannels(): List<Channel>
}