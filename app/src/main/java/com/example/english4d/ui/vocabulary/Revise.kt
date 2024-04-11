package com.example.english4d.ui.vocabulary

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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.english4d.R
import com.example.english4d.ui.theme.TypeText

@Composable
fun ChooseAnswerLayout() {
    Column(
        modifier = Modifier.fillMaxHeight()
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(id = R.dimen.padding_medium)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Icon(
                Icons.Default.Close,
                contentDescription = "Close",
                tint = Color.Black
            )
            Text(text = "1/20", style = TypeText.h7)
        }
        Text(
            text = "Chọn nghĩa đúng của từ",
            style = TypeText.h5,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            textAlign = TextAlign.Center
        )
        Box(
            modifier = Modifier
                .weight(8f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Developer", style = TypeText.bodyLarge.copy(
                    color = colorResource(
                        id = R.color.green
                    )
                ), textAlign = TextAlign.Center
            )
        }
        Column(
            modifier = Modifier.weight(5f),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ButtonChoose(text = "")
                ButtonChoose(text = "")
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ButtonChoose(text = "")
                ButtonChoose(text = "")
            }
        }

    }
}


@Composable
fun ButtonChoose(
    text: String
) {
    Card(
        modifier = Modifier.size(
            width = 150.dp,
            height = 120.dp
        ).clickable {  },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.white)),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp, pressedElevation = 0.dp, disabledElevation = 0.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Xà phòng", style = TypeText.h6)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewChooseAnswer() {
    ChooseAnswerLayout()
}