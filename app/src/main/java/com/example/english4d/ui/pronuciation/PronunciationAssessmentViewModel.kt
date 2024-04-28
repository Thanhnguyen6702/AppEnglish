package com.example.english4d.ui.pronuciation

import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.english4d.data.database.vocabulary.Pronunciation
import com.example.english4d.data.database.vocabulary.Vocabulary
import com.example.english4d.data.database.vocabulary.VocabularyRepository
import com.example.english4d.network.pronunciation.PronunciationAssessment
import com.example.english4d.network.pronunciation.PronunciationAssessment.RecordingListener
import com.example.english4d.network.pronunciation.PronunciationResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PronunciationAssessmentViewModel(private val vocabularyRepository: VocabularyRepository) :
    ViewModel() {
    private val _uiStateAssessment = MutableStateFlow(PronunciationAssessmentUiState())
    val uiStateAssessment: StateFlow<PronunciationAssessmentUiState> =
        _uiStateAssessment.asStateFlow()
    private val _uiStateStatistic = MutableStateFlow(PronunciationStatisticUiState())
    val uiStateStatistic: StateFlow<PronunciationStatisticUiState> = _uiStateStatistic.asStateFlow()
    private lateinit var listVocab: List<Vocabulary>
    private var index = 0
    private val pronunciationAssessment = PronunciationAssessment();

    init {
        updateStatistic()
        getVocabWithoutPronun()
        pronunciationAssessment.setRecordingListener(object : RecordingListener {
            override fun onRecordingStarted() {
                _uiStateAssessment.value =
                    _uiStateAssessment.value.copy(isRecording = RecordingState.RECORDING)
            }

            override fun onProcess() {
                _uiStateAssessment.value =
                    _uiStateAssessment.value.copy(isRecording = RecordingState.PROCESSING)

            }

            override fun onResultReceived(pronunciationResult: PronunciationResult?) {
                if (pronunciationResult == null){
                    _uiStateAssessment.value =
                        _uiStateAssessment.value.copy(
                            score = -1,
                            isRecording = RecordingState.NOTRECORDING
                        )
                }else{
                    val score = (pronunciationResult.accuracyScore).toInt()
                    _uiStateAssessment.value =
                        _uiStateAssessment.value.copy(
                            score = score,
                            isRecording = RecordingState.NOTRECORDING
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
                vocabularyRepository.getPronunciation().collect {
                    val determine = it.groupBy {
                        when (it.pronunciation.score) {
                            in 0..49 -> "lower"
                            in 49..74 -> "medium"
                            else -> "high"
                        }
                    }
                    _uiStateStatistic.value =
                        _uiStateStatistic.value.copy(
                            lower = determine["lower"]?.map { it.vocabulary } ?: listOf(),
                            medium = determine["medium"]?.map { it.vocabulary } ?: listOf(),
                            high = determine["high"]?.map { it.vocabulary } ?: listOf()
                        )
                }
            }
        }
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
                _uiStateAssessment.value.copy(score = 0, vocabulary = listVocab[index])
        }
    }

    fun startPronun() {
        pronunciationAssessment.setReferenceText(listVocab[index].english)
        pronunciationAssessment.startRecord()
    }

    fun hasPermissions(activity: ComponentActivity, vararg permissions: String): Boolean {
        return permissions.all { permission ->
            ContextCompat.checkSelfPermission(
                activity,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        }
    }
}