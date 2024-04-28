package com.example.english4d.data.database.question

interface QuestionRepository {
    suspend fun getArticleAndQuestion(href: String): ArticleWithQuestion
    suspend fun insertArticle(article: Article)
    suspend fun insertQuestion(questions: List<Question>)
}