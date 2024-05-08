package com.example.english4d.ui.vocabulary

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.example.english4d.R
import com.example.english4d.navigation.HomeGraphScreen
import com.example.english4d.navigation.Routes
import com.example.english4d.ui.theme.TypeText

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun FinishScreen(
    rootNavHostController: NavHostController,
    navController: NavHostController,
    viewModel: ReviseViewModel,
) {
    val uiState = viewModel.uiStateFinish.collectAsState()
    Scaffold(

    ) {
        Column(
            modifier = Modifier
                .padding(
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding()
                )
                .fillMaxHeight()
        ) {
            IconButton(onClick = { navController.navigate(HomeGraphScreen.Home.route) }) {
                Icon(Icons.Default.Close, contentDescription = null)
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_finish),
                    contentDescription = null,
                    modifier = Modifier.size(
                        dimensionResource(id = R.dimen.size_icon_medium)
                    )
                )
                Text(
                    text = stringResource(id = R.string.finish),
                    style = TypeText.bodyMedium,
                    modifier = Modifier.padding(
                        top = dimensionResource(
                            id = R.dimen.padding_hight
                        ), bottom = dimensionResource(id = R.dimen.padding_medium)
                    )
                )
                Text(
                    text = stringResource(id = R.string.number_finish, viewModel.numberRevise),
                    style = TypeText.h7,
                    modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_small))
                )
                LazyColumn(
                    modifier = Modifier.weight(8f)
                ) {
                    items(uiState.value.listItemFinish) {
                        ItemFinishLayout(it)
                        Divider()
                    }
                }
                Box(modifier = Modifier.weight(2f), contentAlignment = Alignment.Center) {
                    ElevatedButton(
                        onClick = {
                            if (uiState.value.isContinueRevise) {
                                navController.navigate(HomeGraphScreen.ReviseVocab.route) {
                                    popUpTo(HomeGraphScreen.FinishVocab.route) { inclusive = true }
                                }
                            } else {
                                rootNavHostController.navigate(Routes.HomeGraph) {
                                    popUpTo(HomeGraphScreen.FinishVocab.route) { inclusive = true }
                                }
                            }
                        },
                    ) {
                        Text(
                            text = if (uiState.value.isContinueRevise) stringResource(id = R.string.continue_revise)
                            else stringResource(id = R.string.learn_vocab)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ItemFinishLayout(
    itemFinish: ItemFinish,
) {
    Row(
        modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_medium)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.AutoMirrored.Filled.VolumeUp,
            contentDescription = null,
            modifier = Modifier.padding(
                horizontal = dimensionResource(id = R.dimen.padding_medium),
            ),
            tint = colorResource(id = R.color.green_100)
        )
        Column {
            Text(
                text = itemFinish.vocabulary.english,
                style = TypeText.h6.copy(fontWeight = FontWeight.Medium)
            )
            Text(text = itemFinish.vocabulary.vietnamese, style = TypeText.h8)
        }
        Spacer(modifier = Modifier.weight(1f))
        Column(
            modifier = Modifier.padding(end = dimensionResource(id = R.dimen.padding_medium)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(id = R.string.revise_again), style = TypeText.h8)
            Text(
                text = if (itemFinish.dayRevise > 0) stringResource(
                    id = R.string.day, itemFinish.dayRevise
                ) else stringResource(
                    id = R.string.finish
                ), style = TypeText.h7.copy(fontWeight = FontWeight.Medium)
            )
        }
    }
}
