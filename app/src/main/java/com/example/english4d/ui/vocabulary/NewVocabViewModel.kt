package com.example.english4d.ui.vocabulary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.english4d.data.database.vocabulary.Vocabulary
import com.example.english4d.data.database.vocabulary.VocabularyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewVocabViewModel(
    private val vocabularyRepository: VocabularyRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(NewVocabUiState())
    val uiState: StateFlow<NewVocabUiState> = _uiState.asStateFlow()
    private lateinit var  vocabularies: List<Vocabulary>
    private var index = 0
    fun updateLayout(id: Int ) {
        viewModelScope.launch {
            withContext(context = Dispatchers.IO) {
                val title = vocabularyRepository.getTopic(id)
                vocabularies = vocabularyRepository.getNewVocabulary(id)
                _uiState.value = _uiState.value.copy(title = title.topic)
            }
        }
    }

    fun getDefinitions() {
        viewModelScope.launch {
            withContext(context = Dispatchers.IO) {
                val listDefinitions =
                    vocabularyRepository.getDefinition(vocabularies[index].id)
                _uiState.value = _uiState.value.copy(definitions = listDefinitions)
            }
        }
    }

    fun getExamples() {
        viewModelScope.launch {
            withContext(context = Dispatchers.IO) {
                val listExamples = vocabularyRepository.getExample(vocabularies[index].id)
                _uiState.value = _uiState.value.copy(examples = listExamples)
            }
        }
    }
}