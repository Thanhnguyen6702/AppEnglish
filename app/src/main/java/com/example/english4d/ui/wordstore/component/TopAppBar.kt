package com.example.englishe4.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.english4d.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    type: String = "",
    title: String,
    onClickRight: () -> Unit,
    onClickLeft: () -> Unit,
) {
    androidx.compose.material3.TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        title = {
            Row(
                modifier = Modifier
                    .height(60.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onClickLeft
                ) {
                    Icon(
                        modifier = Modifier
                            .size(20.dp),
                        painter = painterResource(id = R.drawable.icon_backpress),
                        contentDescription = "BACKPRESS"
                    )
                }
                Text(
                    modifier = Modifier,
                    text = title,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    )

                )
                if (type == "modifier") {
                    IconButton(onClick = onClickRight) {
                        Icon(
                            imageVector = Icons.Filled.EditNote,
                            contentDescription = "edit"
                        )
                    }
                } else if (type == "add") {
                    IconButton(onClick = onClickRight) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_add_list),
                            contentDescription = "add"
                        )
                    }
                } else {
                    Text(text = " ")
                }

            }

        }
    )

}
