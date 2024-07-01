package com.example.english4d.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.english4d.di.DataApplication
import com.example.english4d.ui.fairytail.FairyTailViewModel
import com.example.english4d.ui.home.HomeViewModel
import com.example.english4d.ui.newspaper.ItemQuestionViewModel
import com.example.english4d.ui.newspaper.NewsViewmodel
import com.example.english4d.ui.newspaper.StatisticNewsViewModel
import com.example.english4d.ui.pronuciation.PronunciationAssessmentViewModel
import com.example.english4d.ui.splash.SplashViewModel
import com.example.english4d.ui.topic.TopicViewModel
import com.example.english4d.ui.video.ListeningViewModel
import com.example.english4d.ui.vocabulary.NewVocabViewModel
import com.example.english4d.ui.vocabulary.ReviseViewModel
import com.example.english4d.ui.wordstore.WordStoreViewModel
import com.example.english4d.ui.wordstore.addtopic.AddWordSViewModel


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
                dataApplication().container.workerRepository,
                dataApplication().container.vocabularyRepository,
                dataApplication().applicationContext
            )
        }
        initializer {
            NewVocabViewModel(
                dataApplication().container.vocabularyRepository,
                dataApplication().applicationContext
            )
        }
        initializer {
            TopicViewModel(
                dataApplication().container.vocabularyRepository
            )
        }
        initializer {
            ReviseViewModel(
                dataApplication().container.vocabularyRepository,
                dataApplication().applicationContext
            )
        }
        initializer {
            SplashViewModel(dataApplication().container.vocabularyRepository, context = dataApplication().applicationContext)
        }
        initializer {
            PronunciationAssessmentViewModel(dataApplication().container.vocabularyRepository, context = dataApplication().applicationContext)
        }
        initializer {
            ItemQuestionViewModel(dataApplication().container.questionRepository)
        }
        initializer {
            StatisticNewsViewModel(dataApplication().container.questionRepository)
        }
        initializer {
            ListeningViewModel(dataApplication().applicationContext)
        }
        initializer {
            FairyTailViewModel(context = dataApplication().applicationContext)
        }
        initializer {
            AddWordSViewModel(context = dataApplication().applicationContext)
        }
        initializer {
            AddWordSViewModel(context = dataApplication().applicationContext)
        }
        initializer {
            WordStoreViewModel(dataApplication().container.myWordRepository)
        }
    }
}

fun CreationExtras.dataApplication(): DataApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as DataApplication)