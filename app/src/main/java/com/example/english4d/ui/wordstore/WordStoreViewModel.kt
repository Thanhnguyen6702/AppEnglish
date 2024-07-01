package com.example.english4d.ui.wordstore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.english4d.data.database.wordstore.MyWordRepository
import com.example.english4d.data.database.wordstore.MyWordTopic
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class WordStoreViewModel(private val myWordRepository: MyWordRepository): ViewModel() {
        val topic: StateFlow<List<MyWordTopic>> = myWordRepository.getTopics().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}