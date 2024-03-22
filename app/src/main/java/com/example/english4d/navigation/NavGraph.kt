package com.example.english4d.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.english4d.ui.theme.home.HomeScreen
import com.example.english4d.ui.theme.newspaper.NewsScreen
import com.example.english4d.ui.theme.newspaper.ReadNewsScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(route = Screen.NewsTopic.route) {
            NewsScreen(navController = navController)
        }
        composable(
            route = Screen.ReadNews.route,
            arguments = listOf(
                navArgument(READNEWS_ARGUMENT_TOPIC) {
                    type = NavType.StringType
                },
                navArgument(READNEWA_ARGUMENT_HREF) {
                    type = NavType.StringType
                }
            )
        ) {
            val topic = it.arguments?.getString(READNEWS_ARGUMENT_TOPIC).toString()
            val url = it.arguments?.getString(READNEWA_ARGUMENT_HREF).toString()
            ReadNewsScreen(topic = topic, url = url)
        }
    }
}