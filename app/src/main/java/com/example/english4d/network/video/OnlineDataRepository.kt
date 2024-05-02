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
    val json = Json{
        ignoreUnknownKeys = true
    }
    private val BaseUrl1 = "https://caption-youtube.onrender.com/"
    private val BaseUrl2 = "https://thanhhust.x10.mx/jetpack/"
    private val retrofitCaptionTrack: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .client(okHttpClient)
        .baseUrl(BaseUrl1)
        .build()
    private val retrofitChannelInfo: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .client(okHttpClient)
        .baseUrl(BaseUrl2)
        .build()
    private val retrofitServerCaptionTrack :ApiCaptionTrack by lazy {
        retrofitCaptionTrack.create(ApiCaptionTrack::class.java)
    }
    private val retrofitServerChannelInfo : ApiChannel by lazy{
        retrofitChannelInfo.create(ApiChannel::class.java)
    }
    override val videoRepository: VideoRepository by lazy {
        NetworkCaptionTrackRepository(retrofitServerCaptionTrack, retrofitServerChannelInfo)
    }

}