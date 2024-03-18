package com.example.english4d.ui.theme.newspaper

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.english4d.R
import com.example.english4d.data.NewsItem

@Composable
fun HorizontalScrollRow(
    title: String,
    listItem: List<NewsItem>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_small))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_small))
            )
            Text(
                text = stringResource(id = R.string.more),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_small))
            )
        }
        LazyRow(
            //  modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
        ) {
            items(listItem) {
                ItemRow(
                    it,
                    modifier = Modifier.padding(end = dimensionResource(id = R.dimen.padding_small))
                )
            }
        }
    }
}

@Composable
fun ItemRow(
    item: NewsItem,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .size(
                width = dimensionResource(id = R.dimen.item_news_width),
                height = dimensionResource(id = R.dimen.item_news_height)
            ) // Thiết lập kích thước cho Card
    ) {
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(id = R.dimen.padding_small))
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(item.image).build(),
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.loading_img),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(
                        dimensionResource(
                            id = R.dimen.image_news_height
                        )
                    )
                    .clip(MaterialTheme.shapes.small)
            )
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
        Text(
            text = item.title,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

//@Preview(
//    showBackground = true,
//
//    )
//@Composable
//fun PreviewItem() {
//    HorizontalScrollRow()
//}