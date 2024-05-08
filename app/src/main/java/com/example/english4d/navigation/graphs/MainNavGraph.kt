package com.example.english4d.navigation.graphs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.english4d.navigation.Routes
const val TIME_TRANSITION = 750
@Composable
fun MainNavGraph(
    innerPadding: PaddingValues,
    navController: NavHostController = rememberNavController(),
    showBottomBar:MutableState<Boolean>
) {
    NavHost(navController = navController, startDestination = Routes.HomeGraph ){
        composable(route = Routes.HomeGraph) {
            HomeNavGraph(innerPadding = innerPadding, showBottomBar = showBottomBar)
        }
        composable(route = Routes.PronunciationGraph) {
            PronunciationNavGraph(innerPadding = innerPadding,showBottomBar = showBottomBar )
        }
        composable(route = Routes.ExtensionGraph) {
            ExtensionNavGraph(innerPadding = innerPadding,showBottomBar = showBottomBar)
        }
    }
}