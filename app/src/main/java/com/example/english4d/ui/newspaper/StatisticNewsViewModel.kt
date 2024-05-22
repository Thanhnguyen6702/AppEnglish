package com.example.english4d.ui.newspaper

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.english4d.data.database.question.QuestionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StatisticNewsViewModel(private val questionRepository: QuestionRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(StatisticNewsUiState())
    val uiState: StateFlow<StatisticNewsUiState> = _uiState.asStateFlow()
    fun update(href: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val listQuestion = questionRepository.getArticleAndQuestion(Uri.encode(href))
                val listQuestionFailed = mutableListOf<Pair<Int, String>>()
                var numberCorrect = 0
                for (index in 0..<listQuestion.questions.size) {
                    if (listQuestion.questions[index].answerSelected != null && listQuestion.questions[index].answerSelected == listQuestion.questions[index].answer) {
                        numberCorrect++
                    } else {
                        listQuestionFailed.add(Pair(index+1,listQuestion.questions[index].answer))
                    }
                }
                _uiState.value = _uiState.value.copy(
                    numberCorrect = numberCorrect,
                    totalQuestion = listQuestion.questions.size,
                    questionsFailed = listQuestionFailed
                )
            }
        }
    }
}