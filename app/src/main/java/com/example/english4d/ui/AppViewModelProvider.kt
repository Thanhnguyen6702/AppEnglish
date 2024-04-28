package com.example.english4d.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.english4d.DataApplication
import com.example.english4d.ui.Splash.SplashViewModel
import com.example.english4d.ui.home.HomeViewModel
import com.example.english4d.ui.newspaper.NewsViewmodel
import com.example.english4d.ui.pronuciation.PronunciationAssessmentViewModel
import com.example.english4d.ui.topic.TopicViewModel
import com.example.english4d.ui.vocabulary.NewVocabViewModel
import com.example.english4d.ui.vocabulary.ReviseViewModel


object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            NewsViewmodel(
                dataApplication().container.newsRepository,
                dataApplication().container.questionRepository
            )
        }
        initializer {
            HomeViewModel(
                dataApplication().container.vocabularyRepository,
                dataApplication().applicationContext
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
        initializer {
            PronunciationAssessmentViewModel(dataApplication().container.vocabularyRepository)
        }
    }
}

fun CreationExtras.dataApplication(): DataApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as DataApplication)