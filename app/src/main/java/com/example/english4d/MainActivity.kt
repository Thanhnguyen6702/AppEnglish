package com.example.english4d

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.english4d.ui.theme.newspaper.NewsViewmodel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val newsViewmodel: NewsViewmodel = viewModel(factory = NewsViewmodel.Factory)
            val uiState = newsViewmodel.newsUiState
           // PaperLayout(uiState)
//            ReadNewsScreen("",listItem = listOf(
//                "The Vietnamese restaurant stands among six eateries across the United States selected for the 2024 America's Classics Award, given to \"locally owned restaurants that serve quality food, have timeless appeal, and reflect the character of their communities.\"",
//                "https://i1-english.vnecdn.net/2024/03/14/392856095-18413839876040437-54-5934-5267-1710408514.jpg?w=680&h=0&q=100&dpr=1&fit=crop&s=-PA2hhUqXE1__9n_6D5xjA",
//                "Established by Thuyen Luu and Nhu Lai in 1984, Vietnam Restaurant sprouted its roots just five years after the couple immigrated to the U.S. from Vietnam with only $30, as noted by The Philadelphia Inquirer.",
//                "The restaurant is known for its wide range of selections, including barbecue platters, crispy spring rolls, vermicelli noodle bowls, soups and stir-fries.",
//                "The James Beard Foundation hailed it as \"one of Philly’s favorite date-night destinations for classic Vietnamese cuisine,\" and \"a model of consistency for decades\", as reported by Eater Philly."
//            ))
             LayoutEnglish()
        }

    }
}



