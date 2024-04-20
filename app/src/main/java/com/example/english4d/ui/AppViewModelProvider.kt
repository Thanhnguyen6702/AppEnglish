package com.example.english4d.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.english4d.NewsApplication
import com.example.english4d.ui.Splash.SplashViewModel
import com.example.english4d.ui.home.HomeViewModel
import com.example.english4d.ui.topic.TopicViewModel
import com.example.english4d.ui.vocabulary.NewVocabViewModel
import com.example.english4d.ui.vocabulary.ReviseViewModel


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
        initializer {
            TopicViewModel(
                dataApplication().container.vocabularyRepository
            )
        }
        initializer {
            ReviseViewModel(
                dataApplication().container.vocabularyRepository
            )
        }
        initializer {
            SplashViewModel(dataApplication().container.vocabularyRepository, context = dataApplication().applicationContext)
        }
    }
}

fun CreationExtras.dataApplication(): NewsApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as NewsApplication)