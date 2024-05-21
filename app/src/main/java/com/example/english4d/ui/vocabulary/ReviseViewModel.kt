package com.example.english4d.ui.vocabulary

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.english4d.data.database.vocabulary.Vocabulary
import com.example.english4d.data.database.vocabulary.VocabularyRepository
import com.example.english4d.utils.TextToSpeechManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

private const val TIME_DELAY:Long = 1000
data class ItemFinish(
    val vocabulary: Vocabulary,
    val dayRevise: Int
)
class ReviseViewModel(private val vocabularyRepository: VocabularyRepository,context: Context) : ViewModel() {
    private val _uiState = MutableStateFlow(ReviseUiState())
    val uiState: StateFlow<ReviseUiState> = _uiState.asStateFlow()
    private val _uiStateFinish = MutableStateFlow(FinishUiState())
    val uiStateFinish: StateFlow<FinishUiState> = _uiStateFinish.asStateFlow()
    var numberRevise = 0
    private lateinit var listVocabulary :List<Vocabulary>
    private lateinit var listVocabRevise: List<Vocabulary>
    private var index = 0
    private var selected = false
    private val playSound: TextToSpeechManager = TextToSpeechManager(context)
    init {
        initVocab()
    }
    private fun initVocab(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                listVocabulary = vocabularyRepository.getVocabulary()
                listVocabRevise = vocabularyRepository.getRevise()
                shuffleWords()
            }
        }
    }
    private fun shuffleWords(){
        val listRandom: MutableList<Vocabulary> = mutableListOf()
        listRandom.add(listVocabRevise[index])
        val sizeVocab = listVocabulary.size
        while (listRandom.size < 4){
            val i = Random.nextInt(sizeVocab)
            if(!listRandom.contains(listVocabulary[i])){
                listRandom.add(listVocabulary[i])
            }
        }
        listRandom.shuffle()
        _uiState.value = _uiState.value.copy(isQuestionText = Random.nextBoolean(),currentNumber = index+1, totalNumber = listVocabRevise.size, listVocabMixed = listRandom, vocabulary = listVocabRevise[index])
    }
    fun nextWord(pos: Int){
        if(index < listVocabRevise.size && _uiState.value.listVocabMixed[pos] == listVocabRevise[index]) {
            numberRevise++
            viewModelScope.launch {
                index++
                if (!selected) vocabularyRepository.chooseAnswer(1,_uiState.value.vocabulary.id)
                selected = false
                delay(TIME_DELAY)
                if(index < listVocabRevise.size) {
                    shuffleWords()
                    resetStatusWord()
                }else{
                    _uiState.value = _uiState.value.copy(isFinish = true)
                }
            }
        }else{
            viewModelScope.launch{
                if (!selected) vocabularyRepository.chooseAnswer(0,_uiState.value.vocabulary.id)
                selected = true
            }
        }
    }
    fun setStatusWord(pos: Int){
        val listStatusWord: MutableList<StatusWord> = _uiState.value.listStatus.toMutableList()
        if(_uiState.value.listVocabMixed[pos] == listVocabRevise[index]){
            listStatusWord[pos] = StatusWord.CORRECT
            _uiState.value = _uiState.value.copy(listStatus = listStatusWord, enableChoose = false)
        }else{
            viewModelScope.launch {
                listStatusWord[pos] = StatusWord.UNCORRECT
                _uiState.value = _uiState.value.copy(listStatus = listStatusWord, enableChoose = false)
                delay(TIME_DELAY)
                listStatusWord[pos] = StatusWord.DISABLE
                _uiState.value = _uiState.value.copy(listStatus = listStatusWord, enableChoose = true)
            }
        }
    }
    private fun resetStatusWord(){
        val listStatusWord = listOf(StatusWord.UNCHOOSE,StatusWord.UNCHOOSE,StatusWord.UNCHOOSE,StatusWord.UNCHOOSE)
        _uiState.value = _uiState.value.copy(listStatus = listStatusWord, enableChoose = true)
    }

    fun updateItemFinish() {
         viewModelScope.launch{
            withContext(Dispatchers.IO){
                val listItemFinish: MutableList<ItemFinish> = mutableListOf()
                for (i in 0..<numberRevise){
                    val dataItem = vocabularyRepository.getItemStatistic(listVocabRevise[i].id)
                    val dayRevise = dataItem.check_day % 10
                    val itemFinish = ItemFinish(vocabulary =  listVocabRevise[i],dayRevise)
                    listItemFinish.add(itemFinish)
                }
                val isContinueRevise = (numberRevise < listVocabRevise.size)
                _uiStateFinish.value = _uiStateFinish.value.copy(listItemFinish = listItemFinish,isContinueRevise = isContinueRevise)
            }
        }
    }
    fun speak(){
        playSound.speak(_uiState.value.vocabulary.english)
    }
}