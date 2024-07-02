package com.example.english4d.data.database.wordstore

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface MyWordDao {
    @Insert
    suspend fun insertTopic(topic: MyWordTopic): Long
    @Query("SELECT * FROM myword_topic")
    fun getTopics(): Flow<List<MyWordTopic>>
    @Transaction
    @Query("SELECT * FROM myword_topic WHERE id = :id")
    fun getTopic(id: Long): TopicWithWords
    @Transaction
    @Query("SELECT * FROM myword WHERE id = :id ")
    suspend fun getMyWordDetail(id: Long): MyWordWithDetails

    @Insert
    suspend fun insertMyWord(word: MyWord): Long

    @Insert
    suspend fun insertMyWordDefinition(detail: MyWordDefinition): Long

    @Insert
    suspend fun insertMyWordExample(example: MyWordExample): Long

    @Insert
    suspend fun insertMyWordAntonym(antonym: MyWordAntonym)
}
