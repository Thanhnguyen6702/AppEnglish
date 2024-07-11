package com.example.englishe4.presentation.TopicScreen.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.english4d.R
import com.example.english4d.data.database.wordstore.MyWordTopic


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemTopicCard(modifier: Modifier, topic: MyWordTopic,img: Int,colors: Pair<Int,Int>,updateStudy: () -> Unit,onClick :()-> Unit, onLongClick: () -> Unit) {
    Box (
        modifier = modifier
            .height(230.dp)
            .combinedClickable (
                onClick = onClick,
                onLongClick = onLongClick
            )
    ) {
        val study by remember {
            mutableStateOf(false)
        }
        Card(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .height(200.dp)
                .padding(20.dp)
                .align(Alignment.BottomCenter)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(colors.first),
                            Color(colors.second)
                        ), start = Offset.Zero, end = Offset( Float.POSITIVE_INFINITY,0f)
                    ), shape = RoundedCornerShape(20.dp)),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            ),
            shape = RoundedCornerShape(20.dp),
        ) {
            Row {
                Column(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(start = 40.dp,10.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier
                            .wrapContentSize(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(top = 30.dp)
                                .width(30.dp)
                                .height(30.dp)
                                .border(
                                    width = 1.dp,
                                    color = colorResource(id = R.color.white),
                                    shape = RoundedCornerShape(10.dp),
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            IconButton(onClick = updateStudy ) {
                                Icon(
                                    modifier = Modifier
                                        .size(15.dp),
                                    painter = painterResource(id = if(topic.is_study == 0) R.drawable.icon_box else R.drawable.icon_check),
                                    contentDescription = "CHECK",
                                    tint = colorResource(id = R.color.white)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Chủ đề"
                    )
                    Text(
                        text = topic.name,
                    )
                }
            }
        }
        Image(
            modifier = Modifier
                .padding(end= 40.dp)
                .width(120.dp)
                .height(120.dp)
                .align(Alignment.TopEnd)
            ,
            alignment = Alignment.TopEnd,
            painter = painterResource(id = img),
            contentDescription = "IMG",
        )

    }


}


