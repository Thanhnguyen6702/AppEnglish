package com.example.english4d.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.english4d.ui.AppViewModelProvider
import com.example.english4d.ui.fairytail.FairyTailScreen
import com.example.english4d.ui.fairytail.FairyTailViewModel
import com.example.english4d.ui.fairytail.ReadFairyTail
import com.example.english4d.ui.home.HomeScreen
import com.example.english4d.ui.home.HomeViewModel
import com.example.english4d.ui.home.WordsBookScreen
import com.example.english4d.ui.main.MainScreen
import com.example.english4d.ui.newspaper.NewsScreen
import com.example.english4d.ui.newspaper.ReadNewsScreen
import com.example.english4d.ui.pronuciation.PronunciationAssessmentScreen
import com.example.english4d.ui.splash.SplashScreen
import com.example.english4d.ui.topic.TopicScreen
import com.example.english4d.ui.video.ListeningScreen
import com.example.english4d.ui.video.VideoScreen
import com.example.english4d.ui.vocabulary.FinishScreen
import com.example.english4d.ui.vocabulary.NewVocabularyScreen
import com.example.english4d.ui.vocabulary.ReviseScreen
import com.example.english4d.ui.vocabulary.ReviseViewModel

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    var reviseViewModel by remember { mutableStateOf<ReviseViewModel?>(null) }
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(route = Screen.Home.route, arguments = listOf(
            navArgument(HOME_ARGUMENT) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }
        )) {
            val id = it.arguments?.getString(HOME_ARGUMENT)?.toIntOrNull()
            val homeViewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
            HomeScreen(id = id, navController = navController, viewModel = homeViewModel)
        }
        composable(route = Screen.Main.route) {
            MainScreen(navController = navController)
        }
        composable(route = Screen.WordsBook.route,
            arguments = listOf(navArgument(WORDSBOOK_ARGUMENT) {
                type = NavType.IntType
            }
            )
        ) {
            val index = it.arguments?.getInt(WORDSBOOK_ARGUMENT)!!
            val previousBackStackEntry = remember {
                navController.previousBackStackEntry!!
            }
            val homeViewModel: HomeViewModel = viewModel(previousBackStackEntry)
            WordsBookScreen(navController = navController, viewModel = homeViewModel, index = index)
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
                navArgument(NEWVOCAB_ARGUMENT) {
                    type = NavType.IntType
                }
            )
        ) {
            val id = it.arguments?.getInt(NEWVOCAB_ARGUMENT)
            NewVocabularyScreen(id = id, navController = navController)
        }
        composable(route = Screen.TopicsVocab.route) {
            TopicScreen(navController = navController)
        }
        composable(route = Screen.ReviseVocab.route) {
            reviseViewModel = viewModel(factory = AppViewModelProvider.Factory)
            ReviseScreen(navController = navController, viewModel = reviseViewModel!!)
        }
        composable(route = Screen.FinishVocab.route) {
            FinishScreen(navController = navController, viewModel = reviseViewModel!!)
        }
        composable(route = Screen.Pronunciation.route) {
            PronunciationAssessmentScreen(navController = navController)
        }
        composable(
            route = Screen.Video.route, arguments = listOf(
                navArgument(VIDEO_ARGUMENT){
                    type = NavType.StringType
                }
            )
        ) {
            val videoId = it.arguments?.getString(VIDEO_ARGUMENT)?:""
            ListeningScreen(navController = navController, videoId = videoId)
        }
        composable(route = Screen.Channel.route){
            VideoScreen(navController = navController)
        }
        composable(route = Screen.FairyTopic.route){
            val fairyViewModel:FairyTailViewModel = viewModel()
            FairyTailScreen(navController = navController, viewmodel = fairyViewModel)
        }
        composable(route = Screen.ReadFairy.route){
            val parentEntry = remember(it) {
                navController.getBackStackEntry(Screen.FairyTopic.route)
            }
            val fairyViewModel:FairyTailViewModel = viewModel(parentEntry)
            ReadFairyTail(navController = navController, viewmodel = fairyViewModel)
        }
    }
}