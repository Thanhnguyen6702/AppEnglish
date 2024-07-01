package com.example.englishe4.presentation.TopicScreen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.english4d.R


@Composable
fun ItemTopicCard( title: String,onClick :()-> Unit) {
    Box (
        modifier = Modifier
            .height(230.dp)
            .clickable {
                onClick()
            }
    ) {
        Card(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .height(200.dp)
                .padding(20.dp)
                .align(Alignment.BottomCenter),
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
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(
                                    modifier = Modifier
                                        .size(15.dp),
                                    painter = painterResource(id = R.drawable.icon_check),
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
                        text = title,
                    )


                }

            }




        }
        Image(
            modifier = Modifier
                .padding(end= 50.dp)
                .width(150.dp)
                .height(150.dp)
                .align(Alignment.TopEnd)
            ,
            alignment = Alignment.TopEnd,
            painter = painterResource(id = R.drawable.img_bg_topic_2),
            contentDescription = "IMG",
        )

    }


}


@Preview(showBackground = true)
@Composable
fun ItemTopicCardPreview() {
    ItemTopicCard(title = "Chủ đề 1") {
    }
}