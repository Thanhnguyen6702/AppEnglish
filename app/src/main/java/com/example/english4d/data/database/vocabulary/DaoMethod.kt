package com.example.english4d.data.database.vocabulary

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface VocabularyDao{
    @Insert
    suspend fun insertVocab(vocabulary: Vocabulary)

    @Query("SELECT * FROM Vocabulary INNER JOIN Statistic ON Vocabulary.id = Statistic.id_vocab" +
            " WHERE unlearned = 1")
    fun getUnlearned():Flow<List<Vocabulary>>

    @Query("SELECT * FROM Vocabulary INNER JOIN Statistic ON Vocabulary.id = Statistic.id_vocab WHERE learning = 1")
    fun getLearning():Flow<List<Vocabulary>>

    @Query("SELECT * FROM Vocabulary INNER JOIN Statistic ON Vocabulary.id = Statistic.id_vocab" +
            " WHERE master = 1")
    fun getMaster(): Flow<List<Vocabulary>>

    @Query("SELECT * FROM Vocabulary INNER JOIN Statistic ON Vocabulary.id = Statistic.id_vocab " +
            "WHERE unlearned = 1 OR learning = 1")
    suspend fun getRevise(): List<Vocabulary>
}

@Dao
interface StatisticDao{
    @Query("SELECT * FROM Statistic")
    fun getStatistic(): Flow<Statistic>
}

@Dao
interface TopicsDAO{
    @Insert
    suspend fun insertTopic(topics: Topics)
}

@Dao
interface ThemeDao{
    @Insert
    suspend fun insertTheme(theme: Theme)
}