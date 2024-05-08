package com.example.english4d.ui.vocabulary

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.english4d.R
import com.example.english4d.navigation.HomeGraphScreen
import com.example.english4d.ui.AppViewModelProvider
import com.example.english4d.ui.customdialog.DialogRevise
import com.example.english4d.ui.theme.TypeText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviseScreen(
    navController: NavController,
    viewModel: ReviseViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    val onClick: (pos: Int) -> Unit = { pos ->
        viewModel.setStatusWord(pos)
        viewModel.nextWord(pos)
    }
    var isShowDialog by remember {
        mutableStateOf(false)
    }
    if (uiState.isFinish) {
        navController.navigate(HomeGraphScreen.FinishVocab.route) {
            popUpTo(HomeGraphScreen.ReviseVocab.route) { inclusive = true }
        }
        viewModel.updateItemFinish()
    }
    if (isShowDialog) {
        DialogRevise(onDismissRequest = { isShowDialog = false }) {
            isShowDialog = false
            navController.navigate(HomeGraphScreen.FinishVocab.route) {
               // popUpTo(HomeGraphScreen.ReviseVocab.route) { inclusive = true }
            }
            viewModel.updateItemFinish()
        }
    }
    BackHandler(enabled = true) {}
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = dimensionResource(id = R.dimen.padding_medium)),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    IconButton(onClick = { isShowDialog = true }) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Color.Black
                        )
                    }
                    Text(
                        text = (uiState.currentNumber.toString() + "/" + uiState.totalNumber),
                        style = TypeText.h7.copy(color = colorResource(id = R.color.green_100))
                    )
                }
            })
        }
    ) {
        Column(
            modifier = Modifier
                .padding(
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding()
                )
                .fillMaxHeight()
        ) {

            Text(
                text = if (uiState.isQuestionText) stringResource(id = R.string.question_text)
                else stringResource(id = R.string.question_sound),
                style = TypeText.h5,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                textAlign = TextAlign.Center
            )
            Box(
                modifier = Modifier
                    .weight(5f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                if (uiState.isQuestionText) {
                    Text(
                        text = uiState.vocabulary.english, style = TypeText.bodyLarge.copy(
                            color = colorResource(
                                id = R.color.green
                            )
                        ), textAlign = TextAlign.Center
                    )
                } else {
                    IconButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.size(128.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.volume),
                            contentDescription = null,
                            modifier = Modifier.size(128.dp)
                        )
                    }
                }
            }
            Column(
                modifier = Modifier.weight(6f),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                if (uiState.isQuestionText) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        ButtonChoose(
                            text = uiState.listVocabMixed[0].vietnamese,
                            onClick = { onClick(0) },
                            statusWord = uiState.listStatus[0],
                            isEnable = uiState.enableChoose
                        )
                        ButtonChoose(
                            text = uiState.listVocabMixed[1].vietnamese,
                            onClick = { onClick(1) },
                            statusWord = uiState.listStatus[1],
                            isEnable = uiState.enableChoose
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        ButtonChoose(
                            text = uiState.listVocabMixed[2].vietnamese,
                            onClick = { onClick(2) },
                            statusWord = uiState.listStatus[2],
                            isEnable = uiState.enableChoose
                        )
                        ButtonChoose(
                            text = uiState.listVocabMixed[3].vietnamese,
                            onClick = { onClick(3) },
                            statusWord = uiState.listStatus[3],
                            isEnable = uiState.enableChoose
                        )
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        ButtonChoose(
                            text = uiState.listVocabMixed[0].english,
                            onClick = { onClick(0) },
                            statusWord = uiState.listStatus[0],
                            isEnable = uiState.enableChoose
                        )
                        ButtonChoose(
                            text = uiState.listVocabMixed[1].english,
                            onClick = { onClick(1) },
                            statusWord = uiState.listStatus[1],
                            isEnable = uiState.enableChoose
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        ButtonChoose(
                            text = uiState.listVocabMixed[2].english,
                            onClick = { onClick(2) },
                            statusWord = uiState.listStatus[2],
                            isEnable = uiState.enableChoose
                        )
                        ButtonChoose(
                            text = uiState.listVocabMixed[3].english,
                            onClick = { onClick(3) },
                            statusWord = uiState.listStatus[3],
                            isEnable = uiState.enableChoose
                        )
                    }
                }
            }

        }
    }
}


@Composable
fun ButtonChoose(
    text: String,
    onClick: () -> Unit,
    statusWord: StatusWord,
    isEnable: Boolean

) {
    val colorBackground = when (statusWord) {
        StatusWord.UNCHOOSE -> CardDefaults.cardColors(containerColor = colorResource(id = R.color.white))
        StatusWord.CORRECT -> CardDefaults.cardColors(containerColor = colorResource(id = R.color.green_10))
        StatusWord.UNCORRECT -> CardDefaults.cardColors(containerColor = colorResource(id = R.color.red_10))
        StatusWord.DISABLE -> CardDefaults.cardColors(containerColor = colorResource(id = R.color.gray_10))
    }
    val styleText = when (statusWord) {
        StatusWord.UNCHOOSE -> TypeText.h6
        StatusWord.CORRECT -> TypeText.h6.copy(
            color = colorResource(id = R.color.green_100),
            fontWeight = FontWeight.Bold
        )

        StatusWord.UNCORRECT -> TypeText.h6.copy(
            color = colorResource(id = R.color.orange_red),
            fontWeight = FontWeight.Bold
        )

        StatusWord.DISABLE -> TypeText.h6.copy(
            color = colorResource(id = R.color.gray_20),
            fontWeight = FontWeight.Bold
        )
    }
    Card(
        modifier = Modifier
            .size(
                width = 150.dp,
                height = 120.dp
            )
            .clickable(
                onClick = onClick,
                enabled = (isEnable && statusWord != StatusWord.DISABLE)
            ),
        shape = MaterialTheme.shapes.medium,
        colors = colorBackground,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = text, style = styleText, textAlign = TextAlign.Center)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewChooseAnswer() {
    //   ButtonChoose("HIHI",{}, statusWord = StatusWord.UNCORRECT)
}
