package com.example.english4d.utils

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import java.util.Locale

class TextToSpeechManager private constructor(context: Context, private val onInitListener: OnInitListener? = null) : OnInitListener {
    private var textToSpeech: TextToSpeech = TextToSpeech(context.applicationContext, this)

    init {
        // Initialization code here
    }

    companion object {
        @Volatile
        private var INSTANCE: TextToSpeechManager? = null

        fun getInstance(context: Context, onInitListener: OnInitListener? = null): TextToSpeechManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: TextToSpeechManager(context, onInitListener).also { INSTANCE = it }
            }
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech.setLanguage(Locale.ENGLISH)
            if (result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED) {
                onInitListener?.onInit(TextToSpeech.SUCCESS)
            }
        }
    }

    fun speak(text: String) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_ADD, null, null)
    }

    fun stop() {
        textToSpeech.stop()
    }
}
