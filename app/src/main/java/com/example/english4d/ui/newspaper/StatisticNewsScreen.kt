package com.example.english4d.ui.newspaper

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.english4d.R
import com.example.english4d.navigation.ExtensionGraphScreen
import com.example.english4d.ui.AppViewModelProvider
import com.example.english4d.ui.customdialog.DialogQuestion
import com.example.english4d.ui.theme.TypeText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticNewsScreen(
    navController: NavController,
    href: String,
    viewModel: StatisticNewsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    var isShowDialog by remember {
        mutableStateOf(false)
    }
    var questionSelected by remember {
        mutableIntStateOf(0)
    }
    if (isShowDialog) {
        DialogQuestion(
            numberQuestion = questionSelected + 1,
            question = uiState.questionsFailed[questionSelected].second,
            onDismissRequest = { isShowDialog = false }) {

        }
    }
    LaunchedEffect(key1 = true) {
        viewModel.update(href)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(colorResource(id = R.color.green_100)),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = null,
                            tint = colorResource(
                                id = R.color.white
                            )
                        )
                    }
                },
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Kết quả trả lời",
                        textAlign = TextAlign.Center,
                        style = TypeText.h4.copy(
                            fontWeight = FontWeight.Medium, color = colorResource(
                                id = R.color.white
                            )
                        )
                    )
                })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(dimensionResource(id = R.dimen.padding_hight)),
                        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.white)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                modifier = Modifier
                                    .padding(dimensionResource(id = R.dimen.padding_hight))
                                    .size(75.dp),
                                painter = painterResource(id = R.drawable.icon_login),
                                contentDescription = null,
                            )
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Bạn đã hoàn thành bài báo",
                                    style = TypeText.h6.copy(fontWeight = FontWeight.Bold)
                                )
                                Text(text = "Bạn cần cố gắng hơn nữa")
                            }
                        }
                    }
                }
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(dimensionResource(id = R.dimen.padding_hight)),
                        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.white)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
                    ) {
                        Column {

                            Row(
                                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = "Thành tích của bạn:",
                                    style = TypeText.h6.copy(fontWeight = FontWeight.Bold)
                                )
                            }
                            Row(
                                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = "Kết quả: ${uiState.numberCorrect}/${uiState.totalQuestion}",
                                    style = TypeText.h6.copy(fontWeight = FontWeight.Bold)
                                )
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .size(42.dp)
                                        .clip(shape = RoundedCornerShape(50))
                                        .background(
                                            color = when (uiState.numberCorrect * 100 / uiState.totalQuestion) {
                                                in 0..45 -> colorResource(id = R.color.orange_red)
                                                in 46..75 -> colorResource(id = R.color.yellow)
                                                else -> colorResource(id = R.color.green_100)
                                            }
                                        )
                                ) {
                                    Text(
                                        text = "${uiState.numberCorrect * 100 / uiState.totalQuestion}%",
                                        style = TypeText.h8.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = colorResource(
                                                id = R.color.white
                                            )
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
                item {
                    Card(
                        modifier = Modifier
                            .height(400.dp)
                            .fillMaxWidth()
                            .padding(dimensionResource(id = R.dimen.padding_hight)),
                        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.white)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
                    ) {
                        Column {
                            Text(
                                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
                                text = "Danh sách câu hỏi sai:", style = TypeText.h6.copy(
                                    fontWeight = FontWeight.Bold, color = colorResource(
                                        id = R.color.orange_100
                                    )
                                )
                            )
                            LazyColumn {
                                items(uiState.questionsFailed.size) {
                                    ItemQuestionFailedLayout(
                                        position = uiState.questionsFailed[it].first,
                                        answer = uiState.questionsFailed[it].second.answer
                                    ) {
                                        questionSelected = it
                                        isShowDialog = true
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth()
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.wave_background),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ElevatedButton(
                        modifier = Modifier
                            .padding(vertical = dimensionResource(id = R.dimen.padding_hight))
                            .width(250.dp),
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = colorResource(
                                id = R.color.green_100
                            )
                        ),
                        onClick = { navController.popBackStack() }) {
                        Text(
                            modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_small)),
                            text = "Làm lại", style = TypeText.h5.copy(
                                fontWeight = FontWeight.Medium, color = colorResource(
                                    id = R.color.white
                                )
                            )
                        )

                    }
                    Text(
                        text = "Đọc các bài báo khác",
                        style = TypeText.h7.copy(textDecoration = TextDecoration.Underline),
                        modifier = Modifier.clickable {
                            navController.navigate(ExtensionGraphScreen.NewsTopic.route) {
                                popUpTo(ExtensionGraphScreen.NewsTopic.route)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ItemQuestionFailedLayout(
    position: Int,
    answer: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.padding_small))
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(text = "Câu hỏi $position", style = TypeText.h6.copy(fontWeight = FontWeight.Medium))
        Text(
            text = "Đáp án đúng: $answer",
            style = TypeText.h6.copy(fontWeight = FontWeight.Medium)
        )
        IconButton(onClick = onClick) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos, contentDescription = null)
        }
    }
}

@Preview
@Composable
fun PreviewNews() {
    StatisticNewsScreen(navController = NavController(LocalContext.current), href = "")
}