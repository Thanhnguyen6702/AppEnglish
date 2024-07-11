package com.example.english4d.data.database.wordstore

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface MyWordDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTopic(topic: MyWordTopic): Long
    @Query("DELETE FROM myword_topic WHERE id = :id")
    suspend fun deleteTopic(id: Long)

    @Query("SELECT * FROM myword_topic")
    fun getTopics(): Flow<List<MyWordTopic>>
    @Transaction
    @Query("SELECT * FROM myword_topic WHERE id = :id")
    fun getTopic(id: Long): TopicWithWords
    @Query("UPDATE myword_topic SET name = :name WHERE id = :id")
    suspend fun updateTopic(id: Long, name: String)
    @Query("UPDATE myword_topic SET is_study = :study WHERE id = :id")
    suspend fun updateTopicStudy(id: Long, study: Int)
    @Query("SELECT * FROM myword_topic WHERE name = :name")
    suspend fun getTopicByName(name: String): MyWordTopic?
    @Query("SELECT COUNT(*) FROM myword WHERE english = :english")
    suspend fun isWordExist(english: String): Int

    @Transaction
    @Query("SELECT * FROM myword WHERE id = :id ")
    suspend fun getMyWordDetail(id: Long): MyWordWithDetails
    @Query("DELETE FROM myword WHERE id = :id")
    suspend fun deleteMyWord(id: Long)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMyWord(word: MyWord): Long

    @Insert
    suspend fun insertMyWordDefinition(detail: MyWordDefinition): Long

    @Insert
    suspend fun insertMyWordExample(example: MyWordExample): Long

    @Insert
    suspend fun insertMyWordAntonym(antonym: MyWordAntonym)
}
