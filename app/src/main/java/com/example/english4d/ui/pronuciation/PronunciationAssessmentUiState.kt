package com.example.english4d.ui.pronuciation

import com.example.english4d.data.database.vocabulary.Vocabulary
enum class RecordingState {
    RECORDING,
    NOTRECORDING,
    PROCESSING
}
data class PronunciationAssessmentUiState(
    val score: Int= 0,
    val vocabulary: Vocabulary = Vocabulary(0,"","","","",0),
    val isRecording: RecordingState  = RecordingState.NOTRECORDING,
    val isSpeak: Boolean = false,
    val isListen: Boolean = false
)
data class PronunciationStatisticUiState(
    val lower: List<Vocabulary> = listOf(),
    val medium: List<Vocabulary> = listOf(),
    val high: List<Vocabulary> = listOf()
)
