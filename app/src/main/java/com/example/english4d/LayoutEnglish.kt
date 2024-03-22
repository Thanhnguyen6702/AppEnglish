package com.example.english4d

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.english4d.navigation.SetupNavGraph


@Composable
fun LayoutEnglish(
     navController: NavHostController = rememberNavController()
){
    SetupNavGraph(navController = navController)
}