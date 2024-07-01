package com.example.english4d.ui.pronuciation

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.english4d.data.database.vocabulary.Pronunciation
import com.example.english4d.data.database.vocabulary.Vocabulary
import com.example.english4d.data.database.vocabulary.VocabularyRepository
import com.example.english4d.network.pronunciation.PronunciationAssessment
import com.example.english4d.network.pronunciation.PronunciationAssessment.RecordingListener
import com.example.english4d.network.pronunciation.PronunciationResult
import com.example.english4d.utils.TextToSpeechManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.DataInputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException

class PronunciationAssessmentViewModel(
    private val vocabularyRepository: VocabularyRepository,
    context: Context
) :
    ViewModel() {
    private val _uiStateAssessment = MutableStateFlow(PronunciationAssessmentUiState())
    val uiStateAssessment: StateFlow<PronunciationAssessmentUiState> =
        _uiStateAssessment.asStateFlow()
    private val _uiStateStatistic = MutableStateFlow(PronunciationStatisticUiState())
    val uiStateStatistic: StateFlow<PronunciationStatisticUiState> = _uiStateStatistic.asStateFlow()
    private lateinit var listVocab: List<Vocabulary>
    private var index = 0
    private val pronunciationAssessment = PronunciationAssessment()
    private val playSound = TextToSpeechManager.getInstance(context)
    private var filePath: String? = context.getExternalFilesDir(null)?.absolutePath + "/record.wav"

    init {
        if (filePath != null) {
            pronunciationAssessment.setFilePath(filePath)
        } else {
            Log.e("AppError", "External storage not available")
        }
        updateStatistic()
        getVocabWithoutPronun()
        pronunciationAssessment.setRecordingListener(object : RecordingListener {
            override fun onRecordingStarted() {
                viewModelScope.launch {
                    delay(5000)
                    pronunciationAssessment.stopRecord()
                }

                _uiStateAssessment.value =
                    _uiStateAssessment.value.copy(isRecording = RecordingState.RECORDING)
            }

            override fun onProcess() {
                _uiStateAssessment.value =
                    _uiStateAssessment.value.copy(isRecording = RecordingState.PROCESSING)
            }

            override fun onResultReceived(pronunciationResult: PronunciationResult?) {
                if (pronunciationResult == null) {
                    _uiStateAssessment.value =
                        _uiStateAssessment.value.copy(
                            score = -1,
                            isRecording = RecordingState.NOTRECORDING
                        )
                } else {
                    val score = (pronunciationResult.accuracyScore).toInt()
                    _uiStateAssessment.value =
                        _uiStateAssessment.value.copy(
                            score = score,
                            isRecording = RecordingState.NOTRECORDING,
                            isListen = true
                        )
                    if (score > 0) {
                        viewModelScope.launch {
                            withContext(Dispatchers.IO) {
                                vocabularyRepository.insertPronunciation(
                                    Pronunciation(
                                        id_vocab = listVocab[index].id,
                                        score = score
                                    )
                                )
                            }
                        }
                    }
                }
            }

        })
    }

    private fun updateStatistic() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                vocabularyRepository.getPronunciation().collect { pronunciationWithVocabularies ->
                    val determine = pronunciationWithVocabularies.groupBy {
                        when (it.pronunciation.score) {
                            in 0..49 -> "lower"
                            in 49..74 -> "medium"
                            else -> "high"
                        }
                    }
                    _uiStateStatistic.value =
                        _uiStateStatistic.value.copy(
                            lower = determine["lower"] ?: listOf(),
                            medium = determine["medium"] ?: listOf(),
                            high = determine["high"] ?: listOf()
                        )
                }
            }
        }
    }
    fun setOptionSelect(option: StatisticPronunciation){
        _uiStateStatistic.value = _uiStateStatistic.value.copy(optionSelect = option)
    }
    private fun getVocabWithoutPronun() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                listVocab = vocabularyRepository.getVocabWithoutPronunciation()
                if (listVocab.isNotEmpty()) {
                    _uiStateAssessment.value =
                        _uiStateAssessment.value.copy(vocabulary = listVocab[index])
                }
            }
        }
    }

    fun nextPronunciation() {
        if (_uiStateAssessment.value.isRecording == RecordingState.NOTRECORDING) {
            index++
            _uiStateAssessment.value =
                _uiStateAssessment.value.copy(
                    score = 0,
                    vocabulary = listVocab[index],
                    isListen = false
                )
        }
    }

    fun startPronun() {
        pronunciationAssessment.setReferenceText(listVocab[index].english)
        pronunciationAssessment.startRecord()
    }

    fun textToSpeech(text: String) {
        _uiStateAssessment.value = _uiStateAssessment.value.copy(isSpeak = true)
        playSound.speak(text)
        _uiStateAssessment.value = _uiStateAssessment.value.copy(isSpeak = false)
    }

fun listeningAgain() {
    if (filePath != null) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _uiStateAssessment.value = _uiStateAssessment.value.copy(isListen = false)
                    val file = File(filePath)
                    val bufferSize = 8192
                    val audioAttributes = AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                    val audioFormat = AudioFormat.Builder()
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setSampleRate(16000)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build()
                    val audioTrack = AudioTrack.Builder()
                        .setAudioAttributes(audioAttributes)
                        .setAudioFormat(audioFormat)
                        .setBufferSizeInBytes(bufferSize)
                        .setTransferMode(AudioTrack.MODE_STREAM)
                        .build()

                    val buffer = ByteArray(bufferSize)
                    val dataInputStream = DataInputStream(FileInputStream(file))

                    audioTrack.play()

                    while (dataInputStream.available() > 0) {
                        val bytesRead = dataInputStream.read(buffer, 0, buffer.size)
                        if (bytesRead != AudioTrack.ERROR_INVALID_OPERATION) {
                            audioTrack.write(buffer, 0, bytesRead)
                        }
                    }

                    audioTrack.stop()
                    _uiStateAssessment.value = _uiStateAssessment.value.copy(isListen = true)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

    }
}
    fun stopSound() {
        playSound.stop()
      }

}