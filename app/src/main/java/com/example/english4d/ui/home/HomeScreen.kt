package com.example.english4d.ui.home

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.outlined.Chat
import androidx.compose.material.icons.filled.Cached
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.english4d.R
import com.example.english4d.navigation.Screen
import com.example.english4d.ui.AppViewModelProvider
import com.example.english4d.ui.theme.TypeText

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    id: Int? = null,
    navController: NavController,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    viewModel.updateStatistic(LocalContext.current)
    Scaffold(
        bottomBar = {
            MyBottomBar(selectedItemIndex = 0)
        }
    ) {
        Column(
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
        ) {
            val homeUiState by viewModel.uiState.collectAsState()
            if (id != null) viewModel.changeTopic(id)
            Text(
                text = stringResource(id = R.string.welcome),
                style = TypeText.h4.copy(fontWeight = FontWeight.Medium),
                modifier = Modifier.padding(
                    dimensionResource(id = R.dimen.padding_hight)
                )
            )
            LayoutInfo(
                homeUiState = homeUiState, modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(vertical = dimensionResource(id = R.dimen.padding_hight))
            )
            CardStudy(
                homeUiState = homeUiState,
                navController = navController,
                modifier = Modifier.padding(
                    vertical = dimensionResource(id = R.dimen.padding_hight)
                )
            )
            ButtonNews(onClick = {
                navController.navigate(Screen.NewsTopic.route)
            })
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun LayoutInfo(
    modifier: Modifier = Modifier, homeUiState: HomeUiState
) {
    Row(
        modifier = modifier.border(
            width = 2.dp, color = colorResource(id = R.color.gray_10), shape = MaterialTheme.shapes.medium
        ), horizontalArrangement = Arrangement.SpaceAround
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
            Text(
                text = homeUiState.unlearned.size.toString(),
                style = MaterialTheme.typography.labelMedium
            )
            Text(text = stringResource(id = R.string.unfam_word), style = TypeText.h7)
        }
        Divider(
            modifier = Modifier
                .padding(vertical = dimensionResource(id = R.dimen.padding_small))
                .fillMaxHeight()
                .width(1.dp), color = colorResource(id = R.color.gray_10)
        )
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
            Text(
                text = homeUiState.learning.size.toString(),
                style = MaterialTheme.typography.labelMedium
            )
            Text(text = stringResource(id = R.string.almost_word), style = TypeText.h7)
        }
        Divider(
            modifier = Modifier
                .padding(vertical = dimensionResource(id = R.dimen.padding_small))
                .fillMaxHeight()
                .width(1.dp), color = colorResource(id = R.color.gray_10)
        )
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
            Text(
                text = homeUiState.master.size.toString(),
                style = MaterialTheme.typography.labelMedium
            )
            Text(text = stringResource(id = R.string.fam_word), style = TypeText.h7)
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
        IconButton(
            onClick = onClick, modifier = Modifier.background(
                color = colorResource(id = R.color.green), shape = MaterialTheme.shapes.small
            )
        ) {
            Icon(
                Icons.Default.Newspaper,
                contentDescription = stringResource(id = R.string.news),
                tint = Color.White,
                modifier = Modifier.size(dimensionResource(id = R.dimen.size_icon_home))
            )
        }
        Text(text = stringResource(id = R.string.news), style = TypeText.h6)
    }
}

