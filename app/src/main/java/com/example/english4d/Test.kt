package com.example.english4d

import android.widget.Toast
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun InteractiveWords() {
    var textValue by remember { mutableStateOf("This is a clickable text") }

    val context = LocalContext.current

    val annotatedString = buildAnnotatedString {
        append(textValue)
        addStyle(
            style = SpanStyle(textDecoration = TextDecoration.Underline),
            start = 0,
            end = textValue.length
        )
    }

    ClickableText(
        text = annotatedString,
        onClick = { offset ->
            val word = textValue.split(" ")[offset]
            Toast.makeText(context, "Clicked word: $word", Toast.LENGTH_SHORT).show()
        }
    )
}
@Preview(
    showBackground = true
)
@Composable
fun PreviewTest(){
    InteractiveWords()
}