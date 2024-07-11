package com.example.english4d.ui.pronuciation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.english4d.R
import com.example.english4d.data.database.vocabulary.PronunciationWithVocabulary
import com.example.english4d.navigation.PronunciationGraphScreen
import com.example.english4d.ui.AppViewModelProvider
import com.example.english4d.ui.theme.TypeText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailStatisticPronunciationScreen(
    viewModel: PronunciationAssessmentViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navController: NavController
    ) {
    val uiState by viewModel.uiStateStatistic.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = null
                        )
                    }
                })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.icon_login),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
            Text(
                text = when(uiState.optionSelect){
                    StatisticPronunciation.LOWER -> "Từ nên cải thiện"
                    StatisticPronunciation.MEDIUM -> "Từ khá ổn"
                    StatisticPronunciation.HIGH -> "Từ xuất sắc"
                } ,
                style = TypeText.h5.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(
                    top = dimensionResource(
                        id = R.dimen.padding_hight
                    ),
                    bottom = dimensionResource(id = R.dimen.padding_medium)
                )
            )
            Text(
                text = when(uiState.optionSelect){
                    StatisticPronunciation.LOWER -> "Cố gắng lên nào,\nkhông gì là không thể bạn ơi!"
                    StatisticPronunciation.MEDIUM -> "Mỗi ngày chỉ cần tiến bộ một chút,\nchẳng mấy chốc sẽ tới đích ngay"
                    StatisticPronunciation.HIGH -> "U là trời! Cứ đà này,\nbạn sẽ sớm nói tiếng Anh như gió thôi!"
                },
                textAlign = TextAlign.Center,
                style = TypeText.h7.copy(fontWeight = FontWeight.Medium),
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_medium))
            )
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                val listItem = when(uiState.optionSelect){
                    StatisticPronunciation.LOWER -> uiState.lower
                    StatisticPronunciation.MEDIUM -> uiState.medium
                    StatisticPronunciation.HIGH -> uiState.high
                }
                items(listItem){ pronunciationWithVocabulary ->
                    ItemDetailStatisticPronunciationLayout(item = pronunciationWithVocabulary) {
                    viewModel.textToSpeech(it)
                }
                }
            }
            Box(
                modifier = Modifier.height(80.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = {
                        viewModel.getVocabWithoutPronun(
                            when(uiState.optionSelect){
                                StatisticPronunciation.LOWER -> 1
                                StatisticPronunciation.MEDIUM -> 2
                                StatisticPronunciation.HIGH -> 3
                            })
                        navController.navigate(PronunciationGraphScreen.Pronunciation.route)
                    }, colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(
                            id = when(uiState.optionSelect){
                                StatisticPronunciation.LOWER -> if (uiState.lower.isEmpty()) R.color.gray_100 else R.color.green_100
                                StatisticPronunciation.MEDIUM ->  if (uiState.medium.isEmpty()) R.color.gray_100 else R.color.green_100
                                StatisticPronunciation.HIGH -> if (uiState.high.isEmpty()) R.color.gray_100 else R.color.green_100
                            }
                        )
                    ),
                    enabled = when(uiState.optionSelect){
                        StatisticPronunciation.LOWER -> uiState.lower.isNotEmpty()
                        StatisticPronunciation.MEDIUM -> uiState.medium.isNotEmpty()
                        StatisticPronunciation.HIGH -> uiState.high.isNotEmpty()
                    }
                ) {
                    Text(
                        text = "Học lại", style = TypeText.h5.copy(
                            fontWeight = FontWeight.Medium, color = colorResource(
                                id = R.color.white
                            )
                        ),
                        modifier = Modifier.padding(
                            horizontal = dimensionResource(id = R.dimen.padding_hight),
                            vertical = dimensionResource(
                                id = R.dimen.padding_small
                            )
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun ItemDetailStatisticPronunciationLayout(
    item: PronunciationWithVocabulary,
    onClick: (text: String)->Unit
) {
    Row(
        modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_medium)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { onClick(item.vocabulary.english) }) {
            Icon(
                Icons.AutoMirrored.Filled.VolumeUp,
                contentDescription = null,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium),
                ),
                tint = colorResource(id = R.color.green_100)
            )
        }
        Text(text = item.vocabulary.english, style = TypeText.h7.copy(fontWeight = FontWeight.Medium))
        Spacer(modifier = Modifier.weight(1f))
        Text(
            modifier = Modifier.padding(end = dimensionResource(id = R.dimen.padding_hight)),
            text = stringResource(id = R.string.percentage, item.pronunciation.score),
            style = TypeText.h7.copy(fontWeight = FontWeight.Bold)
        )
    }
}