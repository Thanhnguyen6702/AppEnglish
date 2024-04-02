package com.example.english4d.ui.theme.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.english4d.R
import com.example.english4d.navigation.Screen
import com.example.english4d.ui.theme.TypeText

@Composable
fun HomeScreen(
    navController: NavController
) {
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(text = stringResource(id = R.string.welcome), style = MaterialTheme.typography.titleLarge)
            Text(text = "1500 từ", style = TypeText.headLarge)
        }
        LayoutInfo(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        )
        ButtonNews(onClick = {
            navController.navigate(Screen.NewsTopic.route)
        })
    }
}

@Composable
fun LayoutInfo(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = MaterialTheme.shapes.small
            ),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Image(
                painter = painterResource(id = R.drawable.leaf),
                contentDescription = stringResource(
                    id = R.string.leaft
                ),
                modifier = Modifier.size(dimensionResource(id = R.dimen.size_icon_home))
            )
            Text(text = "0", style = MaterialTheme.typography.labelMedium)
            Text(text = stringResource(id = R.string.unfam_word), style = TypeText.bodyMedium)
        }
        Column(
            modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Image(
                painter = painterResource(id = R.drawable.bough),
                contentDescription = stringResource(
                    id = R.string.bough
                ),
                modifier = Modifier.size(dimensionResource(id = R.dimen.size_icon_home))
            )
            Text(text = "0", style = MaterialTheme.typography.labelMedium)
            Text(text = stringResource(id = R.string.almost_word), style = TypeText.bodyMedium)
        }
        Column(
            modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Image(
                painter = painterResource(id = R.drawable.tree),
                contentDescription = stringResource(
                    id = R.string.tree
                ),
                modifier = Modifier.size(dimensionResource(id = R.dimen.size_icon_home))
            )
            Text(text = "0", style = MaterialTheme.typography.labelMedium)
            Text(text = stringResource(id = R.string.fam_word), style = TypeText.bodyMedium)
        }
    }


}

@Composable
fun ButtonNews(
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(onClick = onClick,
            modifier = Modifier
                .background(
                    color = colorResource(id = R.color.green),
                    shape = MaterialTheme.shapes.small
                )
                ) {
            Icon(
                Icons.Default.Newspaper,
                contentDescription = stringResource(id = R.string.news),
                tint = Color.White,
                modifier = Modifier.size(dimensionResource(id = R.dimen.size_icon_home))
            )
        }
        Text(text = stringResource(id = R.string.news), style = TypeText.bodyMedium)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTest() {
    ButtonNews(onClick = {})
}