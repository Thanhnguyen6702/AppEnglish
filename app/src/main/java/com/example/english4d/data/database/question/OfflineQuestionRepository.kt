package com.example.english4d.data.database.question

class OfflineQuestionRepository(
    private val articleDao: ArticleDao,
    private val  questionDao: QuestionDao
):QuestionRepository {
    override suspend fun getArticleAndQuestion(id: Int): ArticleWithQuestion = articleDao.getArticleAndQuestion(id)

    override suspend fun insertArticle(article: Article) = articleDao.insertArticle(article)

    override suspend fun insertQuestion(question: Question) = questionDao.insertQuestion(question)
}