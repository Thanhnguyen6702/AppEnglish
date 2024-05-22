package com.example.english4d.ui.newspaper

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.english4d.R
import com.example.english4d.data.database.question.Question
import com.example.english4d.navigation.ExtensionGraphScreen
import com.example.english4d.ui.AppViewModelProvider
import com.example.english4d.ui.theme.TypeText

@Composable
fun QuestionLayout(
    viewModel: ItemQuestionViewModel = viewModel(factory = AppViewModelProvider.Factory),
    questions: List<Question>,
    href: String,
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            color = colorResource(id = R.color.gray_10)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(
                        id = R.string.question_number,
                        uiState.questionNumber + 1
                    ), style = TypeText.bodyMedium
                )
                Text(
                    text = questions[uiState.questionNumber].question,
                    style = TypeText.h7.copy(fontWeight = FontWeight.Medium),
                    modifier = Modifier.padding(
                        all = dimensionResource(id = R.dimen.padding_medium)
                    )
                )
            }
        }
        GroupItemAnswer(
            onClick = {
                viewModel.setSelectOption(it, questions[uiState.questionNumber].id)
            },
            options = questions[uiState.questionNumber].options,
            answerCorrect = questions[uiState.questionNumber].answer,
            selectOption = uiState.answerSelected[uiState.questionNumber] ?: ""
        )
        ElevatedButton(
            enabled = uiState.questionNumber == questions.size - 1,
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = colorResource(id = R.color.green_100),
                disabledContainerColor = colorResource(
                    id = R.color.gray_50
                )
            ),
            onClick = {
                navController.navigate(ExtensionGraphScreen.QuestionStatistic.passHref(Uri.encode(href))) {
                    popUpTo(ExtensionGraphScreen.ReadNews.route) {
                        inclusive = true
                    }
                }
            }) {
            Text(
                text = "Hoàn thành", style = TypeText.h7.copy(
                    fontWeight = FontWeight.Medium, color = colorResource(
                        id = R.color.white
                    )
                ),
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_hight),
                    vertical = dimensionResource(id = R.dimen.padding_small)
                )
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = colorResource(id = R.color.green_10),
                    shape = RoundedCornerShape(100.dp)
                )
                .padding(bottom = dimensionResource(id = R.dimen.padding_hight)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(modifier = Modifier
                .padding(
                    horizontal = dimensionResource(id = R.dimen.padding_hight),
                    vertical = dimensionResource(
                        id = R.dimen.padding_hight
                    )
                )
                .size(36.dp)
                .background(
                    color = colorResource(id = R.color.white),
                    shape = RoundedCornerShape(100.dp)
                ), onClick = {
                viewModel.backQuestion()
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBackIosNew, contentDescription = null,
                    tint = if (uiState.questionNumber > 0) {
                        colorResource(id = R.color.black)
                    } else {
                        colorResource(id = R.color.gray_50)
                    }
                )
            }
            Text(
                text = "${uiState.questionNumber + 1} / ${questions.size}",
                style = TypeText.h6.copy(
                    fontWeight = FontWeight.Medium, color = colorResource(
                        id = R.color.purple_700
                    )
                ),
                textAlign = TextAlign.Center
            )

            IconButton(modifier = Modifier
                .padding(
                    horizontal = dimensionResource(id = R.dimen.padding_hight),
                    vertical = dimensionResource(
                        id = R.dimen.padding_hight
                    )
                )
                .size(36.dp)
                .background(
                    color = colorResource(id = R.color.white),
                    shape = RoundedCornerShape(100.dp)
                ), onClick = {
                viewModel.nextQuestion(questions.size)
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                    contentDescription = null,
                    tint = if (uiState.questionNumber < questions.size - 1) {
                        colorResource(id = R.color.black)
                    } else {
                        colorResource(id = R.color.gray_50)
                    }
                )
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemAnswer(
    modifier: Modifier = Modifier,
    answer: Pair<String, String>,
    selected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = if (!selected) {
                colorResource(id = R.color.white)
            } else {
                colorResource(id = R.color.purple_200)
            }
        ),
        shape = MaterialTheme.shapes.small
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium),
                    vertical = dimensionResource(
                        id = R.dimen.padding_small
                    )
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(
                        color = if (!selected) {
                            colorResource(id = R.color.white)
                        } else {
                            colorResource(id = R.color.pink_50)
                        }, shape = RoundedCornerShape(100.dp)
                    )
                    .border(
                        width = 1.dp, color = if (!selected) {
                            colorResource(id = R.color.black)
                        } else {
                            colorResource(id = R.color.white)
                        }, shape = RoundedCornerShape(100.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = answer.first.uppercase(),
                    modifier = Modifier.padding(5.dp),
                    style = TypeText.h6.copy(
                        fontWeight = FontWeight.Medium, color = if (!selected) {
                            colorResource(id = R.color.black)
                        } else {
                            colorResource(id = R.color.white)
                        }
                    )
                )
            }
            Text(
                text = answer.second,
                style = TypeText.h8.copy(fontWeight = FontWeight.Medium),
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_hight))
            )
        }
    }
}

@Composable
fun GroupItemAnswer(
    onClick: (String) -> Unit,
    options: Map<String, String>,
    answerCorrect: String,
    selectOption: String
) {
    LazyColumn {
        items(options.keys.toList()) {
            ItemAnswer(
                answer = Pair(it, options[it]!!),
                selected = selectOption == it,
                onClick = { onClick(it) }
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewTest() {
//    QuestionLayout(
//        maxNumberQuestion = 4,
//        questionNumber = 1,
//        questions = QuestionGPT(
//            question = "What was the fine imposed on the female Russian tourist for kicking the pregnant woman?",
//            options = mapOf(
//                "a" to "500 baht",
//                "b" to "1,000 baht",
//                "c" to "1,500 baht",
//                "d" to "2,000 baht"
//            ),
//            answer = "a",
//            explanation = "The female Russian tourist was fined 500 baht (US$13) for kicking the pregnant woman."
//        )
//    )
//}