@Composable
fun CardStudy(
    homeUiState: HomeUiState,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    if (homeUiState.isRevise) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.height_card_home))
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            colorResource(id = R.color.orange_100),
                            colorResource(id = R.color.orange_50)
                        ), start = Offset.Zero, end = Offset(0f, Float.POSITIVE_INFINITY)
                    ), shape = MaterialTheme.shapes.medium
                )
        ) {
            Row(
                modifier = Modifier.fillMaxHeight(1f), verticalAlignment = Alignment.Bottom
            ) {
                Column(
                    Modifier
                        .padding(
                            start = dimensionResource(id = R.dimen.padding_medium),
                            top = dimensionResource(id = R.dimen.padding_medium)
                        )
                        .fillMaxHeight()
                        .width(200.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = stringResource(id = R.string.noti_revise1),
                            style = TypeText.h9.copy(color = Color.White)
                        )
                        Text(
                            text = stringResource(id = R.string.noti_revise2),
                            style = TypeText.bodyMedium.copy(color = Color.White),
                            maxLines = 2
                        )
                    }
                    Button(
                        onClick = {
                            navController.navigate(Screen.ReviseVocab.route)
                        },
                        modifier = Modifier.padding(
                            start = dimensionResource(id = R.dimen.padding_hight),
                            bottom = dimensionResource(id = R.dimen.padding_hight)
                        ),
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.orange_red)),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = dimensionResource(id = R.dimen.elevation)
                        )
                    ) {
                        Text(text = stringResource(id = R.string.revise))
                    }

                }
                Image(
                    painter = painterResource(id = R.drawable.magnifier),
                    contentDescription = "magnifier",
                    modifier = Modifier.size(
                        width = dimensionResource(id = R.dimen.size_icon_medium),
                        height = dimensionResource(id = R.dimen.size_icon_medium)
                    )
                )
            }
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.height_card_home))
                .padding(
                    all = dimensionResource(id = R.dimen.padding_medium)
                )
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            colorResource(id = R.color.green_100),
                            colorResource(id = R.color.green_50)
                        ), start = Offset.Zero, end = Offset(0f, Float.POSITIVE_INFINITY)
                    ), shape = MaterialTheme.shapes.small
                )
        ) {
            Row(
                modifier = Modifier.fillMaxHeight(1f), verticalAlignment = Alignment.Bottom
            ) {
                Column(
                    Modifier
                        .padding(
                            start = dimensionResource(id = R.dimen.padding_medium),
                            top = dimensionResource(id = R.dimen.padding_medium)
                        )
                        .fillMaxHeight()
                        .width(200.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Học từ mơi", style = TypeText.h9.copy(color = Color.White)
                        )
                        Text(
                            text = homeUiState.newVocab.first.topic,
                            style = TypeText.bodyMedium.copy(color = Color.White),
                            maxLines = 2
                        )
                    }
                    Button(
                        onClick = { navController.navigate(Screen.NewVocab.passID(homeUiState.newVocab.first.id)) },
                        modifier = Modifier.padding(
                            start = dimensionResource(id = R.dimen.padding_hight),
                            bottom = dimensionResource(id = R.dimen.padding_hight)
                        ),
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.orange_red)),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = dimensionResource(id = R.dimen.elevation)
                        )
                    ) {
                        Text(text = stringResource(id = R.string.learn_vocab))
                    }

                }
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Row(
                        modifier = Modifier
                            .padding(top = dimensionResource(id = R.dimen.padding_medium))
                            .clickable {
                                navController.navigate(route = Screen.TopicsVocab.route)
                            }, verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.change_topic),
                            style = TypeText.h8.copy(color = colorResource(id = R.color.white)),
                            modifier = Modifier.padding(end = dimensionResource(id = R.dimen.padding_small))
                        )
                        Icon(
                            Icons.Default.Cached,
                            contentDescription = "Change Topic",
                            tint = colorResource(
                                id = R.color.white
                            )
                        )
                    }
                    AsyncImage(
                        model = ImageRequest.Builder(
                            context = LocalContext.current
                        ).data(homeUiState.newVocab.first.image).build(),
                        contentDescription = "Image Topic",
                        modifier = Modifier.size(
                            width = dimensionResource(id = R.dimen.size_icon_medium),
                            height = dimensionResource(id = R.dimen.size_icon_medium)
                        )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHome() {
    HomeScreen1()
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen1(
) {
    Scaffold(bottomBar = {
        MyBottomBar(0)
    }) {
        Column(
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
        ) {
            Text(
                text = stringResource(id = R.string.welcome),
                style = TypeText.h4.copy(fontWeight = FontWeight.Medium)
            )
            LayoutInfo1(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)

            )
            CardStudy1()
            ButtonNews1(onClick = {})
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun LayoutInfo1(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.border(
            width = 2.dp,
            color = colorResource(id = R.color.gray_10),
            shape = MaterialTheme.shapes.medium
        ), horizontalArrangement = Arrangement.SpaceAround
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
            Text(
                text = "1", style = MaterialTheme.typography.labelMedium
            )
            Text(text = stringResource(id = R.string.unfam_word), style = TypeText.h7)
        }
        Divider(
            modifier = Modifier
                .padding(vertical = dimensionResource(id = R.dimen.padding_small))
                .fillMaxHeight()
                .width(1.dp), color = colorResource(id = R.color.gray_20)
        )
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
            Text(
                text = "2", style = MaterialTheme.typography.labelMedium
            )
            Text(text = stringResource(id = R.string.almost_word), style = TypeText.h7)
        }
        Divider(
            modifier = Modifier
                .padding(vertical = dimensionResource(id = R.dimen.padding_small))
                .fillMaxHeight()
                .width(1.dp), color = colorResource(id = R.color.gray_20)
        )
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
            Text(
                text = "3", style = MaterialTheme.typography.labelMedium
            )
            Text(text = stringResource(id = R.string.fam_word), style = TypeText.h7)
        }
    }


}

