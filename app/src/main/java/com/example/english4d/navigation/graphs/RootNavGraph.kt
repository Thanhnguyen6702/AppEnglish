package com.example.english4d.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.english4d.navigation.Routes
import com.example.english4d.ui.main.MainScreen

@Composable
fun RootNavGraph() {
    val rootNavGraph = rememberNavController()
    NavHost(
        navController = rootNavGraph,
        route = Routes.RootGraph,
        startDestination = Routes.SplashGraph
    ) {
        splashNavGraph(rootNavController = rootNavGraph)
        composable(route = Routes.MainGraph) {
            MainScreen()
        }
    }
}