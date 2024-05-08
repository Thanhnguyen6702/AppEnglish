package com.example.english4d.ui.vocabulary

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.HelpCenter
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.english4d.R
import com.example.english4d.ui.AppViewModelProvider
import com.example.english4d.ui.customdialog.DialogRevise
import com.example.english4d.ui.theme.TypeText

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewVocabularyScreen(
    id: Int?,
    navController: NavHostController,
    viewmodel: NewVocabViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val newVocabViewModel by viewmodel.uiState.collectAsState()
    var isDefinitionExpanded by remember {
        mutableStateOf(false)
    }
    var isExampleExpanded by remember {
        mutableStateOf(false)
    }
    var isShowDialog by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = id) {
        viewmodel.updateLayout(id ?: 1)
    }
    if (isShowDialog){
        DialogRevise(onDismissRequest = { isShowDialog = false }) {
            isShowDialog = false
            navController.popBackStack()
        }
    }
    BackHandler(enabled = true) {}
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        isShowDialog = true
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                            contentDescription = null
                        )
                    }
                },
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
            modifier = Modifier
                .fillMaxHeight()
                .padding(it),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
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
                    Column(
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Example", style = TypeText.h6, modifier = Modifier.padding(
                                    dimensionResource(id = R.dimen.padding_small)

                                )
                            )
                            IconButton(onClick = {
                                isExampleExpanded = !isExampleExpanded
                                viewmodel.getExamples()
                            }) {
                                Icon(
                                    Icons.Default.KeyboardArrowDown,
                                    contentDescription = "ArrowDown"
                                )
                            }
                        }
                        if (isExampleExpanded) {
                            LazyColumn {
                                items(newVocabViewModel.examples) {
                                    ItemExpanded(text = it.example)
                                }
                            }
                        }
                    }
                    Column {

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Defination",
                                style = TypeText.h6,
                                modifier = Modifier.padding(
                                    dimensionResource(id = R.dimen.padding_small)
                                )
                            )
                            IconButton(onClick = {
                                isDefinitionExpanded = !isDefinitionExpanded
                                viewmodel.getDefinitions()
                            }) {

                                Icon(
                                    Icons.Default.KeyboardArrowDown,
                                    contentDescription = "ArrowDown"
                                )
                            }
                        }
                        if (isDefinitionExpanded) {
                            LazyColumn {
                                items(newVocabViewModel.definitions) {
                                    ItemExpanded(text = it.definition)
                                }
                            }
                        }
                    }
                }
            }
            BottomBarNewVocab(
                onReset = {
                    isDefinitionExpanded = false
                    isExampleExpanded = false
                },
                viewmodel = viewmodel,
                modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_small))
            )
        }
    }

}

@Composable
fun BottomBarNewVocab(
    onReset: () -> Unit,
    viewmodel: NewVocabViewModel,
    modifier: Modifier = Modifier
) {
    val newVocabUiState by viewmodel.uiState.collectAsState()
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
                IconButton(onClick = {
                    viewmodel.setStatus(StatusVocab.UNLEARNED)
                }) {
                    Icon(
                        Icons.AutoMirrored.Filled.HelpCenter,
                        contentDescription = "Don't Know",
                        tint = if (newVocabUiState.statusVocab == StatusVocab.UNLEARNED)
                            colorResource(id = R.color.orange_red)
                        else colorResource(id = R.color.gray_50),
                        modifier = Modifier.size(36.dp, 36.dp)
                    )
                }
                Text(text = "Không biết")

            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(onClick = { viewmodel.setStatus(StatusVocab.UNCERTAIN) }) {
                    Icon(
                        Icons.Default.Warning,
                        contentDescription = "Don't Know",
                        tint = if (newVocabUiState.statusVocab == StatusVocab.UNCERTAIN)
                            colorResource(id = R.color.orange_50)
                        else colorResource(id = R.color.gray_50),
                        modifier = Modifier.size(36.dp, 36.dp)
                    )
                }
                Text(text = "Không chắc")

            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                IconButton(onClick = { viewmodel.setStatus(StatusVocab.MASTER) }) {
                    Icon(
                        Icons.Default.CheckBox,
                        contentDescription = "Don't Know",
                        tint = if (newVocabUiState.statusVocab == StatusVocab.MASTER)
                            colorResource(id = R.color.green_100)
                        else colorResource(id = R.color.gray_50),
                        modifier = Modifier.size(36.dp, 36.dp)
                    )
                }
                Text(text = "Đã biết")

            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Icon(
                Icons.Default.ArrowBackIosNew, contentDescription = "Arrow Back",
                modifier = Modifier
                    .size(36.dp, 36.dp)
                    .clickable {
                        viewmodel.backVocab()
                        onReset()
                    }
            )
            Button(
                onClick = {
                    viewmodel.updateStatistic()
                    onReset()
                },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.teal_700))
            ) {
                Text(text = "Done")
            }
            Icon(
                Icons.AutoMirrored.Filled.ArrowForwardIos, contentDescription = "Arrow Forward",
                modifier = Modifier
                    .size(36.dp, 36.dp)
                    .clickable {
                        viewmodel.nextVocab()
                        onReset()
                    },
            )
        }
    }
}


@Composable
fun ItemExpanded(text: String, modifier: Modifier = Modifier) {

    Row {
        Icon(
            Icons.Default.Add, contentDescription = "icon", modifier = modifier.padding(
                vertical = dimensionResource(id = R.dimen.padding_medium)
            )
        )
        Text(
            text = text, style = TypeText.h6, modifier = modifier.padding(
                horizontal = dimensionResource(id = R.dimen.padding_medium),
                vertical = dimensionResource(id = R.dimen.padding_medium)
            )
        )
    }
}