package com.example.englishe4.presentation.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.english4d.R
import com.example.english4d.ui.animation.LoadingScreen
import com.example.english4d.ui.theme.TypeText


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemWordCardScreen(modifier: Modifier = Modifier,data: String,colors: Pair<Int,Int>,onLongClick: () -> Unit, onClickNav: () -> Unit) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = dimensionResource(id = R.dimen.padding_hight))
            .height(60.dp)
            .combinedClickable(
                onClick = onClickNav,
                onLongClick = onLongClick
            )
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(colors.first),
                        Color(colors.second)
                    ), start = Offset.Zero, end = Offset(Float.POSITIVE_INFINITY, 0f)
                ), shape = RoundedCornerShape(10.dp)
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(10.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(30.dp))
            Image(
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
            if(data.isBlank()) {
                LoadingScreen(
                    circleSize = dimensionResource(id = R.dimen.size_loading_medium),
                    travelDistance = dimensionResource(id = R.dimen.travel_distance)
                )
            }else{
                Text(text = data, style = TypeText.h6.copy(fontWeight = FontWeight.Medium))
            }
        }
    }
}
