package com.example.english4d.ui.theme.newspaper

import com.example.english4d.data.NewsItem
import com.example.english4d.data.NewsTopic

sealed interface NewsUiState {
    data class Success(
        val travel: NewsTopic = NewsTopic("travel",listOf(NewsItem())),
        val world: NewsTopic = NewsTopic("world",listOf(NewsItem())),
        val education: NewsTopic = NewsTopic("education",listOf(NewsItem())),
        val sports: NewsTopic = NewsTopic("sports",listOf(NewsItem())),
        val business: NewsTopic = NewsTopic("business",listOf(NewsItem())),
        val trend: NewsTopic = NewsTopic("trend",listOf(NewsItem())),
//        val technology: NewsTopic = NewsTopic("",listOf(NewsItem())),
//        val society: NewsTopic = NewsTopic("",listOf(NewsItem())),
//        val science: NewsTopic = NewsTopic("",listOf(NewsItem())),
//        val football: NewsTopic = NewsTopic("",listOf(NewsItem()))
    ) : NewsUiState

    object Loading : NewsUiState
    object Error : NewsUiState
}