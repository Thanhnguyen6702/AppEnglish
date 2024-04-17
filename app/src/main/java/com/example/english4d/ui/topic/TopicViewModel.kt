package com.example.english4d.ui.topic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.english4d.data.database.vocabulary.CompletionRate
import com.example.english4d.data.database.vocabulary.VocabularyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TopicViewModel(val vocabularyRepository: VocabularyRepository): ViewModel() {
    private val _uiState = MutableStateFlow(TopicUiState())
    val uiState: StateFlow<TopicUiState> = _uiState.asStateFlow()

    init {
        getListItemTopic()
    }
    private fun getListItemTopic(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val data = vocabularyRepository.getItemTopic()
                val listTopic = data.groupBy { it.title }.map { it.key to it.value }
                val listCompletionRate : MutableMap<Int,CompletionRate> = mutableMapOf()
                for (item in data){
                    listCompletionRate[item.id] = vocabularyRepository.getCompletionRate(item.id)
                }
                _uiState.value = _uiState.value.copy(listTopic = listTopic, completionRates = listCompletionRate)
            }
        }
    }
}