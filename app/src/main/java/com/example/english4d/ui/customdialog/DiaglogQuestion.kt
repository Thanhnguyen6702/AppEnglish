package com.example.english4d.ui.customdialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import com.example.english4d.R
import com.example.english4d.data.database.question.Question
import com.example.english4d.ui.newspaper.GroupItemAnswer
import com.example.english4d.ui.theme.TypeText

@Composable
fun DialogQuestion(
    numberQuestion: Int,
    question: Question,
    onDismissRequest: () -> Unit,
    onConfirmRequest: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    color = colorResource(id = R.color.gray_10)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            IconButton(onClick =  onDismissRequest ) {
                                Icon(imageVector = Icons.Default.Close, contentDescription = null)
                            }
                            Text(
                                text = stringResource(
                                    id = R.string.question_number,
                                    numberQuestion
                                ), style = TypeText.bodyMedium
                            )
                            IconButton(onClick = { /*TODO*/ }) {
                                
                            }
                        }
                        Text(
                            text = question.question,
                            style = TypeText.h7.copy(fontWeight = FontWeight.Medium),
                            modifier = Modifier.padding(
                                all = dimensionResource(id = R.dimen.padding_medium)
                            )
                        )
                    }
                }
                GroupItemAnswer(
                    onClick = {
                    },
                    options = question.options,
                    selectOption = question.answer
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = dimensionResource(id = R.dimen.padding_medium))
                ) {
                    Text(
                        text = "Explain:", style = TypeText.h7.copy(
                            fontWeight = FontWeight.Medium, color = colorResource(
                                id = R.color.green_100
                            )
                        )
                    )
                    Text(
                        text = question.explanation,
                        style = TypeText.h8.copy(
                            fontWeight = FontWeight.Medium,
                            fontStyle = FontStyle.Italic
                        )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewQuestionDialog() {
    DialogQuestion(numberQuestion = 1, question = Question(
        question = "What was the fine imposed on the female Russian tourist for kicking the pregnant woman?",
        options = mapOf(
            "a" to "500 baht",
            "b" to "1,000 baht",
            "c" to "1,500 baht",
            "d" to "2,000 baht"
        ),
        answer = "a",
        explanation = "The female Russian tourist was fined 500 baht (US$13) for kicking the pregnant woman.",
        id_article = 1
    ), onDismissRequest = { /*TODO*/ }) {

    }
}