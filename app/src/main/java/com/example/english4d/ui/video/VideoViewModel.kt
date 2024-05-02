package com.example.english4d.ui.video

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.english4d.network.video.ChannelInfo
import com.example.english4d.network.video.OnlineVideoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


private val channelsId = listOf(
    "UCNbeSPp8RYKmHUliYBUDizg",
    "UCVBErcpqaokOf4fI5j73K_w",
    "UCz4tgANd4yy8Oe0iXCdSWfA",
    "UCxJGMJbjokfnr2-s4_RXPxQ",
    "UCeTVoczn9NOZA9blls3YgUg"
)

class VideoViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(VideosUiState())
    val uiState: StateFlow<VideosUiState> = _uiState.asStateFlow()
    private val onlineVideoRepository = OnlineVideoRepository().videoRepository
    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val channelsInfo = mutableListOf<ChannelInfo>()
                channelsId.forEach{
                    val channelInfo = onlineVideoRepository.getChannelInfo(it)
                    channelsInfo.add(channelInfo)
                }
                _uiState.value = _uiState.value.copy(channelsInfo)
            }
        }
    }




    //// API V3 Youtube
//    private val httpTransport = NetHttpTransport()
//    private val jsonFactory: JacksonFactory = JacksonFactory.getDefaultInstance()
//
//    private val youtube: YouTube = YouTube.Builder(httpTransport, jsonFactory, null)
//        .setApplicationName("English4D")
//        .setYouTubeRequestInitializer(YouTubeRequestInitializer(BuildConfig.apiYoutube))
//        .build()
//
//    init {
//        viewModelScope.launch {
//            withContext(Dispatchers.IO) {
//                try {
//                    val channels = mutableListOf<ItemChannel>()
//                    channelsId.forEach {
//                        channels.add(getChannel(it))
//                    }
//                    _uiState.value = _uiState.value.copy(channels = channels)
//                } catch (e: Exception) {
//                    Log.e("ERROR", e.toString())
//                }
//
//            }
//        }
//    }

//    suspend fun getChannel(id: String): ItemChannel {
//        val videos = getListVideo(id)
//        val searchChannel = youtube.channels().list("snippet")
//        searchChannel.id = id
//        val response = searchChannel.execute()
//        val item = response.items[0]
//        return ItemChannel(
//            title = item.snippet.title,
//            imageChannel = item.snippet.thumbnails.medium.url,
//            videos = videos
//        )
//    }
//
//    suspend fun getListVideo(id: String): List<ItemVideo> {
//        val search = youtube.search().list("snippet")
//        search.channelId = id
//        search.maxResults = 10
//        search.publishedAfter = DateTime("2023-01-01T00:00:00Z")
//        val response = search.execute()
//        val items = response.items
//        val videos = mutableListOf<ItemVideo>()
//        items.forEach {
//            if (it.id.videoId != null) {
//                videos.add(
//                    ItemVideo(
//                        imageVideo = it.snippet.thumbnails.medium.url,
//                        videoId = it.id.videoId,
//                        title = it.snippet.title
//                    )
//                )
//            }
//        }
//        return videos
//    }
}