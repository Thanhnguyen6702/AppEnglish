package com.example.english4d.data

import com.example.english4d.data.news.NetworkNewsRepository
import com.example.english4d.data.news.NewsCrawl
import com.example.english4d.data.news.NewsRepository

interface AppContainer {
    val newsRepository: NewsRepository
}

class DefaultAppContainer: AppContainer{
    val newsCrawl = NewsCrawl()
    override val newsRepository: NewsRepository by lazy {
        NetworkNewsRepository(newsCrawl)
    }
}