package com.example.english4d.data.database.question

import androidx.room.Insert
import androidx.room.Query

interface ArticleDao {
    @Query("SELECT * FROM Article WHERE id = :id")
    suspend fun getArticleAndQuestion(id: Int): ArticleWithQuestion
    @Insert
    suspend fun insertArticle(article: Article)
}

interface QuestionDao{
    @Insert
    suspend fun insertQuestion(question: Question)
}