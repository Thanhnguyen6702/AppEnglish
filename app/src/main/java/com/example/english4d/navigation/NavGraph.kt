package com.example.english4d.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.english4d.ui.animation.SplashScreen
import com.example.english4d.ui.home.HomeScreen
import com.example.english4d.ui.newspaper.NewsScreen
import com.example.english4d.ui.newspaper.ReadNewsScreen
import com.example.english4d.ui.topic.TopicScreen
import com.example.english4d.ui.vocabulary.NewVocabularyScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route){
            SplashScreen(navController = navController)
        }
        composable(route = Screen.Home.route, arguments = listOf(
            navArgument(HOME_ARGUMENT){
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }
        )) {
            val id = it.arguments?.getString(HOME_ARGUMENT)?.toIntOrNull()
            HomeScreen(id = id,navController = navController)
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
                navArgument(READNEWS_ARGUMENT_HREF) {
                    type = NavType.StringType
                }
            )
        ) {
            val topic = it.arguments?.getString(READNEWS_ARGUMENT_TOPIC).toString()
            val url = it.arguments?.getString(READNEWS_ARGUMENT_HREF).toString()
            ReadNewsScreen(topic = topic, url = url)
        }
        composable(route = Screen.NewVocab.route,
            arguments = listOf(
                navArgument(NEWVOCAB_ARGUMENT){
                    type = NavType.IntType
                }
            )
        ){
            val id = it.arguments?.getInt(NEWVOCAB_ARGUMENT)
            NewVocabularyScreen(id = id,navController = navController)
        }
        composable(route = Screen.TopicsVocab.route){
            TopicScreen(navController = navController)
        }
    }
}