package com.example.english4d.ui.newspaper

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.english4d.R
import com.example.english4d.ui.theme.TypeText

@Preview
@Composable
fun StatisticNewsScreen() {
    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
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
                                    text = "Kết quả: 6/10",
                                    style = TypeText.h6.copy(fontWeight = FontWeight.Bold)
                                )
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .size(42.dp)
                                        .clip(shape = RoundedCornerShape(50))
                                        .background(color = colorResource(id = R.color.green_100))

                                ) {
                                    Text(
                                        text = "60%", style = TypeText.h8.copy(
                                            fontWeight = FontWeight.Bold, color = colorResource(
                                                id = R.color.white
                                            )
                                        )
                                    )
                                }
                            }
                            Row(
                                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = "Tỷ lệ trung bình của bạn:",
                                    style = TypeText.h6.copy(fontWeight = FontWeight.Bold)
                                )
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .size(42.dp)
                                        .clip(shape = RoundedCornerShape(50))
                                        .background(color = colorResource(id = R.color.green_100))

                                ) {
                                    Text(
                                        text = "60%", style = TypeText.h8.copy(
                                            fontWeight = FontWeight.Bold, color = colorResource(
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
                                items(5) {
                                    ItemQuestionFailedLayout(position = 1, answer = "A") {

                                    }
                                }
                            }
                        }
                    }
                }
            }
            Box {
                Image(
                    painter = painterResource(id = R.drawable.wave_background),
                    contentDescription = null
                )
                Column(
                    modifier = Modifier
                        .height(200.dp)
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
                        onClick = { /*TODO*/ }) {
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
                        style = TypeText.h7.copy(textDecoration = TextDecoration.Underline)
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

