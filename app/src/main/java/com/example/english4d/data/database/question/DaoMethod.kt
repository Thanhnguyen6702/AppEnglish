package com.example.english4d.data.database.question

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface ArticleDao {
    @Transaction
    @Query("SELECT * FROM Article WHERE href = :href")
    suspend fun getArticleAndQuestion(href:String): ArticleWithQuestion
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertArticle(article: Article)
}
@Dao
interface QuestionDao{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertQuestion(questions: List<Question>)
    @Query("UPDATE Question SET answerSelected = :answer WHERE id = :id")
    suspend fun updateAnswer(id:Long, answer: String)
}