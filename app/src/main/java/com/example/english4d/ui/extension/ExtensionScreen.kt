package com.example.english4d.ui.extension

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.outlined.LibraryAdd
import androidx.compose.material.icons.outlined.Newspaper
import androidx.compose.material.icons.outlined.VideoLibrary
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.english4d.R
import com.example.english4d.navigation.Screen
import com.example.english4d.ui.theme.TypeText

@Composable
fun ExtensionScreen(
    navController: NavController
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.gray_10))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            colorResource(id = R.color.green_100),
                            colorResource(id = R.color.green_50)
                        ), start = Offset.Zero, end = Offset(0f, Float.POSITIVE_INFINITY)
                    )
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(start = dimensionResource(id = R.dimen.padding_hight))
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Vừa học vừa chill", style = TypeText.h4.copy(
                        fontWeight = FontWeight.Bold, color = colorResource(
                            id = R.color.white
                        )
                    )
                )
                Text(
                    modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_small)),
                    text = "Càng học càng fun", style = TypeText.h4.copy(
                        fontWeight = FontWeight.Bold, color = colorResource(
                            id = R.color.white
                        )
                    )
                )
                Text(
                    text = "Ứng dụng ngay kiến thức vừa học\nvào nghe - đọc - nói",
                    style = TypeText.h8.copy(
                        color = colorResource(
                            id = R.color.white
                        )
                    )
                )
            }
            Image(
                painter = painterResource(id = R.drawable.stationery),
                contentDescription = null,
                Modifier
                    .size(120.dp)
                    .padding(dimensionResource(id = R.dimen.padding_hight))
            )
        }
        Column(
            modifier = Modifier.padding(top = 214.dp),
            verticalArrangement = Arrangement.Center
        ) {
            ItemExtension(
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_hight)),
                icon = Icons.Outlined.VideoLibrary,
                title = "Thư viện video",
                description = "Luyên nghe với các kênh youtube"
            ){
                navController.navigate(Screen.Channel.route)
            }
            ItemExtension(
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_hight)),
                icon = Icons.AutoMirrored.Outlined.MenuBook,
                title = "Kho truyện ngắn",
                description = "Tăng khả năng đọc qua các mẩu truyện"
            ){
                navController.navigate(Screen.FairyTopic.route)
            }
            ItemExtension(
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_hight)),
                icon = Icons.Outlined.Newspaper,
                title = "Tin tức hàng ngày",
                description = "Tăng khả năng đọc qua các bài báo",
                label = "New"
            ){
                navController.navigate(Screen.NewsTopic.route)
            }
            ItemExtension(
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_hight)),
                icon = Icons.Outlined.LibraryAdd,
                title = "Kho từ của tôi",
                description = "Quản lý từ vựng mà bạn thêm vào"
            )
        }
    }
}

@Composable
fun ItemExtension(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    description: String,
    label: String = "",
    onClick: () -> Unit = {}
) {
    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.white)
        ),
        modifier = modifier
            .padding(horizontal = dimensionResource(id = R.dimen.padding_hight))
            .height(72.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .padding(
                        dimensionResource(id = R.dimen.padding_hight)
                    )
                    .background(
                        color = colorResource(id = R.color.green_10),
                        shape = MaterialTheme.shapes.small
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
                    imageVector = icon,
                    contentDescription = null,
                    tint = colorResource(
                        id = R.color.green_100
                    )
                )
            }
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_medium)),
                    text = title,
                    style = TypeText.h6.copy(fontWeight = FontWeight.Bold)
                )
                Text(text = description)
            }
            if (label != "") {
                Column(
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Box(
                        modifier = Modifier
                            .padding(
                                top = dimensionResource(id = R.dimen.padding_medium),
                                end = dimensionResource(
                                    id = R.dimen.padding_medium
                                )
                            )
                            .background(
                                color = colorResource(id = R.color.red_20),
                                shape = MaterialTheme.shapes.small
                            )

                    ) {
                        Text(
                            text = label,
                            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEx() {
    ExtensionScreen(navController = NavController(LocalContext.current))
}