package com.example.english4d.utils

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.LANG_MISSING_DATA
import android.speech.tts.TextToSpeech.LANG_NOT_SUPPORTED
import android.speech.tts.TextToSpeech.OnInitListener
import android.speech.tts.TextToSpeech.SUCCESS
import java.util.Locale

class TextToSpeechManager(context: Context,private val onInitListener: OnInitListener? = null):
OnInitListener{
    private var textToSpeech: TextToSpeech = TextToSpeech(context, this)
    override fun onInit(status: Int) {
        if(status == SUCCESS){
            val result = textToSpeech.setLanguage(Locale.ENGLISH)
            if (result == LANG_MISSING_DATA || result == LANG_NOT_SUPPORTED){
            }else{
                onInitListener?.onInit(SUCCESS)
            }
        }
    }
    fun speak(text: String){
        textToSpeech.speak(text,TextToSpeech.QUEUE_ADD,null,null)
    }
    fun stop(){
        textToSpeech.stop()
        textToSpeech.shutdown()
    }
}