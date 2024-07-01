package com.example.english4d.data.database.wordstore

import kotlinx.coroutines.flow.Flow

interface MyWordRepository {
    suspend fun insertTopical(topic: MyWordTopic): Long
    fun getTopics(): Flow<List<MyWordTopic>>
    suspend fun insertMyWord(word: MyWord): Long
    suspend fun getMyWordDetail(id: Long): MyWordWithDetails
    suspend fun insertMyWordDefinition(detail: MyWordDefinition): Long
    suspend fun insertMyWordExample(example: MyWordExample): Long
    suspend fun insertMyWordAntonym(antonym: MyWordAntonym)

}