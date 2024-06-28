package com.example.english4d.network.video

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

interface AppContainer{
    val videoRepository: VideoRepository
}
class OnlineVideoRepository: AppContainer {
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)  // Thời gian chờ kết nối là 30 giây
        .readTimeout(30, TimeUnit.SECONDS)  // Thời gian chờ đọc là 30 giây
        .writeTimeout(30, TimeUnit.SECONDS)  // Thời gian chờ ghi là 30 giây
        .build()
    private val BaseUrl = "https://thanhhust.x10.mx/jetpack/"
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .client(okHttpClient)
        .baseUrl(BaseUrl)
        .build()
    private val retrofitServerCaptionTrack :ApiCaptionTrack by lazy {
        retrofit.create(ApiCaptionTrack::class.java)
    }
    private val retrofitServerChannelInfo : ApiChannel by lazy{
        retrofit.create(ApiChannel::class.java)
    }
    override val videoRepository: VideoRepository by lazy {
        NetworkCaptionTrackRepository(retrofitServerCaptionTrack, retrofitServerChannelInfo)
    }
}