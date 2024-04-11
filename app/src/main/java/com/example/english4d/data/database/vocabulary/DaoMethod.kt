package com.example.english4d.data.database.vocabulary

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
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
            "WHERE isStudy = 1")
    suspend fun getRevise(): List<Vocabulary>

    @Query("SELECT Vocabulary.* FROM Vocabulary " +
            "INNER JOIN Topic ON Vocabulary.id_topic = Topic.id " +
            "LEFT JOIN Statistic ON Vocabulary.id = Statistic.id_vocab " +
            "WHERE (:topicID IS NULL OR Topic.id = :topicID) AND " +
            "Statistic.id_vocab IS NULL")
    suspend fun getNewVocab(topicID: Int?): List<Vocabulary>



}

@Dao
interface StatisticDao{
    @Insert
    suspend fun insertUnLearned(statistic: Statistic)
    @Query("UPDATE Statistic SET unlearned = 0,learning = CASE WHEN check_day = 0 THEN 0 ELSE 1 END," +
            " master = CASE WHEN check_day = 0 THEN 1 ELSE 0 END ")
    suspend fun updateStatistic()
    @Query("SELECT * FROM Statistic")
    fun getStatistic(): Flow<Statistic>
    @Query("UPDATE Statistic SET isStudy = 1 WHERE check_day % 10 = 1")
    suspend fun updateIsStudy()
    @Query("UPDATE Statistic SET check_day = CASE WHEN check_day % 10 = 1 THEN check_day/10 ELSE (check_day -1) END")
    suspend fun updateCheckDay()

    @Transaction
    suspend fun updateIsStudyAndCheckDay(){
        updateIsStudy()
        updateCheckDay()
    }
}

@Dao
interface TopicsDAO{
    @Insert
    suspend fun insertTopic(topics: Topics)
    @Query("SELECT * FROM Topic WHERE id = :id")
    suspend fun getTopic(id: Int): Topics
}

@Dao
interface ThemeDao{
    @Insert
    suspend fun insertTheme(theme: Theme)
}

@Dao
interface DefinitionsDao{
    @Insert
    suspend fun insertDefinitions(definitions: Definitions)
    @Query("SELECT * FROM Definitions WHERE id_vocab = :id")
    suspend fun getDefinitions(id: Int):List<Definitions>
}
@Dao
interface ExamplesDao {
   @Insert
   suspend fun insertExamples(examples: Examples)
    @Query("SELECT * FROM Examples WHERE id_vocab = :id")
    suspend fun getExamples(id: Int): List<Examples>
}