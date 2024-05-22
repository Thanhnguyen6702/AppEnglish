package com.example.english4d.data.database.question

class OfflineQuestionRepository(
    private val articleDao: ArticleDao,
    private val  questionDao: QuestionDao
):QuestionRepository {
    override suspend fun getArticleAndQuestion(href: String): ArticleWithQuestion = articleDao.getArticleAndQuestion(href)

    override suspend fun insertArticle(article: Article) = articleDao.insertArticle(article)

    override suspend fun insertQuestion(questions: List<Question>) = questionDao.insertQuestion(questions)
    override suspend fun updateAnswer(id: Long, answer: String) = questionDao.updateAnswer(id,answer)
}