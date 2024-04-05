package com.example.english4d.ui.theme.home

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.english4d.data.database.vocabulary.VocabularyDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel(context: Context): ViewModel() {
    val _uiState  = MutableStateFlow(HomeUiState())
    val uiState : StateFlow<HomeUiState> = _uiState.asStateFlow()
    val vocabularyDao = VocabularyDatabase.getDatabase(context).vocabularyDao()

    fun updateLayout(){
        _uiState.value.unlearned = vocabularyDao.getUnlearned()
    }
}