package com.example.english4d.ui.home

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.english4d.PreferencesManager
import com.example.english4d.data.database.vocabulary.VocabularyDatabase
import com.example.english4d.data.database.vocabulary.VocabularyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val vocabularyRepository: VocabularyRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private fun updateLayout() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                launch {
                    vocabularyRepository.getUnlearned().collect {
                        _uiState.value = _uiState.value.copy(unlearned = it)
                    }
                }
                launch {
                    vocabularyRepository.getLearning().collect {
                        _uiState.value = _uiState.value.copy(learning = it)
                    }
                }
                launch {
                    vocabularyRepository.getMaster().collect {
                        _uiState.value = _uiState.value.copy(master = it)
                    }
                }
                launch {
                    val listRevise = vocabularyRepository.getRevise()
                    _uiState.value =
                        _uiState.value.copy(revise = listRevise, isRevise = listRevise.isNotEmpty())
                }
                launch {
                    val listVocab = vocabularyRepository.getNewVocabulary(null)
                    val minID = listVocab.minOf { it.id }
                    val topic = vocabularyRepository.getTopic(minID)
                    val listNewVocab = listVocab.filter { it.id == minID }
                    _uiState.value = _uiState.value.copy(newVocab = Pair(topic, listNewVocab))
                }
            }
        }
    }

    fun changeTopic(id: Int) {
        viewModelScope.launch {
            val listVocab = vocabularyRepository.getNewVocabulary(id)
            val topic = vocabularyRepository.getTopic(id)
            _uiState.value = _uiState.value.copy(newVocab = Pair(topic, listVocab))
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateStatistic(context: Context) {
        Log.e("hiihih","bị gọi")
        val sharedPreferences = PreferencesManager(context)
        if (!sharedPreferences.isNewDay()) {
            val statisticDao = VocabularyDatabase.getDatabase(context).statisticDao()
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    statisticDao.updateIsStudyAndCheckDay()
                    updateLayout()
                }
            }
            sharedPreferences.saveCurrentDate()
        } else {
            updateLayout()
        }

    }
}