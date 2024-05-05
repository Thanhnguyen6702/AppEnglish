package com.example.english4d.ui.newspaper

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.english4d.data.database.question.Article
import com.example.english4d.data.database.question.Question
import com.example.english4d.data.database.question.QuestionRepository
import com.example.english4d.data.news.NewsContent
import com.example.english4d.data.news.NewsItem
import com.example.english4d.data.news.NewsRepository
import com.example.english4d.data.news.NewsTopic
import com.example.english4d.data.news.QuestionGPT
import com.example.english4d.model.GenerateContent
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class NewsViewmodel(
    private val newsRepository: NewsRepository,
    private val questionRepository: QuestionRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<NewsUiState>(NewsUiState.Loading)
    val uiState:StateFlow<NewsUiState> = _uiState.asStateFlow()
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
            _uiState.value = NewsUiState.Loading
            _uiState.value = try {
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
            getListQuestion(url)
        }
    }

    fun insertArticle(href: String, title: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                questionRepository.insertArticle(Article(href = href, type = title))
            }
        }
    }

    private fun getListQuestion(href: String) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    val questions = questionRepository.getArticleAndQuestion(Uri.encode(href))
                    if (questions.questions.isEmpty()) {
                        val contentNews = dataNews.contentNews.filter { it.type == "text" }
                            .joinToString(separator = "") { it.content }
                        val generateQuestion = GenerateContent()
                        val dataQuestion = generateQuestion.response(contentNews)
                        val gson = Gson()
                        val listQuestionGPT =
                            gson.fromJson(dataQuestion, Array<QuestionGPT>::class.java).toList()
                        questionUiState = QuestionUiState(listQuestionGPT = listQuestionGPT)
                        val listQuestionDB = listQuestionGPT.map { Question(question = it.question, options = it.options, answer = it.answer, explanation = it.explanation, id_article = questions.article.id) }
                        questionRepository.insertQuestion(listQuestionDB)
                    } else {
                        val listQuestionGPT = questions.questions.map {
                            QuestionGPT(
                                question = it.question,
                                options = it.options,
                                answer = it.answer,
                                explanation = it.explanation
                            )
                        }
                        questionUiState = QuestionUiState(listQuestionGPT = listQuestionGPT)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}