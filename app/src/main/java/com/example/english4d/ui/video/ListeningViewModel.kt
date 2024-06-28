package com.example.english4d.ui.video

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.withStyle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.english4d.R
import com.example.english4d.network.video.CaptionTrack
import com.example.english4d.network.video.OnlineVideoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class ListeningViewModel(private val context: Context) : ViewModel() {
    private val _uiState = MutableStateFlow<ListeningUiState>(ListeningUiState.Loading)
    val uiState: StateFlow<ListeningUiState> = _uiState.asStateFlow()
    private val captionTrackRepository = OnlineVideoRepository().videoRepository
    var listAnswer: List<String> = listOf()
    fun getCaptionTrack(videoId: String) {
        viewModelScope.launch {
            try {
                _uiState.value = ListeningUiState.Loading
                val transcript =
                    captionTrackRepository.getCaptionTrack(videoId).transcript.filterNot { it.text[0] == '[' }
                _uiState.value =
                    ListeningUiState.Success(captionTrack = CaptionTrack(transcript = transcript))
            } catch (e: Exception) {
                _uiState.value = ListeningUiState.Error
            }

        }
    }

    fun readTextFileFromRaw(): List<String> {
        val inputStream = context.resources.openRawResource(R.raw.words_5k)
        return inputStream.bufferedReader().use { it.readLines() }
    }

    fun getCaptionTrackWithSelectWord(videoId: String) {
        viewModelScope.launch {
            try {
                _uiState.value = ListeningUiState.Loading
                listAnswer = readTextFileFromRaw()
                val transcript =
                    captionTrackRepository.getCaptionTrack(videoId).transcript.filterNot { it.text[0] == '[' }
                val transcriptWithSelectWord = transcript.map {
                    val listWord = it.text.split(" ")
                    val posHide = randomPos(listWord.size)
                    ItemCaptionTrackSeparation(
                        listWord = listWord,
                        posHide = posHide,
                        start = it.start,
                        duration = it.duration
                    )
                }
                _uiState.value =
                    ListeningUiState.Success(
                        captionTrack = CaptionTrack(transcript = transcript),
                        captionTrackSeparation = CaptionTrackSeparation(
                            transcript = transcriptWithSelectWord
                        )
                    )
            } catch (e: Exception) {
                _uiState.value = ListeningUiState.Error
            }

        }
    }

    private fun randomPos(numberWord: Int): List<Int> {
        return if (numberWord == 1) {
            listOf(0)
        } else {
            val indices = (0 until numberWord).toMutableList()
            indices.shuffle()
            val first = indices[0]
            val second = indices[1]
            if (first < second) listOf(first, second) else listOf(second, first)
        }
    }

    fun getDisplayText(
        itemCaptionTrackSeparation: ItemCaptionTrackSeparation
    ): AnnotatedString {
        val builder = AnnotatedString.Builder()

        val posHideSet = itemCaptionTrackSeparation.posHide.toHashSet()
        val posHideSize = itemCaptionTrackSeparation.posHide.size
        val indexStop = itemCaptionTrackSeparation.indexStop

        itemCaptionTrackSeparation.listWord.forEachIndexed { index, word ->
            when {
                index in posHideSet && indexStop < posHideSize && index >= itemCaptionTrackSeparation.posHide[indexStop] -> {
                    if (index == itemCaptionTrackSeparation.posHide[indexStop]) {
                        builder.append("⨀ ")
                    } else {
                        builder.append("● ")
                    }
                }

                else -> {
                    val spanStyle =
                        if (index in posHideSet) SpanStyle(color = Color.Blue) else SpanStyle()
                    builder.withStyle(style = spanStyle) {
                        append("$word ")
                    }
                }
            }
        }
        return builder.toAnnotatedString()
    }

    fun shuffleAnswer(answer: String) {
        val answers = mutableListOf(answer)
        val size = listAnswer.size
        while (answers.size < 4) {
            val index = Random.nextInt(size)
            if (!answer.contains(listAnswer[index])) {
                answers.add(listAnswer[index])
            }
        }
        answers.shuffle()
        _uiState.value =
            (_uiState.value as ListeningUiState.Success).copy(answers = Pair(answer, answers))
    }

    fun nextQuestion() {
        val currentState = _uiState.value as ListeningUiState.Success
        val updatedTranscript = currentState.captionTrackSeparation.transcript.toMutableList()
        val index = currentState.captionTrackSeparation.indexItem
        if (updatedTranscript[index].indexStop < updatedTranscript[index].posHide.size - 1) {
            val updatedEntry =
                updatedTranscript[index].copy(indexStop = updatedTranscript[index].indexStop + 1)
            updatedTranscript[index] = updatedEntry
            val updatedCaptionTrackSeparation =
                currentState.captionTrackSeparation.copy(transcript = updatedTranscript)
            _uiState.value =
                currentState.copy(captionTrackSeparation = updatedCaptionTrackSeparation)
        } else {
            val maxIndexItem = updatedTranscript.size - 1
            val updatedEntry =
                updatedTranscript[index].copy(indexStop = updatedTranscript[index].indexStop + 1)
            updatedTranscript[index] = updatedEntry
            val updatedCaptionTrackSeparation =
                currentState.captionTrackSeparation.copy(
                    transcript = updatedTranscript,
                    indexItem = if (index < maxIndexItem - 1) {
                        index + 1
                    } else {
                        index
                    }
                )
            _uiState.value =
                currentState.copy(captionTrackSeparation = updatedCaptionTrackSeparation)
        }
    }
}