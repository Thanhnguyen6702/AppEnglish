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
import com.example.english4d.data.news.Question
import com.example.english4d.model.GenerateContent
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class NewsViewmodel(private val newsRepository: NewsRepository) : ViewModel() {
    var newsUiState: NewsUiState by mutableStateOf(NewsUiState.Loading)
        private set
    var dataNews: ContentUiState by mutableStateOf(ContentUiState())
        private set
    var questionUiState: QuestionUiState by mutableStateOf(QuestionUiState())
        private set

    init {
        getListTopic()
    }

    private fun NewsUiState.Success.updateState(update: NewsUiState.Success.() -> NewsUiState.Success): NewsUiState {
        return update(this)
    }

    private fun ContentUiState.updateState(data: List<NewsContent>): ContentUiState {
        return this.copy(contentNews = data)
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

    fun getNewsPaper(url: String) {
        viewModelScope.launch {
            dataNews = withContext(Dispatchers.IO) {
                val contenNews = newsRepository.getContentNews(url)
                dataNews.updateState(contenNews)
            }
            getListQuestion()
        }
    }

    private fun getListQuestion() {
        val contentNews = dataNews.contentNews.filter { it.type == "text" }.map { it.content }
            .joinToString(separator = "")
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    val generateQuestion = GenerateContent()
                    val dataQuestion = generateQuestion.response(contentNews)
                    val gson = Gson()
                    val listQuestion =
                        gson.fromJson(dataQuestion, Array<Question>::class.java).toList()
                    questionUiState = QuestionUiState(listQuestion = listQuestion)
                }
            }catch (e:Exception){
                e.printStackTrace()
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