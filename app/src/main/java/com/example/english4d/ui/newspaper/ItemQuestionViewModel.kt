package com.example.english4d.ui.newspaper

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ItemQuestionViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ItemQuestionUiState())
    val uiState: StateFlow<ItemQuestionUiState> = _uiState.asStateFlow()
    val QuestionSelected = mutableMapOf<Int,Boolean>()
    fun setSelectOption(selectOption: String) {
        _uiState.value = _uiState.value.copy(selectOption = selectOption)
    }

    fun nextQuestion(maxQuestion: Int) {
        if (_uiState.value.questionNumber < maxQuestion-1)
            _uiState.value =
                _uiState.value.copy(questionNumber = _uiState.value.questionNumber.inc(), selectOption = "", expanded = false)
    }

    fun backQuestion() {
        if (_uiState.value.questionNumber > 0)
            _uiState.value =
                _uiState.value.copy(questionNumber = _uiState.value.questionNumber.dec(), selectOption = "", expanded = false)
    }
    fun changeExpanded(){
        _uiState.value = _uiState.value.copy(expanded = !_uiState.value.expanded)
    }
}