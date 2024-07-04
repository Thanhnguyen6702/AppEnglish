package com.example.englishe4.presentation.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.english4d.R


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemWordCardScreen(modifier: Modifier,data: String,onLongClick: () -> Unit, onClickNav: () -> Unit) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .height(60.dp)
            .combinedClickable (
                onClick = onClickNav,
                onLongClick = onLongClick
            )
            .background(color = colorResource(id = R.color.white)),
        shape = RoundedCornerShape(10.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(30.dp))
            Icon(
                modifier = Modifier
                    .size(20.dp)
                    .fillMaxHeight(),
                painter = painterResource(id = R.drawable.icon_book),
                contentDescription = "Item"
            )
            Spacer(
                modifier = Modifier
                    .width(20.dp)
            )
            Text(
                text = if (data.isBlank()) "" else data,
            )
        }


    }
}
