package com.example.english4d.ui.splash

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.english4d.PreferencesManager
import com.example.english4d.data.database.vocabulary.VocabularyRepository
import com.example.english4d.network.database.AsyncDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewModel(vocabularyRepository: VocabularyRepository, private val context : Context): ViewModel() {
       private val _uiState = MutableStateFlow(SplashUiState())
       val uiState: StateFlow<SplashUiState> = _uiState.asStateFlow()
       private val asyncDatabase = AsyncDatabase(vocabularyRepository)
       init {
           asyncDatabase()
       }
       private fun asyncDatabase(){
              val preferencesManager = PreferencesManager(context)
              if(!preferencesManager.checkUpdated()) {
                     viewModelScope.launch {
                            asyncDatabase.startAsyncDatabase()
                            asyncDatabase.dataSynchronized.collect {
                                   _uiState.value = _uiState.value.copy(updated = it.second)
                                   preferencesManager.setUpdated()
                            }
                     }
              }else{
                     _uiState.value = _uiState.value.copy(updated = true)
              }
       }

}