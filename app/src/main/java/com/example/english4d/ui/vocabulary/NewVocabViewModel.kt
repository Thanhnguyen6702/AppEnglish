package com.example.english4d.ui.vocabulary

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.english4d.data.database.vocabulary.Statistic
import com.example.english4d.data.database.vocabulary.Vocabulary
import com.example.english4d.data.database.vocabulary.VocabularyRepository
import com.example.english4d.utils.TextToSpeechManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewVocabViewModel(
    private val vocabularyRepository: VocabularyRepository,
    context: Context
) : ViewModel() {
    private val _uiState = MutableStateFlow(NewVocabUiState())
    val uiState: StateFlow<NewVocabUiState> = _uiState.asStateFlow()
    private var vocabularies: MutableList<Vocabulary> = mutableListOf()
    private val playSound = TextToSpeechManager(context)
    private var index = 0
    private var isShowedE = false
    private var isShowedD = false
    fun updateLayout(id: Int) {
        viewModelScope.launch {
            withContext(context = Dispatchers.IO) {
                val title = vocabularyRepository.getTopic(id)
                vocabularies.addAll(vocabularyRepository.getNewVocabulary(id))
                _uiState.value =
                    _uiState.value.copy(title = title.topic, vocabulary = vocabularies[index])
            }
        }
    }

    fun getDefinitions() {
        if (!isShowedD) {
            isShowedD = true
            viewModelScope.launch {
                withContext(context = Dispatchers.IO) {
                    val listDefinitions =
                        vocabularyRepository.getDefinition(vocabularies[index].id)
                    _uiState.value = _uiState.value.copy(definitions = listDefinitions)
                }
            }
        }
    }

    fun getExamples() {
        if (!isShowedE) {
            isShowedE = true
            viewModelScope.launch {
                withContext(context = Dispatchers.IO) {
                    val listExamples = vocabularyRepository.getExample(vocabularies[index].id)
                    _uiState.value = _uiState.value.copy(examples = listExamples)
                }
            }
        }
    }

    fun nextVocab() {
        if (index < vocabularies.size - 1) {
            index++;
            isShowedD = false
            isShowedE = false
            _uiState.value = _uiState.value.copy(vocabulary = vocabularies[index], statusVocab = StatusVocab.UNCHOOSE)
        }
    }

    fun backVocab() {
        if (index > 0) {
            index--
            isShowedD = false
            isShowedE = false
            _uiState.value = _uiState.value.copy(vocabulary = vocabularies[index], statusVocab = StatusVocab.UNCHOOSE)
        }
    }

    fun setStatus(statusVocab: StatusVocab) {
        _uiState.value = _uiState.value.copy(statusVocab = statusVocab)
    }

     fun updateStatistic() {
        if (_uiState.value.statusVocab != StatusVocab.UNCHOOSE) {
            val statistic: Statistic = when (_uiState.value.statusVocab) {
                StatusVocab.MASTER -> Statistic(id_vocab = _uiState.value.vocabulary.id, master = 1, check_day = 0)
                StatusVocab.UNLEARNED -> Statistic(
                    id_vocab = _uiState.value.vocabulary.id,
                    unlearned = 1,
                    check_day = 85321
                )

                StatusVocab.UNCERTAIN -> Statistic(
                    id_vocab = _uiState.value.vocabulary.id,
                    learning = 1,
                    check_day = 1
                )
                StatusVocab.UNCHOOSE -> TODO()
            }
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    if(index < vocabularies.size-1) {
                        vocabularyRepository.insertStatistic(statistic)
                        vocabularies.removeAt(index)
                        isShowedD = false
                        isShowedE = false
                        _uiState.value = _uiState.value.copy(
                            vocabulary = vocabularies[index],
                            statusVocab = StatusVocab.UNCHOOSE
                        )
                    }else{
                        _uiState.value = _uiState.value.copy(isFinish = true)
                    }
                }
            }
        }
    }
    fun speak(){
        playSound.speak(_uiState.value.vocabulary.english)
    }
}