package com.example.english4d.ui.wordstore.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.english4d.R
import com.example.english4d.data.database.wordstore.Example
import com.example.english4d.ui.wordstore.WordStoreViewModel
import com.example.englishe4.presentation.cardFlip.CardWordItemFlip
import com.example.englishe4.presentation.component.TopAppBar


@Composable
fun DetailItemWordCardScreen(
    navController: NavHostController,
    viewModel: WordStoreViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar( title = "Chi tiết thẻ", onClickRight = {}){
                    navController.popBackStack()
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(
                    20.dp
                )
                .background(
                    color = colorResource(id = R.color.white),
                    shape = RoundedCornerShape(10.dp),
                ),
        ) {
            uiState.listItemDetail.let { it ->
                if (it.isNotEmpty()) {
                    CardWordItemFlip(word = it[uiState.position])
                }
            }
            Icon(
                modifier = Modifier
                    .size(20.dp),
                painter = painterResource(id = R.drawable.arrows_turn_right_solid),
                contentDescription = "Arrow",
                tint = Color.Red
            )
            Column(
                modifier = Modifier
                    .padding(start = 20.dp)
                    .wrapContentSize()
                ,
            ) {
                uiState.listItemDetail[uiState.position].definitions?.forEach { item ->
                    Row(
                        modifier = Modifier
                            .padding(top = 20.dp),
                    ) {
                        Image(
                            modifier = Modifier
                                .padding(end = 10.dp)
                                .size(12.dp),
                            painter = painterResource(id = R.drawable.icon_usa),
                            contentDescription = null
                        )
                        Text(
                            text = item.definition_en,

                            )

                    }
                    Row(
                        modifier = Modifier
                            .padding(top = 5.dp),
                    ) {
                        Image(
                            modifier = Modifier
                                .padding(end = 10.dp)
                                .size(12.dp),
                            painter = painterResource(id = R.drawable.icon_vietnam),
                            contentDescription = null
                        )

                        Text(
                            text = item.definition_vi,

                            )
                    }
                }

            }

            Text(
                modifier = Modifier
                    .padding(top = 10.dp),
                text = "Ví dụ :",

            )

            uiState.listItemDetail[uiState.position].examples?.get(0)
                ?.let { example -> ExampleItem(data = Example(example.example_en, example.example_vi)) }
        }


    }
}


@Composable
fun ExampleItem(data: Example) {
    Icon(
        modifier = Modifier
            .size(25.dp),
        painter = painterResource(id = R.drawable.icon_dot),
        contentDescription = "Arrow",
        tint = Color.Blue
    )
    Column(
        modifier = Modifier
            .padding(start = 20.dp)
    ) {
        Row(
            modifier = Modifier
        ) {
            Image(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(12.dp),
                painter = painterResource(id = R.drawable.icon_usa),
                contentDescription = null
            )
            data.exampleEN?.let {
                Text(
                    text = it,
                )
            }
        }
        Row(
            modifier = Modifier
                .padding(top = 5.dp),
        ) {
            Image(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(12.dp),
                painter = painterResource(id = R.drawable.icon_vietnam),
                contentDescription = null
            )
            data.exampleVI?.let {
                Text(
                    text = it,
                )
            }
        }

    }
}
