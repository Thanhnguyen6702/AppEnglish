package com.example.english4d.network.video

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiCaptionTrack {
    @GET("getCaptionTrack.php")
    suspend fun getCaptionTrack(
        @Query("video_id") video_id: String
    ):CaptionTrack
}