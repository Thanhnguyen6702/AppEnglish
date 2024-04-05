package com.example.english4d.network.database

import android.content.Context
import com.example.english4d.data.database.vocabulary.Theme
import com.example.english4d.data.database.vocabulary.Topics
import com.example.english4d.data.database.vocabulary.Vocabulary
import com.example.english4d.data.database.vocabulary.VocabularyDatabase
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface DataContainer {
    val dataRepository: DataRepository
}

class AsyncDatabase(private val context: Context) : DataContainer {
    private val baseUrl = "https://thanhhust.x10.mx/jetpack/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val retrofitService: ApiAsyncDatabase by lazy {
        retrofit.create(ApiAsyncDatabase::class.java)
    }
    override val dataRepository: DataRepository by lazy {
        OnlineDataRepository(retrofitService)
    }

    fun startAsyncDatabase() {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            val listThemes = dataRepository.getThemes()
            val listTopics = dataRepository.getTopics()
            val listVocab = dataRepository.getVocabulary()
            asyncTheme(listThemes = listThemes, context = context)
            asyscTopics(listTopics = listTopics, context = context)
            asyncVocabularies(listVocab = listVocab, context = context)
        }
    }
}


suspend fun asyncTheme(listThemes: List<ThemeAPI>, context: Context) {
    val themDao = VocabularyDatabase.getDatabase(context).themeDao()
    for (theme in listThemes) {
        themDao.insertTheme(Theme(id = theme.id, title = theme.title))
    }
}

suspend fun asyscTopics(listTopics: List<TopicsAPI>, context: Context) {
    val topicDao = VocabularyDatabase.getDatabase(context).topicDao()
    for (topic in listTopics) {
        topicDao.insertTopic(
            Topics(
                id = topic.id,
                topic = topic.topic,
                image = topic.image,
                id_theme = topic.id_theme
            )
        )
    }
}

suspend fun asyncVocabularies(listVocab: List<VocabularyAPI>, context: Context) {
    val vocabularyDao = VocabularyDatabase.getDatabase(context).vocabularyDao()
    for (vocab in listVocab) {
        vocabularyDao.insertVocab(
            Vocabulary(
                id = vocab.id,
                english = vocab.english,
                vietnamese = vocab.vietnamese,
                pronunciation = vocab.pronunciation,
                image = vocab.image,
                id_topic = vocab.id_topic
            )
        )
    }
}