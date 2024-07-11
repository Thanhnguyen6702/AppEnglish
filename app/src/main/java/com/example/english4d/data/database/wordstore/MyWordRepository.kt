package com.example.english4d.data.database.wordstore

import kotlinx.coroutines.flow.Flow

interface MyWordRepository {
    suspend fun insertTopic(topic: MyWordTopic): Long
    suspend fun deleteTopic(id: Long)
    suspend fun renameTopic(id: Long, name: String)
    fun getTopics(): Flow<List<MyWordTopic>>
    suspend fun getTopicByNames(names: String): MyWordTopic?
    suspend fun getTopic(id: Long): TopicWithWords
    suspend fun setTopicStudy(id: Long, study: Int)
    suspend fun insertMyWord(word: MyWord): Long
    suspend fun isMyWordExist(english: String): Int
    suspend fun deleteMyWord(id: Long)
    suspend fun getMyWordDetail(id: Long): MyWordWithDetails
    suspend fun insertMyWordDefinition(detail: MyWordDefinition): Long
    suspend fun insertMyWordExample(example: MyWordExample): Long
    suspend fun insertMyWordAntonym(antonym: MyWordAntonym)

}