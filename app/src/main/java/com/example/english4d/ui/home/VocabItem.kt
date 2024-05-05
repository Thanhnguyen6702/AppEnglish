package com.example.english4d.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.english4d.R
import com.example.english4d.ui.theme.TypeText

@Composable
fun VocabItemLayout() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.padding_hight))
    ) {
        Text(
            text = "Mỗi Ngày 3 từ vựng", style = TypeText.h6.copy(
                fontWeight = FontWeight.Medium, color = colorResource(
                    id = R.color.black
                )
            ),
            modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_medium))
        )
        Row(
            modifier = Modifier.fillMaxWidth()

        ) {
            Spacer(
                modifier = Modifier
                    .padding(end = dimensionResource(id = R.dimen.padding_medium))
                    .weight(1f)
                    .height(4.dp)
                    .background(color = colorResource(id = R.color.green_100), shape = MaterialTheme.shapes.large)
            )
            Spacer(
                modifier = Modifier
                    .padding(end = dimensionResource(id = R.dimen.padding_medium))
                    .weight(1f)
                    .height(4.dp)
                    .background(color = colorResource(id = R.color.green_100), shape = MaterialTheme.shapes.large)
            )
            Spacer(
                modifier = Modifier
                    .weight(1f)
                    .height(4.dp)
                    .background(color = colorResource(id = R.color.green_100), shape = MaterialTheme.shapes.large)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = dimensionResource(id = R.dimen.padding_hight)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Nghĩa nào nhỉ? Chọn nghĩa đúng",
                style = TypeText.h5,
                fontWeight = FontWeight.Medium
            )
            Text(
                modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_hight)),
                text = "Critical",
                style = TypeText.h4.copy(fontWeight = FontWeight.Medium, color = colorResource(id = R.color.purple_700))

            )
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.VolumeUp, contentDescription = null,
                    tint = colorResource(id = R.color.green_100))
            }
        }
        OutlinedButton(
            onClick = { /*TODO*/ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Hoài nghi", style = TypeText.h6)
        }
        OutlinedButton(
            onClick = { /*TODO*/ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Hoài nghi", style = TypeText.h6)
        }
    }
}
