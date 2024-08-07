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


class VideoViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<VideoUiState>(VideoUiState.Loading)
    val uiState: StateFlow<VideoUiState> = _uiState.asStateFlow()
    private val onlineVideoRepository = OnlineVideoRepository().videoRepository
    var channelSelected: Int = 0
    private set
    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                try {
                    val channels = onlineVideoRepository.getChannels()
                    val channelsInfo = mutableListOf<ChannelInfo>()
                    channels.forEach{
                        val channelInfo = onlineVideoRepository.getChannelInfo(it.id)
                        channelsInfo.add(channelInfo)
                    }
                    _uiState.value = VideoUiState.Success(channelsInfo)
                }catch (e:Exception){
                    _uiState.value = VideoUiState.Error
                }
            }
        }
    }
    fun setSelect(index:Int){
        channelSelected = index
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