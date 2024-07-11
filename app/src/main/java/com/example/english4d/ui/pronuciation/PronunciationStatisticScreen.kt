package com.example.english4d.ui.pronuciation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.english4d.R
import com.example.english4d.navigation.PronunciationGraphScreen
import com.example.english4d.ui.AppViewModelProvider
import com.example.english4d.ui.theme.TypeText

enum class StatisticPronunciation {
    LOWER,
    MEDIUM,
    HIGH
}

@Preview
@Composable
fun PronunciationStatisticScreen12(

) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.gray_10))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        bottom = 25.dp, top = 30.dp, start = 30.dp, end = 30.dp
                    ),
                color = colorResource(id = R.color.white),
                shape = MaterialTheme.shapes.large
            ) {

            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_pronun),
                    contentDescription = null,
                    modifier = Modifier.size(150.dp)
                )
                Text(
                    text = "Bạn đã phát âm được",
                    style = TypeText.h4.copy(fontWeight = FontWeight.Medium),
                    modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_hight))
                )
                Text(
                    text = "10 từ",
                    style = TypeText.h2.copy(fontWeight = FontWeight.Bold),
                    color = colorResource(
                        id = R.color.green_100
                    ),
                    modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_hight))
                )
                ElevatedButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.size(width = 200.dp, height = 50.dp),
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = colorResource(id = R.color.green_100)
                    ),
                    elevation = ButtonDefaults.elevatedButtonElevation(
                        defaultElevation = 10.dp
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Tiếp tục học từ", style = TypeText.h7.copy(
                                fontWeight = FontWeight.Medium, color = colorResource(
                                    id = R.color.white
                                )
                            ),
                            modifier = Modifier.padding(end = dimensionResource(id = R.dimen.padding_small))

                        )
                        Icon(
                            imageVector = Icons.Filled.Mic,
                            contentDescription = null,
                            tint = colorResource(
                                id = R.color.white
                            )
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ItemStatisticPronunciation(type = StatisticPronunciation.LOWER, 0) {

            }
            ItemStatisticPronunciation(type = StatisticPronunciation.MEDIUM, 0) {

            }
            ItemStatisticPronunciation(type = StatisticPronunciation.HIGH, 0) {

            }

        }
        Spacer(modifier = Modifier.weight(1f))
    }
}


@Composable
fun ItemStatisticPronunciation(
    type: StatisticPronunciation,
    number: Int,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(
                color = when (type) {
                    StatisticPronunciation.LOWER -> colorResource(id = R.color.red_10)
                    StatisticPronunciation.MEDIUM -> colorResource(id = R.color.yellow_10)
                    StatisticPronunciation.HIGH -> colorResource(id = R.color.green_10)
                },
                shape = MaterialTheme.shapes.large
            )
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .padding(
                    top = dimensionResource(id = R.dimen.padding_hight),
                    bottom = dimensionResource(id = R.dimen.padding_medium),
                    start = dimensionResource(id = R.dimen.padding_hight),
                    end = dimensionResource(id = R.dimen.padding_hight)
                )
                .size(80.dp)
                .background(
                    color = when (type) {
                        StatisticPronunciation.LOWER -> colorResource(id = R.color.red)
                        StatisticPronunciation.MEDIUM -> colorResource(id = R.color.yellow)
                        StatisticPronunciation.HIGH -> colorResource(id = R.color.green_100)
                    },
                    shape = MaterialTheme.shapes.medium
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = number.toString(), style = TypeText.h6.copy(
                    fontWeight = FontWeight.Medium, color = colorResource(
                        id = R.color.white
                    )
                )
            )
        }
        Text(
            text = when (type) {
                StatisticPronunciation.LOWER -> "Nên cải thiện"
                StatisticPronunciation.MEDIUM -> "Khá ổn"
                StatisticPronunciation.HIGH -> "Xuất sắc"
            }, style = TypeText.h7.copy(fontWeight = FontWeight.Medium)
        )
        Text(
            text = when (type) {
                StatisticPronunciation.LOWER -> "1% - 49%"
                StatisticPronunciation.MEDIUM -> "50% - 74%"
                StatisticPronunciation.HIGH -> "75% - 100%"
            }, style = TypeText.h8, modifier = Modifier.padding(
                vertical = dimensionResource(
                    id = R.dimen.padding_small
                )
            )
        )
    }
}

@Composable
fun PronunciationStatisticScreen(
    innerPadding: PaddingValues,
    viewModel: PronunciationAssessmentViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navController: NavController
) {
    val uiState by viewModel.uiStateStatistic.collectAsState()
    Column(
        modifier = Modifier
            .padding(bottom = innerPadding.calculateBottomPadding())
            .fillMaxSize()
            .background(color = colorResource(id = R.color.gray_10))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        bottom = 25.dp, top = 30.dp, start = 30.dp, end = 30.dp
                    ),
                color = colorResource(id = R.color.white),
                shape = MaterialTheme.shapes.large
            ) {

            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_pronun),
                    contentDescription = null,
                    modifier = Modifier.size(150.dp)
                )
                Text(
                    text = "Bạn đã phát âm được",
                    style = TypeText.h4.copy(fontWeight = FontWeight.Medium),
                    modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_hight))
                )
                Text(
                    text = (uiState.high.size + uiState.medium.size + uiState.lower.size).toString(),
                    style = TypeText.h2.copy(fontWeight = FontWeight.Bold),
                    color = colorResource(
                        id = R.color.green_100
                    ),
                    modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_hight))
                )
                ElevatedButton(
                    onClick = {
                        viewModel.getVocabWithoutPronun(0)
                        navController.navigate(PronunciationGraphScreen.Pronunciation.route)
                    },
                    modifier = Modifier.size(width = 200.dp, height = 50.dp),
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = colorResource(id = R.color.green_100)
                    ),
                    elevation = ButtonDefaults.elevatedButtonElevation(
                        defaultElevation = 10.dp
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Tiếp tục học từ", style = TypeText.h7.copy(
                                fontWeight = FontWeight.Medium, color = colorResource(
                                    id = R.color.white
                                )
                            ),
                            modifier = Modifier.padding(end = dimensionResource(id = R.dimen.padding_small))

                        )
                        Icon(
                            imageVector = Icons.Filled.Mic,
                            contentDescription = null,
                            tint = colorResource(
                                id = R.color.white
                            )
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ItemStatisticPronunciation(type = StatisticPronunciation.LOWER, uiState.lower.size) {
                viewModel.setOptionSelect(StatisticPronunciation.LOWER)
                navController.navigate(PronunciationGraphScreen.DetailStatisticPronunciation.route)
            }
            ItemStatisticPronunciation(type = StatisticPronunciation.MEDIUM, uiState.medium.size) {
                viewModel.setOptionSelect(StatisticPronunciation.MEDIUM)
                navController.navigate(PronunciationGraphScreen.DetailStatisticPronunciation.route)
            }
            ItemStatisticPronunciation(type = StatisticPronunciation.HIGH, uiState.high.size) {
                viewModel.setOptionSelect(StatisticPronunciation.HIGH)
                navController.navigate(PronunciationGraphScreen.DetailStatisticPronunciation.route)
            }

        }
        Spacer(modifier = Modifier.weight(1f))
    }
}
