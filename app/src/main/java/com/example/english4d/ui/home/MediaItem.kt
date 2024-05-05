package com.example.english4d.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.english4d.R
import com.example.english4d.ui.theme.TypeText

@Composable
fun MediaItemLayout() {
    Row(
        modifier = Modifier.height(100.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(start = dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Xin chào tất cả các bạn Xin chào tất cả các bạn Xin chào tất cả các bạn",
                style = TypeText.h5.copy(fontWeight = FontWeight.Medium),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_medium))
            ) {
                Icon(
                    imageVector = Icons.Outlined.PlayCircle,
                    contentDescription = null,
                    modifier = Modifier.padding(
                        end = dimensionResource(
                            id = R.dimen.padding_medium
                        )
                    )
                )
                Text(text = "Video")
            }
        }
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data("").crossfade(true).build(),
            placeholder = painterResource(id = R.drawable.boy),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_medium))
                .clip(MaterialTheme.shapes.medium)
                .size(80.dp)

        )
    }
}

@Preview(showBackground = true)
@Composable
fun Previewadnf() {
    MediaItemLayout()
}