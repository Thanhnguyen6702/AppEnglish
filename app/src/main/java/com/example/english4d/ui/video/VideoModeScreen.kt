package com.example.english4d.ui.video

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RuleFolder
import androidx.compose.material.icons.filled.Subtitles
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.english4d.R
import com.example.english4d.navigation.ExtensionGraphScreen
import com.example.english4d.ui.theme.TypeText

@Composable
fun VideoModeScreen(
    navController: NavController,
    urlImage: String,
    title: String,
    videoId: String
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                model = ImageRequest.Builder(LocalContext.current).data(urlImage).build(),
                contentDescription = null,
                placeholder = painterResource(
                    id = R.drawable.loading_img
                ),
                contentScale = ContentScale.Crop
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(
                            horizontal = dimensionResource(id = R.dimen.padding_medium),
                            vertical = dimensionResource(
                                id = R.dimen.padding_medium
                            )
                        ),
                    text = title,
                    style = TypeText.h6.copy(fontWeight = FontWeight.Bold),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Icon(
                    modifier = Modifier.size(32.dp),
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = null,
                    tint = colorResource(
                        id = R.color.gray_50
                    )
                )
            }
            HorizontalDivider()
        }
        Text(
            modifier = Modifier.padding(
                start = dimensionResource(id = R.dimen.padding_medium), top = dimensionResource(
                    id = R.dimen.padding_hight
                )
            ), text = "Chọn kiểu xem", style = TypeText.h6.copy(fontWeight = FontWeight.Medium)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {

            ElevatedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(id = R.dimen.padding_hight)),
                onClick = {
                    navController.navigate(ExtensionGraphScreen.Video.passId(videoId = videoId, mode = 0))
                }, colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = colorResource(
                        id = R.color.green_100
                    ),
                    contentColor = colorResource(id = R.color.white)
                ),
                shape = MaterialTheme.shapes.small
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        imageVector = Icons.Filled.Subtitles,
                        contentDescription = null,
                        modifier = Modifier.size(36.dp)
                    )
                    Column(
                        modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_hight))
                    ) {
                        Text(
                            text = "Phụ đề",
                            style = TypeText.h7.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "Xem video với phụ đề",
                            style = TypeText.h7.copy(fontWeight = FontWeight.Medium)
                        )
                    }
                }
            }
            ElevatedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = dimensionResource(id = R.dimen.padding_hight)),
                onClick = { navController.navigate(ExtensionGraphScreen.Video.passId(videoId = videoId, mode = 1)) },
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = colorResource(
                        id = R.color.green_100
                    ),
                    contentColor = colorResource(id = R.color.white)
                ),
                shape = MaterialTheme.shapes.small
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        imageVector = Icons.Filled.RuleFolder,
                        contentDescription = null,
                        modifier = Modifier.size(36.dp)
                    )
                    Column(
                        modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_hight))
                    ) {
                        Text(
                            text = "Chọn từ",
                            style = TypeText.h7.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "Chọn từ nghe được",
                            style = TypeText.h7.copy(fontWeight = FontWeight.Medium)
                        )
                    }
                }
            }
        }
    }
}