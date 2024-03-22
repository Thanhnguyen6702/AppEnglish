package com.example.english4d.data.news

interface NewsRepository {
    suspend fun getListNews(topic: String): List<NewsItem>?
    suspend fun getContentNews(url: String): List<NewsContent>
}

class NetworkNewsRepository(private val newsCrawl: NewsCrawl): NewsRepository {
    override suspend fun getListNews(topic: String): List<NewsItem>? {
        return newsCrawl.getItemNews(topic)
    }

    override suspend fun getContentNews(url: String): List<NewsContent> {
        return newsCrawl.getContent(url)
    }

}