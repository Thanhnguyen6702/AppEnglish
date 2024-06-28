package com.example.english4d.ui.newspaper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.english4d.data.database.question.QuestionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ItemQuestionViewModel(private val questionRepository: QuestionRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(ItemQuestionUiState())
    val uiState: StateFlow<ItemQuestionUiState> = _uiState.asStateFlow()
    fun setSelectOption(selectOption: String,id: Long) {
        val answerSelected = _uiState.value.answerSelected.toMutableMap()
        answerSelected[_uiState.value.questionNumber] = selectOption
        _uiState.value =
            _uiState.value.copy(answerSelected = answerSelected)
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                questionRepository.updateAnswer(id,selectOption)
            }
        }
    }

    fun nextQuestion(maxQuestion: Int) {
        if (_uiState.value.questionNumber < maxQuestion-1)
            _uiState.value =
                _uiState.value.copy(questionNumber = _uiState.value.questionNumber.inc())
    }

    fun backQuestion() {
        if (_uiState.value.questionNumber > 0)
            _uiState.value =
                _uiState.value.copy(questionNumber = _uiState.value.questionNumber.dec())
    }
}