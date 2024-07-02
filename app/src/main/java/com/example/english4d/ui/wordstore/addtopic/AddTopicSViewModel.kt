package com.example.english4d.ui.wordstore.addtopic

import android.content.Context
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.english4d.R
import com.example.english4d.data.database.wordstore.DictionaryResponse
import com.example.english4d.data.database.wordstore.MyWordRepository
import com.example.english4d.data.database.wordstore.MyWordTopic
import com.example.english4d.model.ResponseData
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


class AddWordSViewModel(
   private val repository: MyWordRepository,
    context: Context
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddWordUiState())
    val uiState: StateFlow<AddWordUiState> = _uiState.asStateFlow()
    private val _dataFind = MutableStateFlow<DictionaryResponse?>(null)
    val dataFind: StateFlow<DictionaryResponse?> = _dataFind
    val trie = Trie()
    var gson: Gson? = null

    init {
        gson = Gson()
        readRawTextFile(context).forEach {
            it.content?.let { it1 -> trie.insert(it1) }
        }
    }

    val topicCards = uiState.value.contentSearch
        .debounce(300)  // Debounce to reduce search frequency
        .flatMapLatest { query ->
            flow {
                emit(trie.search(query)
                    .take(10)
                    .map {
                        WordCard(content = it)
                    })
            }.flowOn(Dispatchers.Default)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()  // Initial value for _topicCards
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

    fun onTextChange(newValue: String) {
        _searchText.value = newValue
    }

    fun onWordChange(newValue: String) {
        _searchWord.value = newValue
    }

    fun submit(word: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val jsonString = ResponseData(word)
            Log.d("vm", "jsonString: $jsonString")
            gson?.fromJson(jsonString, DictionaryResponse::class.java)?.let { entry ->
                _listData.update { currentList ->
                    currentList + entry
                }
            }
            Log.d("vm", "submit: ${listData.value}")

        }
    }

    fun findDataByItem(word: String) {
        val filteredList = _listData.value.find { item ->
            item.response == word
        }
        _dataFind.value = filteredList
        Log.d("dataVM", "findDataByItem: $word")
        Log.d("dataVM", "findDataByItem: ${listData.value} ")
        Log.d("dataVM", "findDataByItem: ${dataFind.value} ")
    }
    fun addTopic(title: String){
        viewModelScope.launch(Dispatchers.IO) {
           val topic_id =  repository.insertTopic(MyWordTopic(name = title))
            insertMyWordDatabase(repository = repository, dictionaryMyWord = _listData.value[0], topic_id = topic_id)
        }
    }
    fun getTopic(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val topicDetail = repository.getTopic(id)
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
