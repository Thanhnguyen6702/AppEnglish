package com.example.englishe4.presentation.cardFlip

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.english4d.data.database.wordstore.MyWord
import com.example.english4d.data.database.wordstore.MyWordWithDetails
import com.wajahatkarim.flippable.FlipAnimationType
import com.wajahatkarim.flippable.Flippable
import com.wajahatkarim.flippable.FlippableController

@Composable
fun CardWordItemFlip(word: MyWordWithDetails) {
    var duration: Int by remember { mutableStateOf(400) }
    var flipOnTouchEnabled: Boolean by remember { mutableStateOf(true) }
    val flipEnabled: Boolean by remember { mutableStateOf(true) }
    var autoFlipEnabled: Boolean by remember { mutableStateOf(false) }
    var selectedAnimType: FlipAnimationType by remember { mutableStateOf(FlipAnimationType.VERTICAL_ANTI_CLOCKWISE) }

    val flipController = remember(key1 = "1") {
        FlippableController()
    }

    Flippable(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(20.dp),
        frontSide = {
            EnglishWordFrontSide(flipController, word.myword )
        },
        backSide = {
            EnglishWordBackSide(flipController, word.myword)
        },
        flipController = flipController,
        flipDurationMs = duration,
        flipOnTouch = flipOnTouchEnabled,
        flipEnabled = flipEnabled,
        autoFlip = autoFlipEnabled,
        autoFlipDurationMs = 2000,
        flipAnimationType = selectedAnimType
    )
}

@Composable
fun EnglishWordFrontSide(
    flipController: FlippableController,
    word: MyWord
) {
    CardFlip(
        onFlip = {
            flipController.flip()
        },
        word = word,
        isFont = true
    )
}

@Composable
fun EnglishWordBackSide(
    flipController: FlippableController,
    word: MyWord
) {
    CardFlip(
        onFlip = {
            flipController.flip()
        },
        word = word,
        isFont = false
    )

}
