package com.example.english4d.network.database

interface DataRepository {
    suspend fun getVocabulary(): List<VocabularyAPI>
    suspend fun getTopics(): List<TopicsAPI>
    suspend fun getThemes(): List<ThemeAPI>
    suspend fun getDefinitions(): List<DefinitionsAPI>
    suspend fun getExamples(): List<ExamplesAPI>
}

class OnlineDataRepository(private val apiAsyncDatabase: ApiAsyncDatabase): DataRepository{
    override suspend fun getVocabulary(): List<VocabularyAPI> {
        return apiAsyncDatabase.getVocabulary()
    }

    override suspend fun getTopics(): List<TopicsAPI> {
        return apiAsyncDatabase.getTopics()
    }

    override suspend fun getThemes(): List<ThemeAPI> {
        return apiAsyncDatabase.getTheme()
    }

    override suspend fun getDefinitions(): List<DefinitionsAPI> {
        return apiAsyncDatabase.getDefinitions()
    }

    override suspend fun getExamples(): List<ExamplesAPI> {
        return apiAsyncDatabase.getExamples()
    }

}