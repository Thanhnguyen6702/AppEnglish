package com.example.english4d.network.video

interface  VideoRepository {
    suspend fun getCaptionTrack(video_id: String): CaptionTrack
    suspend fun getChannelInfo(channelId: String): ChannelInfo
}
class NetworkCaptionTrackRepository(
    private val apiCaptionTrack: ApiCaptionTrack,
    private val apiChannel: ApiChannel
): VideoRepository{
    override suspend fun getCaptionTrack(video_id: String): CaptionTrack = apiCaptionTrack.getCaptionTrack(video_id)
    override suspend fun getChannelInfo(channelId: String): ChannelInfo = apiChannel.getChannelInfo(channelId)

}
