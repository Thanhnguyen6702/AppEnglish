package com.example.english4d.network.database

import retrofit2.http.GET

interface ApiAsyncDatabase {
    @GET("getVocabulary.php")
    suspend fun getVocabulary(): List<VocabularyAPI>
    @GET("getTopic.php")
    suspend fun getTopics(): List<TopicsAPI>
    @GET("getTheme.php")
    suspend fun getTheme(): List<ThemeAPI>
}