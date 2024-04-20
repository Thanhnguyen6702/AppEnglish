package com.example.english4d.data.database.vocabulary

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.english4d.ui.topic.ItemTopic
import kotlinx.coroutines.flow.Flow

@Dao
interface VocabularyDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVocab(vocabularies: List<Vocabulary>)

    @Query("SELECT Vocabulary.* FROM Vocabulary INNER JOIN Statistic ON Vocabulary.id = Statistic.id_vocab" +
            " WHERE unlearned = 1")
    fun getUnlearned():Flow<List<Vocabulary>>

    @Query("SELECT Vocabulary.* FROM Vocabulary INNER JOIN Statistic ON Vocabulary.id = Statistic.id_vocab WHERE learning = 1")
    fun getLearning():Flow<List<Vocabulary>>

    @Query("SELECT Vocabulary.* FROM Vocabulary INNER JOIN Statistic ON Vocabulary.id = Statistic.id_vocab" +
            " WHERE master = 1")
    fun getMaster(): Flow<List<Vocabulary>>
    @Query("SELECT * FROM Vocabulary")
    fun getVocabulary(): List<Vocabulary>
    @Query("SELECT Vocabulary.* FROM Vocabulary INNER JOIN Statistic ON Vocabulary.id = Statistic.id_vocab " +
            "WHERE isStudy = 1")
    suspend fun getRevise(): List<Vocabulary>

    @Query("SELECT Vocabulary.* FROM Vocabulary " +
            "INNER JOIN Topics ON Vocabulary.id_topic = Topics.id " +
            "LEFT JOIN Statistic ON Vocabulary.id = Statistic.id_vocab " +
            "WHERE (:topicID IS NULL OR Topics.id = :topicID) AND " +
            "Statistic.id_vocab IS NULL")
    suspend fun getNewVocab(topicID: Int?): List<Vocabulary>

    @Query("SELECT " +
            "Topics.id, "+
            "COUNT(Vocabulary.id) AS totalVocabulary, " +
            "SUM(CASE WHEN Statistic.id_vocab IS NULL THEN 1 ELSE 0 END) AS unlearnedVocabulary " +
            "FROM Vocabulary " +
            "INNER JOIN Topics ON Vocabulary.id_topic = Topics.id " +
            "LEFT JOIN Statistic ON Vocabulary.id = Statistic.id_vocab " +
            "WHERE Topics.id = :topicID")
    suspend fun getCompletionRate(topicID: Int):CompletionRate


}

@Dao
interface StatisticDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStatistic(statistic: Statistic)

    @Query("SELECT * FROM Statistic")
    fun getStatistic(): Flow<Statistic>
    @Query("SELECT * FROM Statistic WHERE id_vocab = :id")
    fun getItemStatistic(id: Int): Statistic
    @Query("UPDATE Statistic SET isStudy = 1 WHERE check_day % 10 = 1")
    suspend fun updateIsStudy()
    @Query("UPDATE Statistic SET check_day = CASE WHEN check_day % 10 = 1 THEN check_day/10 ELSE (check_day -1) END WHERE isStudy = 0 AND master =0")
    suspend fun updateCheckDay()
    @Transaction
    suspend fun chooseAnswer(isCorrect: Int, id: Int) {
        updateStatisticFirst(isCorrect, id)
        updateStatisticSecond()
    }

    @Query("UPDATE Statistic SET isStudy = 0, check_day = CASE WHEN 0 = :isCorrect THEN (check_day * 10 + 1) ELSE check_day END WHERE id_vocab = :id")
    suspend fun updateStatisticFirst(isCorrect: Int, id: Int)

    @Query("UPDATE Statistic SET unlearned = 0, learning = CASE WHEN check_day = 0 THEN 0 ELSE 1 END, master = CASE WHEN check_day = 0 THEN 1 ELSE 0 END")
    suspend fun updateStatisticSecond()
    @Transaction
    suspend fun updateIsStudyAndCheckDay(){
        updateCheckDay()
        updateIsStudy()
    }
}

@Dao
interface TopicsDAO{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopic(topics: List<Topics>)
    @Query("SELECT * FROM Topics WHERE id = :id")
    suspend fun getTopic(id: Int): Topics

    @Query("SELECT Topics.id,Topics.image,Topics.topic,Theme.title,Topics.id_theme FROM Topics " +
            "INNER JOIN Theme ON Topics.id_theme = Theme.id")
    suspend fun getItemTopic(): List<ItemTopic>
}

@Dao
interface ThemeDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTheme(themes: List<Theme>)
}

@Dao
interface DefinitionsDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDefinitions(definitions: List<Definitions>)
    @Query("SELECT * FROM Definitions WHERE id_vocab = :id")
    suspend fun getDefinitions(id: Int):List<Definitions>
}
@Dao
interface ExamplesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertExamples(examples: List<Examples>)
    @Query("SELECT * FROM Examples WHERE id_vocab = :id")
    suspend fun getExamples(id: Int): List<Examples>
}