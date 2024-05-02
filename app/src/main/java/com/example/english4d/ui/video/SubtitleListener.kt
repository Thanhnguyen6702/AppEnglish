package com.example.english4d.ui.video

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.text.Cue
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer

class ExoPlayerSetup(context: Context, videoUri: Uri) {

    val exoPlayer: ExoPlayer = ExoPlayer.Builder(context).build()

    init {
        // Tạo MediaItem từ URI
        val mediaItem = MediaItem.Builder()
            .setUri(videoUri)
            .build()

        // Đăng ký listener để lắng nghe các cues (phụ đề)
        exoPlayer.addListener(object : Player.Listener {
            @OptIn(UnstableApi::class)
            override fun onCues(cues: List<Cue>) {
                // In ra log các phụ đề
                cues.forEach { cue ->
                    Log.d("ExoPlayerSubtitles", "Subtitle: ${cue.text}")
                }
            }
        })

        // Thiết lập media item và chuẩn bị ExoPlayer
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.play()
    }

    fun play() {
        exoPlayer.play()
    }

    fun pause() {
        exoPlayer.pause()
    }

    fun release() {
        exoPlayer.release()
    }
}