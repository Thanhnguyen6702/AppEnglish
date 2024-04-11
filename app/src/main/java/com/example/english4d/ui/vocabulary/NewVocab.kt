package com.example.english4d.ui.vocabulary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.HelpCenter
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.english4d.R
import com.example.english4d.ui.AppViewModelProvider
import com.example.english4d.ui.theme.TypeText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewVocabularyScreen(
    id: Int?,
    navController: NavHostController,
    viewmodel: NewVocabViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val newVocabViewModel by viewmodel.uiState.collectAsState()
    viewmodel.updateLayout(id?:1)
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = newVocabViewModel.title,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = newVocabViewModel.vocabulary.english, style = TypeText.bodyLarge.copy(
                        color = colorResource(
                            id = R.color.green
                        )
                    ), textAlign = TextAlign.Center
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_hight))
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.VolumeUp,
                        contentDescription = "VolumeUp",
                        modifier = Modifier.padding(
                            horizontal = dimensionResource(
                                id = R.dimen.padding_medium
                            )
                        )
                    )
                    Text(text = newVocabViewModel.vocabulary.pronunciation, style = TypeText.h7)
                }
                Text(
                    text = newVocabViewModel.vocabulary.vietnamese,
                    style = TypeText.h4.copy(color = colorResource(id = R.color.orange_100))
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = dimensionResource(id = R.dimen.padding_hight),
                            horizontal = dimensionResource(id = R.dimen.padding_medium),
                        )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Example", style = TypeText.h6, modifier = Modifier.padding(
                                dimensionResource(id = R.dimen.padding_small)
                            )
                        )
                        Icon(Icons.Default.KeyboardArrowDown, contentDescription = "ArrowDown")
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Defination", style = TypeText.h6, modifier = Modifier.padding(
                                dimensionResource(id = R.dimen.padding_small)
                            )
                        )
                        Icon(Icons.Default.KeyboardArrowDown, contentDescription = "ArrowDown")
                    }
                }
            }
            BottomBarNewVocab(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_hight)))
        }
    }

}

@Composable
fun BottomBarNewVocab(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = dimensionResource(id = R.dimen.padding_hight)),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.HelpCenter,
                    contentDescription = "Don't Know",
                    tint = colorResource(
                        id = R.color.gray_50
                    ),
                    modifier = Modifier.size(36.dp, 36.dp)
                )
                Text(text = "Không biết")

            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Default.Warning,
                    contentDescription = "Don't Know",
                    tint = colorResource(
                        id = R.color.gray_50
                    ),
                    modifier = Modifier.size(36.dp, 36.dp)
                )
                Text(text = "Không chắc")

            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Default.CheckBox,
                    contentDescription = "Don't Know",
                    tint = colorResource(
                        id = R.color.gray_50
                    ),
                    modifier = Modifier.size(36.dp, 36.dp)
                )
                Text(text = "Đã biết")

            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Icon(
                Icons.Default.ArrowBackIosNew, contentDescription = "Arrow Back",
                modifier = Modifier.size(36.dp, 36.dp)
            )
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.teal_700))
            ) {
                Text(text = "Done")
            }
            Icon(
                Icons.AutoMirrored.Filled.ArrowForwardIos, contentDescription = "Arrow Forward",
                modifier = Modifier.size(36.dp, 36.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNewVocab() {
    NewVocabularyScreen(id = 1, navController = NavHostController(LocalContext.current))
}