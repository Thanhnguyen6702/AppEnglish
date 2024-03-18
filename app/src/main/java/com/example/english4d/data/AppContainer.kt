package com.example.english4d.data

interface AppContainer {
    val newsRepository: NewsRepository
}

class DefaultAppContainer: AppContainer{
    val newsCrawl = NewsCrawl()
    override val newsRepository: NewsRepository by lazy {
        NetworkNewsRepository(newsCrawl)
    }
}