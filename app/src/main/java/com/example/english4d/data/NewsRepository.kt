package com.example.english4d.data
interface NewsRepository {
    suspend fun getNews(topic: String): List<NewsItem>?
}

class NetworkNewsRepository(private val newsCrawl: NewsCrawl ): NewsRepository{
    override suspend fun getNews(topic: String): List<NewsItem>? {
        return newsCrawl.getItemNews(topic)
    }
}