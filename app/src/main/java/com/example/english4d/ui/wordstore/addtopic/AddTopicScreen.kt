package com.example.english4d.ui.wordstore.addtopic

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.english4d.R
import com.example.english4d.ui.theme.TypeText
import com.example.english4d.ui.wordstore.WordStoreViewModel
import com.example.english4d.utils.listColor
import com.example.englishe4.presentation.component.ItemWordCardScreen
import com.example.englishe4.presentation.component.TopAppBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWordScreen(
    navController: NavHostController,
    viewModel: WordStoreViewModel
) {
    val resultSearch = viewModel.topicCards.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val active = remember {
        mutableStateOf(false)
    }
    val focusRequester = remember { FocusRequester() }
    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            TopAppBar(title = "Kho của tôi", isShowTick = uiState.showRemoveWordResult, onClickRight = {}, onClickFinish = {viewModel.showDeleteWordResult(false)}) {
                navController.popBackStack()
            }
        },
        bottomBar = {
            Button(
                onClick = {
                    if(uiState.title.isNotBlank()) {
                        viewModel.addTopic(uiState.title)
                    }else{
                        isError = true
                        errorMessage = "Bạn chưa nhập tên chủ đề"
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, bottom = 40.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(30.dp)
            ) {
                Text(text = "Add")
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(start = 16.dp, end = 16.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                modifier = Modifier.padding(bottom = 10.dp, start = 5.dp),
                text = "Title",
            )
            OutlinedTextField(
                value = uiState.title,
                onValueChange = {
                    viewModel.onTitleChange(it)
                },
                placeholder = {
                    Text(
                        text = "Enter the title",
                        modifier = Modifier.padding(top = 2.dp),
                    )
                },
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier.fillMaxWidth(),
                isError = isError
            )
            if (isError) {
                Text(
                    text = errorMessage,
                    color = colorResource(id = R.color.red)
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                modifier = Modifier.padding(bottom = 10.dp, start = 5.dp),
                text = "Vocabulary",
            )
            SearchBar(
                query = uiState.contentSearch,
                onQueryChange = {
                    viewModel.onWordChange(it)
                },
                onSearch = {
                    viewModel.submit(it)
                    active.value = false
                },
                active = active.value,
                onActiveChange = {
                    active.value = it
                },
                windowInsets = WindowInsets(top = (-40).dp),
                colors = SearchBarDefaults.colors(Color.White),
                placeholder = {
                    Text(
                        text = "Nhập vào từ cần thêm",
                        style = TypeText.h6.copy(fontWeight = FontWeight.Medium),
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 250.dp)
                    .padding(all = dimensionResource(id = R.dimen.padding_small))
                    .graphicsLayer(
                        clip = true,
                        shape = RoundedCornerShape(18.dp)
                    )
                    .focusRequester(focusRequester)
                    .onFocusChanged {focusState ->
                        if (!focusState.isFocused) {
                            active.value = false // Update active state when focus is lost
                        }
                    },
                shape = RoundedCornerShape(18.dp),

                ) {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState()),
                ) {
                    resultSearch.value.forEach { item ->
                        Row(
                            modifier = Modifier
                                .clickable {
                                    item.let { it1 ->
                                        viewModel.submit(it1)
                                    }
                                    active.value = false
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = item,
                                style = TypeText.h6,
                                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_hight))
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))

            uiState.wordResult.forEachIndexed { index, dictionaryResponse ->
                dictionaryResponse.response?.let { it1 ->
                    Row(
                        Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ItemWordCardScreen(
                            modifier = Modifier.weight(1f),
                            data = it1,
                            onLongClick = {
                                viewModel.showDeleteWordResult(true)
                            },
                            colors = listColor[index % listColor.size],
                            onClickNav = {
                                // navController.navigate(ExtensionGraphScreen.DetailWord.route)
                            })
                        if (uiState.showRemoveWordResult) {
                            IconButton(onClick = { viewModel.deleteWordResult(index) }) {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
            }
            for(i in 0..< uiState.numberLoading1){
                ItemWordCardScreen( data = "",
                    colors = listColor[i % listColor.size],
                    onLongClick = {  }) {
                }
            }
        }

    }


}

