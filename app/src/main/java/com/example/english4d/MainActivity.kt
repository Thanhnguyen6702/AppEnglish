package com.example.english4d

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.english4d.ui.theme.newspaper.NewsViewmodel
import com.example.english4d.ui.theme.newspaper.PaperLayout

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val newsViewmodel: NewsViewmodel = viewModel(factory = NewsViewmodel.Factory)
            val uiState = newsViewmodel.newsUiState
            PaperLayout(uiState)
        }
    }
}