@Composable
fun ButtonNews1(
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = onClick, modifier = Modifier.background(
                color = colorResource(id = R.color.green), shape = MaterialTheme.shapes.small
            )
        ) {
            Icon(
                Icons.Default.Newspaper,
                contentDescription = stringResource(id = R.string.news),
                tint = Color.White,
                modifier = Modifier.size(dimensionResource(id = R.dimen.size_icon_home))
            )
        }
        Text(text = stringResource(id = R.string.news), style = TypeText.h6)
    }
}

@Composable
fun CardStudy1(
) {
    if (true) {
        Surface(
            tonalElevation = 3.dp ,
            shape = MaterialTheme.shapes.medium
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(id = R.dimen.height_card_home))
                    .padding(
                        vertical = dimensionResource(id = R.dimen.padding_medium)
                    )
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                colorResource(id = R.color.orange_100),
                                colorResource(id = R.color.orange_50)
                            ), start = Offset.Zero, end = Offset(0f, Float.POSITIVE_INFINITY)
                        ), shape = MaterialTheme.shapes.medium
                    )
                    .clip(shape = MaterialTheme.shapes.medium)
            ) {
                Row(
                    modifier = Modifier.fillMaxHeight(1f), verticalAlignment = Alignment.Bottom
                ) {
                    Column(
                        Modifier
                            .padding(
                                start = dimensionResource(id = R.dimen.padding_medium),
                                top = dimensionResource(id = R.dimen.padding_medium)
                            )
                            .fillMaxHeight()
                            .width(200.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = stringResource(id = R.string.noti_revise1),
                                style = TypeText.h9.copy(color = Color.White)
                            )
                            Text(
                                text = stringResource(id = R.string.noti_revise2),
                                style = TypeText.bodyLarge.copy(color = Color.White),
                                maxLines = 2
                            )
                        }
                        Button(
                            onClick = {},
                            modifier = Modifier.padding(
                                start = dimensionResource(id = R.dimen.padding_hight),
                                bottom = dimensionResource(id = R.dimen.padding_hight)
                            ),
                            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.orange_red)),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = dimensionResource(id = R.dimen.elevation)
                            )
                        ) {
                            Text(text = stringResource(id = R.string.revise))
                        }

                    }
                    Image(
                        painter = painterResource(id = R.drawable.magnifier),
                        contentDescription = "magnifier",
                        modifier = Modifier.size(
                            width = dimensionResource(id = R.dimen.size_icon_medium),
                            height = dimensionResource(id = R.dimen.size_icon_medium)
                        )
                    )
                }
            }
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.height_card_home))
                .padding(
                    all = dimensionResource(id = R.dimen.padding_medium)
                )
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            colorResource(id = R.color.green_100),
                            colorResource(id = R.color.green_50)
                        ), start = Offset.Zero, end = Offset(0f, Float.POSITIVE_INFINITY)
                    ), shape = MaterialTheme.shapes.small
                )
        ) {
            Row(
                modifier = Modifier.fillMaxHeight(1f), verticalAlignment = Alignment.Bottom
            ) {
                Column(
                    Modifier
                        .padding(
                            start = dimensionResource(id = R.dimen.padding_medium),
                            top = dimensionResource(id = R.dimen.padding_medium)
                        )
                        .fillMaxHeight()
                        .width(200.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Học từ mơi", style = TypeText.h9.copy(color = Color.White)
                        )
                        Text(
                            text = "Daily activities",
                            style = TypeText.bodyLarge.copy(color = Color.White),
                            maxLines = 2
                        )
                    }
                    Button(
                        onClick = { },
                        modifier = Modifier.padding(
                            start = dimensionResource(id = R.dimen.padding_hight),
                            bottom = dimensionResource(id = R.dimen.padding_hight)
                        ),
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.orange_red)),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = dimensionResource(id = R.dimen.elevation)
                        )
                    ) {
                        Text(text = stringResource(id = R.string.learn_vocab))
                    }

                }
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Row(
                        modifier = Modifier
                            .padding(top = dimensionResource(id = R.dimen.padding_medium))
                            .clickable {

                            }, verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.change_topic),
                            style = TypeText.h8.copy(color = colorResource(id = R.color.white)),
                            modifier = Modifier.padding(end = dimensionResource(id = R.dimen.padding_small))
                        )
                        Icon(
                            Icons.Default.Cached,
                            contentDescription = "Change Topic",
                            tint = colorResource(
                                id = R.color.white
                            )
                        )
                    }
                    AsyncImage(
                        model = ImageRequest.Builder(
                            context = LocalContext.current
                        ).data("").build(),
                        contentDescription = "Image Topic",
                        modifier = Modifier.size(
                            width = dimensionResource(id = R.dimen.size_icon_medium),
                            height = dimensionResource(id = R.dimen.size_icon_medium)
                        )
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBottomBar(
    selectedItemIndex: Int
) {
    val listItems = listOf(
        BottomNavigationItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            hasNews = true
        ), BottomNavigationItem(
            title = "Chat",
            selectedIcon = Icons.AutoMirrored.Filled.Chat,
            unselectedIcon = Icons.AutoMirrored.Outlined.Chat,
            hasNews = false
        ), BottomNavigationItem(
            title = "Settings",
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings,
            hasNews = false
        )
    )
    NavigationBar(
        containerColor = colorResource(id = R.color.white),
        tonalElevation = 3.dp
    ) {
        listItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = (selectedItemIndex == index),
                onClick = { /*TODO*/ },
                label = { Text(text = item.title, style = TypeText.h8) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colorResource(id = R.color.green_100),
                    selectedTextColor = colorResource(id = R.color.green_100),
                    unselectedIconColor = colorResource(id = R.color.gray_50),
                    unselectedTextColor = colorResource(id = R.color.gray_50),
                    indicatorColor = Color.White
                ),
                icon = {
                    BadgedBox(badge = {
                        if (item.badgeCount != null) {
                            Text(text = item.badgeCount.toString())
                        } else if (item.hasNews) {
                            Badge()
                        }
                    }) {
                        Icon(
                            imageVector = if (index == selectedItemIndex) item.selectedIcon
                            else item.unselectedIcon, contentDescription = item.title
                        )
                    }
                })
        }
    }
}
