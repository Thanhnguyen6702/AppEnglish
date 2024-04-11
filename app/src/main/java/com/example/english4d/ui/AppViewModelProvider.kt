package com.example.english4d.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.english4d.NewsApplication
import com.example.english4d.ui.home.HomeViewModel
import com.example.english4d.ui.vocabulary.NewVocabViewModel


object AppViewModelProvider {
    val Factory = viewModelFactory {
       initializer {
           HomeViewModel(
               dataApplication().container.vocabularyRepository
           )
       }
        initializer {
            NewVocabViewModel(
                dataApplication().container.vocabularyRepository
            )
        }
    }
}

fun CreationExtras.dataApplication(): NewsApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as NewsApplication)