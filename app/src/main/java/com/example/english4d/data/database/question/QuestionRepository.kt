package com.example.english4d.data.database.question

interface QuestionRepository {
    suspend fun getArticleAndQuestion(id: Int): ArticleWithQuestion
    suspend fun insertArticle(article: Article)
    suspend fun insertQuestion(question: Question)
}