package com.example.english4d.ui.vocabulary

import com.example.english4d.data.database.vocabulary.Vocabulary

enum class StatusWord{
    UNCHOOSE,
    CORRECT,
    UNCORRECT,
    DISABLE
}
data class ReviseUiState (
    val isQuestionText: Boolean = true,
    val currentNumber: Int = 1,
    val totalNumber: Int = 30,
    val listVocabMixed: List<Vocabulary> = listOf(Vocabulary(0,"","","","",0),Vocabulary(0,"","","","",0),Vocabulary(0,"","","","",0),Vocabulary(0,"","","","",0)),
    val vocabulary: Vocabulary = Vocabulary(0,"","","","",0),
    val listStatus: List<StatusWord> = listOf(StatusWord.UNCHOOSE,StatusWord.UNCHOOSE,StatusWord.UNCHOOSE,StatusWord.UNCHOOSE),
    val enableChoose: Boolean = true,
    val isFinish: Boolean = false
)
