package com.example.english4d.network.database

import com.example.english4d.data.database.vocabulary.Definitions
import com.example.english4d.data.database.vocabulary.Examples
import com.example.english4d.data.database.vocabulary.Theme
import com.example.english4d.data.database.vocabulary.Topics
import com.example.english4d.data.database.vocabulary.Vocabulary
import com.example.english4d.data.database.vocabulary.VocabularyRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface DataContainer {
    val dataRepository: DataRepository
}

class AsyncDatabase(private val vocabularyRepository: VocabularyRepository) : DataContainer {
    private val _dataSynchronized = MutableStateFlow(Pair(false, false))
    val dataSynchronized: StateFlow<Pair<Boolean, Boolean>>
        get() = _dataSynchronized
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
            val listThemesAPI = dataRepository.getThemes()
            val listTopicsAPI = dataRepository.getTopics()
            val listVocabAPI = dataRepository.getVocabulary()
            val listExampleAPI = dataRepository.getExamples()
            val listDefinitionAPI = dataRepository.getDefinitions()
            _dataSynchronized.value = _dataSynchronized.value.copy(first = true)
            asyncTheme(listThemesAPI = listThemesAPI, vocabularyRepository = vocabularyRepository)
            asyncTopics(listTopicsAPI = listTopicsAPI, vocabularyRepository = vocabularyRepository)
            asyncVocabularies(listVocabAPI = listVocabAPI, vocabularyRepository = vocabularyRepository)
            asyncExamples(listExampleAPI = listExampleAPI, vocabularyRepository = vocabularyRepository)
            asyncDefinitions(listDefinitionAPI = listDefinitionAPI, vocabularyRepository = vocabularyRepository)
            _dataSynchronized.value = _dataSynchronized.value.copy(second = true)

        }
    }
}


suspend fun asyncTheme(listThemesAPI: List<ThemeAPI>, vocabularyRepository: VocabularyRepository) {
   val themes = listThemesAPI.map {
       Theme(id = it.id, title = it.title)
   }
    vocabularyRepository.insertTheme(themes)
}

suspend fun asyncTopics(
    listTopicsAPI: List<TopicsAPI>,
    vocabularyRepository: VocabularyRepository
) {
    val topics = listTopicsAPI.map {
        Topics(
            id = it.id,
            topic = it.topic,
            image = it.image,
            id_theme = it.id_theme
        )
    }
    vocabularyRepository.insertTopic(topics)

}

suspend fun asyncVocabularies(
    listVocabAPI: List<VocabularyAPI>,
    vocabularyRepository: VocabularyRepository
) {
    val vocabularies = listVocabAPI.map {
        Vocabulary(
            id = it.id,
            english = it.english,
            vietnamese = it.vietnamese,
            pronunciation = it.pronunciation,
            image = it.image,
            id_topic = it.id_topic
        )
    }
    vocabularyRepository.insertVocab(vocabularies)
}

suspend fun asyncExamples(
    listExampleAPI: List<ExamplesAPI>,
    vocabularyRepository: VocabularyRepository
) {
    val examples = listExampleAPI.map {
        Examples(
            id_vocab = it.id_vocab,
            example = it.example
        )
    }
    vocabularyRepository.insertExamples(examples)
}

suspend fun asyncDefinitions(
    listDefinitionAPI: List<DefinitionsAPI>,
    vocabularyRepository: VocabularyRepository
) {
    val definitions = listDefinitionAPI.map {
        Definitions(
            id_vocab = it.id_vocab,
            definition = it.definition,
            partofspeech = it.partofspeech
        )
    }
    vocabularyRepository.insertDefinitions(definitions)
}