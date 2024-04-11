package com.example.english4d.data

import android.content.Context
import com.example.english4d.data.database.vocabulary.OfflineVocabularyRepository
import com.example.english4d.data.database.vocabulary.VocabularyDatabase
import com.example.english4d.data.database.vocabulary.VocabularyRepository
import com.example.english4d.data.news.NetworkNewsRepository
import com.example.english4d.data.news.NewsCrawl
import com.example.english4d.data.news.NewsRepository

interface AppContainer {
    val newsRepository: NewsRepository
    val vocabularyRepository: VocabularyRepository
}

class DataAppContainer(private val context: Context): AppContainer{
    val newsCrawl = NewsCrawl()
    override val newsRepository: NewsRepository by lazy {
        NetworkNewsRepository(newsCrawl)
    }
    val vocabDatabase = VocabularyDatabase.getDatabase(context)
    override val vocabularyRepository: OfflineVocabularyRepository by lazy {
        OfflineVocabularyRepository(
            vocabularyDao = vocabDatabase.vocabularyDao(),
            statisticDao = vocabDatabase.statisticDao(),
            topicsDAO = vocabDatabase.topicDao(),
            themeDao = vocabDatabase.themeDao()
        )
    }
}