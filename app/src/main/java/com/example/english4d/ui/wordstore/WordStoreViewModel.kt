package com.example.english4d.ui.wordstore

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.english4d.R
import com.example.english4d.data.database.wordstore.DictionaryResponse
import com.example.english4d.data.database.wordstore.MyWordRepository
import com.example.english4d.data.database.wordstore.MyWordTopic
import com.example.english4d.data.workers.VocabWorkerRepository
import com.example.english4d.model.ResponseData
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
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader

class WordStoreViewModel(
    workerRepository: VocabWorkerRepository,
    private val repository: MyWordRepository,
    context: Context
) : ViewModel() {
    val topic: StateFlow<List<MyWordTopic>> =
        repository.getTopics().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _uiState = MutableStateFlow(WordStoreUiState())
    val uiState: StateFlow<WordStoreUiState> = _uiState.asStateFlow()
    val trie = Trie()
    var gson: Gson? = null

    init {
        workerRepository.applyVocab()

        val sharedPreferences = context.getSharedPreferences("worker_prefs", Context.MODE_PRIVATE)
        val workerState = sharedPreferences.getBoolean("worker_state", false)
        if (!workerState) {
            sharedPreferences.edit().putBoolean("worker_state", true).apply()
        }
        gson = Gson()
        readRawTextFile(context).forEach {
            it.let { it1 -> trie.insert(it1) }
        }
    }

    val topicCards = uiState
        .debounce(300)  // Debounce to reduce search frequency
        .flatMapLatest { query ->
            flow {
                emit(
                    trie.search(query.contentSearch)
                        .take(10)
                )
            }.flowOn(Dispatchers.Default)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    private fun readRawTextFile(context: Context): List<String> {
        val inputStream = context.resources.openRawResource(R.raw.wordlist)
        val reader = BufferedReader(InputStreamReader(inputStream))
        val content = mutableListOf<String>()
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            line?.let { content.add(it) }
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


    fun submit(word: String) {
        _uiState.update {
            it.copy(
                contentSearch = word,
                numberLoading1 = it.numberLoading1 + 1,
            )
        }
        viewModelScope.launch(Dispatchers.IO) {
            getWord(word)?.let { dictionaryResponse ->
                _uiState.update {
                    val listData: MutableList<DictionaryResponse> = it.wordResult.toMutableList()
                    listData.add(dictionaryResponse)
                    it.copy(
                        wordResult = listData,
                        numberLoading1 = it.numberLoading1 - 1
                    )
                }
            }
        }
    }

    private suspend fun getWord(word: String): DictionaryResponse? {
        val jsonString = ResponseData(word)
        return gson?.fromJson(jsonString, DictionaryResponse::class.java)
    }

    fun addTopic(title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val topic = repository.getTopicByNames(title)
            var topic_id: Long
            if(topic == null) {
                 topic_id = repository.insertTopic(MyWordTopic(name = title))
            }else{
                topic_id = topic.id
            }
            _uiState.value.wordResult.forEach {
                if (it.response !=null && repository.isMyWordExist(it.response!!) == 0)
                insertMyWordDatabase(
                    repository = repository,
                    dictionaryMyWord = it,
                    topic_id = topic_id
                )
            }
        }
    }

    fun addWord(word: String, topic_id: Long) {
        _uiState.update {
            it.copy(
                numberLoading2 = it.numberLoading2 + 1
            )
        }
        viewModelScope.launch(Dispatchers.IO) {
            val dictionaryMyWord = getWord(word)
            dictionaryMyWord?.let {
                insertMyWordDatabase(
                    repository = repository,
                    dictionaryMyWord = dictionaryMyWord,
                    topic_id = topic_id
                )
            }
            getItem(topic_id)
            _uiState.update {
                it.copy(
                    numberLoading2 = it.numberLoading2 - 1
                )
            }
        }
    }

     fun getItem(id: Long) {
         _uiState.update {
             it.copy(
                 listItemDetail = listOf()
             )
         }
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(
                    topicWithWords = repository.getTopic(id)
                )
            }
            _uiState.value.topicWithWords.words.forEach {
                getItemDetail(it.id)
            }
        }
    }


    private suspend fun getItemDetail(id: Long) {
        _uiState.update {
            val currentList = it.listItemDetail.toMutableList()
            val item = repository.getMyWordDetail(id)
            if (!currentList.contains(item)) {
                currentList.add(item)
            }
            it.copy(
                listItemDetail = currentList
            )
        }

    }

    fun deleteWordResult(pos: Int) {
        val listData: MutableList<DictionaryResponse> = _uiState.value.wordResult.toMutableList()
        listData.removeAt(pos)
        _uiState.update {
            it.copy(
                wordResult = listData
            )
        }
    }

    fun showDeleteWordResult(isShow: Boolean) {
        _uiState.update {
            it.copy(
                showRemoveWordResult = isShow
            )
        }
    }

    fun deleteItem(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMyWord(id)
            _uiState.update { wordStoreUiState ->
                wordStoreUiState.copy(
                    listItemDetail = wordStoreUiState.listItemDetail.filter { it.myword.id != id }
                )
            }
        }
    }

    fun deleteTopic(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTopic(id)
        }
    }

    fun showDeleteTopic(isShow: Boolean) {
        _uiState.update {
            it.copy(
                showRemoveTopic = isShow
            )
        }
    }

    fun showDeleteWord(isShow: Boolean) {
        _uiState.update {
            it.copy(
                showRemoveWord = isShow
            )
        }
    }

    suspend fun renameTopic(title: String): Boolean {
        return withContext(Dispatchers.IO) {
            if (repository.getTopicByNames(title) == null) {
                repository.renameTopic(_uiState.value.topicWithWords.topic.id, title)
                _uiState.update {
                    it.copy(
                        topicWithWords = repository.getTopic(_uiState.value.topicWithWords.topic.id)
                    )
                }
                true
            } else {
                false
            }
        }
    }
    fun updateStudyTopic(id: Long, study: Int){
        if(study != 0){
            viewModelScope.launch(Dispatchers.IO) {
                repository.setTopicStudy(id, 0)
            }
        }else{
            viewModelScope.launch(Dispatchers.IO) {
                repository.setTopicStudy(id, 853211)
            }
        }
    }
    fun setPosition(position: Int) {
        _uiState.update {
            it.copy(
                position = position
            )
        }
    }
}


