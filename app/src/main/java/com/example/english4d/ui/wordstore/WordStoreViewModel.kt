package com.example.english4d.ui.wordstore

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.english4d.R
import com.example.english4d.data.database.wordstore.DictionaryResponse
import com.example.english4d.data.database.wordstore.MyWordRepository
import com.example.english4d.data.database.wordstore.MyWordTopic
import com.example.english4d.model.ResponseData
import com.example.english4d.ui.wordstore.addtopic.AddWordUiState
import com.example.english4d.ui.wordstore.addtopic.insertMyWordDatabase
import com.example.englishe4.common.Trie
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader

class WordStoreViewModel(
    private val repository: MyWordRepository,
    context: Context
) : ViewModel() {
    val topic: StateFlow<List<MyWordTopic>> =
        repository.getTopics().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _uiState = MutableStateFlow(AddWordUiState())
    val uiState: StateFlow<AddWordUiState> = _uiState.asStateFlow()
    val trie = Trie()
    var gson: Gson? = null

    init {
        gson = Gson()
        readRawTextFile(context).forEach {
            it.content?.let { it1 -> trie.insert(it1) }
        }
    }

    val topicCards = uiState
        .debounce(300)  // Debounce to reduce search frequency
        .flatMapLatest { query ->
            flow {
                emit(trie.search(query.contentSearch)
                    .take(10)
                    .map {
                        WordCard(content = it)
                    })
            }.flowOn(Dispatchers.Default)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    private fun readRawTextFile(context: Context): List<WordCard> {
        val inputStream = context.resources.openRawResource(R.raw.wordlist)
        val reader = BufferedReader(InputStreamReader(inputStream))
        val content = mutableListOf<WordCard>()
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            content.add(WordCard(content = line))
        }
        reader.close()
        return content
    }

    fun onWordChange(newValue: String) {
        _uiState.update {
            it.copy(
                contentSearch = newValue
            )
        }
    }

    fun onTitleChange(newValue: String) {
        _uiState.update {
            it.copy(
                title = newValue
            )
        }
    }

    fun onChangeTopic(newValue: String) {
        _uiState.update {
            it.copy(
                topic = newValue
            )
        }
    }

    fun submit(word: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(
                    contentSearch = word
                )
            }
            val jsonString = ResponseData(word)
            gson?.fromJson(jsonString, DictionaryResponse::class.java)?.let { entry ->
                _uiState.update {
                    val listData: MutableList<DictionaryResponse> = it.wordResult.toMutableList()
                    listData.add(entry)
                    it.copy(
                        wordResult = listData
                    )
                }
            }

        }
    }


    fun addTopic(title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val topic_id = repository.insertTopic(MyWordTopic(name = title))
            _uiState.value.wordResult.forEach {
                insertMyWordDatabase(
                    repository = repository,
                    dictionaryMyWord = it,
                    topic_id = topic_id
                )
            }
        }
    }

    fun getItem(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(
                    listItem = repository.getTopic(id).words
                )
            }
            _uiState.value.listItem.forEach {
                getItemDetail(it.id)
            }
        }
    }

    fun getItemDetail(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {

            _uiState.update {
                val currentList = it.listItemDetail.toMutableList()
                currentList.add(repository.getMyWordDetail(id))
                it.copy(
                    listItemDetail = currentList
                )
            }
        }
    }
}


data class WordCard(
    val content: String?
) {
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            "$content",
        )

        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}
