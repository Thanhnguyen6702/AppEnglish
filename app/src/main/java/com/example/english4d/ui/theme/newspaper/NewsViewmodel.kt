package com.example.english4d.ui.theme.newspaper

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.english4d.NewsApplication
import com.example.english4d.data.news.NewsContent
import com.example.english4d.data.news.NewsItem
import com.example.english4d.data.news.NewsRepository
import com.example.english4d.data.news.NewsTopic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class NewsViewmodel(private val newsRepository: NewsRepository) : ViewModel() {
    var newsUiState: NewsUiState by mutableStateOf(NewsUiState.Loading)
        private set
    var newsContent: ContentUiState by mutableStateOf(ContentUiState())
    init {
        getListTopic()
    }

    private fun NewsUiState.Success.updateState(update: NewsUiState.Success.() -> NewsUiState.Success): NewsUiState {
        return update(this)
    }
    private fun ContentUiState.updateState(contenNews: List<NewsContent>): ContentUiState{
        return this.copy(contentNews = contenNews)
    }
    private fun getListTopic() {
        viewModelScope.launch {
            newsUiState = NewsUiState.Loading
            newsUiState = try {
                val travel = withContext(Dispatchers.IO) {
                    val listItem = newsRepository.getListNews("travel")
                    NewsTopic("Travel", listItem ?: listOf(NewsItem()))
                }
                val world = withContext(Dispatchers.IO) {
                    val listItem = newsRepository.getListNews("world")
                    NewsTopic("World", listItem ?: listOf(NewsItem()))
                }
                val education = withContext(Dispatchers.IO) {
                    val listItem = newsRepository.getListNews("news/education")
                    NewsTopic("Education", listItem ?: listOf(NewsItem()))
                }
                val sports = withContext(Dispatchers.IO) {
                    val listItem = newsRepository.getListNews("sports")
                    NewsTopic("Sports", listItem ?: listOf(NewsItem()))
                }
                val business = withContext(Dispatchers.IO) {
                    val listItem = newsRepository.getListNews("business")
                    NewsTopic("Business", listItem ?: listOf(NewsItem()))
                }
                val trend = withContext(Dispatchers.IO) {
                    val listItem = newsRepository.getListNews("life/trend")
                    NewsTopic("Trend", listItem ?: listOf(NewsItem()))
                }
//                val society= withContext(Dispatchers.IO) {
//                      val listItem =  newsRepository.getListNews("travel")
//                    NewsTopic("travel",listItem?: listOf(Pair("","")))
//                }
//                val technology= withContext(Dispatchers.IO) {
//                      val listItem =  newsRepository.getListNews("travel")
//                    NewsTopic("travel",listItem?: listOf(Pair("","")))
//                }
//                val science= withContext(Dispatchers.IO) {
//                  val listItem =  newsRepository.getListNews("travel")
//                    NewsTopic("travel",listItem?: listOf(Pair("","")))
//                }
//                val football= withContext(Dispatchers.IO) {
//                     val listItem =  newsRepository.getListNews("travel")
//                    NewsTopic("travel",listItem?: listOf(Pair("","")))
//                }
                NewsUiState.Success().updateState {
                    copy(
                        travel = travel,
                        world = world,
                        education = education,
                        sports = sports,
                        business = business,
                        trend = trend
                    )
                }
            } catch (e: Exception) {
                NewsUiState.Error
            }
        }
    }
     fun getNewsPaper(url:String){
    viewModelScope.launch {
        withContext(Dispatchers.IO){
            val contenNews = newsRepository.getContentNews(url)
            newsContent.updateState(contenNews)
        }
    }
}
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as NewsApplication)
                val newsRepository = application.container.newsRepository
                NewsViewmodel(newsRepository = newsRepository)
            }
        }
    }
}