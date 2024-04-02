package com.example.english4d.ui.theme.newspaper

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.english4d.R
import com.example.english4d.data.news.Question
import com.example.english4d.ui.theme.TypeText

enum class Status {
    UNCHECK,
    CORRECT,
    UNCORRECT
}

@Composable
fun QuestionLayout(
    questions: Question
) {
    Column {
        var selectOption by remember { mutableIntStateOf(0) }
        val listQuestion = questions.options
        val listSelection = remember { mutableStateListOf("", "", "", "") }
        Text(
            text = questions.question, style = TypeText.bodyMedium, modifier = Modifier.padding(
                start = dimensionResource(
                    id = R.dimen.padding_small
                )
            )
        )
        ItemQuestion(
            question = listQuestion.get("a") ?: "", selectOption == 1,
            status = getStatus(listSelection[0], questions.answer)
        ) {
            listSelection[0] = "a"
            selectOption = 1
        }
        ItemQuestion(
            question = listQuestion.get("b") ?: "", selectOption == 2,
            status = getStatus(listSelection[1], questions.answer)
        ) {
            listSelection[1] = "b"
            selectOption = 2
        }
        ItemQuestion(
            question = listQuestion.get("c") ?: "", selectOption == 3,
            status = getStatus(listSelection[2], questions.answer)
        ) {
            listSelection[2] = "c"
            selectOption = 3
        }
        ItemQuestion(
            question = listQuestion.get("d") ?: "", selectOption == 4,
            status = getStatus(listSelection[3], questions.answer)
        ) {
            listSelection[3] = "d"
            selectOption = 4
        }
    }
}

@Composable
fun ItemQuestion(
    question: String,
    selected: Boolean,
    status: Status = Status.UNCHECK,
    onClick: () -> Unit
) {
    var style: TextStyle
    when (status) {
        Status.UNCHECK -> style = TypeText.bodyMedium
        Status.CORRECT -> style = TypeText.bodyMedium.copy(color = Color.Green)
        Status.UNCORRECT -> style = TypeText.bodyMedium.copy(color = Color.Red)
    }
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(selected = selected, onClick = onClick)
        Text(text = question, style = style)
    }
}

fun getStatus(select: String, answer: String): Status {
    if (select == "") return Status.UNCHECK
    return if (select == answer) {
        Status.CORRECT
    } else {
        Status.UNCORRECT
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTest() {
    QuestionLayout(
        questions = Question(
            question = "What was the fine imposed on the female Russian tourist for kicking the pregnant woman?",
            options = mapOf(
                "a" to "500 baht",
                "b" to "1,000 baht",
                "c" to "1,500 baht",
                "d" to "2,000 baht"
            ),
            answer = "a",
            explanation = "The female Russian tourist was fined 500 baht (US$13) for kicking the pregnant woman."
        )
    )
}