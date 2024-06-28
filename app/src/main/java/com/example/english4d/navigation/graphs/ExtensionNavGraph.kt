package com.example.english4d.navigation.graphs

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.english4d.navigation.ExtensionGraphScreen
import com.example.english4d.navigation.READNEWS_ARGUMENT_HREF
import com.example.english4d.navigation.READNEWS_ARGUMENT_TOPIC
import com.example.english4d.navigation.STATISTIC_NEWS_ARGUMENT
import com.example.english4d.navigation.VIDEO_ID_ARGUMENT
import com.example.english4d.navigation.VIDEO_MODE
import com.example.english4d.navigation.VIDEO_TITLE_ARGUMENT
import com.example.english4d.navigation.VIDEO_URL_ARGUMENT
import com.example.english4d.ui.AppViewModelProvider
import com.example.english4d.ui.extension.ExtensionScreen
import com.example.english4d.ui.fairytail.FairyTailScreen
import com.example.english4d.ui.fairytail.FairyTailViewModel
import com.example.english4d.ui.fairytail.ReadFairyTail
import com.example.english4d.ui.newspaper.NewsScreen
import com.example.english4d.ui.newspaper.NewsViewmodel
import com.example.english4d.ui.newspaper.ReadNewsScreen
import com.example.english4d.ui.newspaper.SeeMoreNewsScreen
import com.example.english4d.ui.newspaper.StatisticNewsScreen
import com.example.english4d.ui.video.ListeningScreen
import com.example.english4d.ui.video.SeeMoreVideoScreen
import com.example.english4d.ui.video.VideoModeScreen
import com.example.english4d.ui.video.VideoScreen
import com.example.english4d.ui.video.VideoViewModel


@Composable
fun ExtensionNavGraph(
    navHostController: NavHostController = rememberNavController(),
    innerPadding: PaddingValues,
    showBottomBar: MutableState<Boolean>
) {
    NavHost(
        navController = navHostController,
        startDestination = ExtensionGraphScreen.Extension.route,
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                tween(TIME_TRANSITION)
            )
        }
    ) {
        composable(route = ExtensionGraphScreen.Extension.route) {
            showBottomBar.value = true
            ExtensionScreen(navController = navHostController, innerPadding = innerPadding)
        }
        composable(route = ExtensionGraphScreen.NewsTopic.route) {
            showBottomBar.value = false
            NewsScreen(navController = navHostController)
        }
        composable(route = ExtensionGraphScreen.SeeMoreNews.route) {
            val parentEntry = remember(it) {
                navHostController.getBackStackEntry(ExtensionGraphScreen.NewsTopic.route)
            }
            val viewModel: NewsViewmodel = viewModel(parentEntry)
            SeeMoreNewsScreen(navController = navHostController, viewModel)
        }
        composable(
            route = ExtensionGraphScreen.ReadNews.route,
            arguments = listOf(navArgument(READNEWS_ARGUMENT_TOPIC) {
                type = NavType.StringType
            }, navArgument(READNEWS_ARGUMENT_HREF) {
                type = NavType.StringType
            })
        ) {
            val topic = it.arguments?.getString(READNEWS_ARGUMENT_TOPIC).toString()
            val url = it.arguments?.getString(READNEWS_ARGUMENT_HREF).toString()
            ReadNewsScreen(topic = topic, url = url, navController = navHostController)
        }

        composable(
            route = ExtensionGraphScreen.QuestionStatistic.route,
            arguments = listOf(navArgument(STATISTIC_NEWS_ARGUMENT) {
                type = NavType.StringType
            })
        ) {
            val href = it.arguments?.getString(STATISTIC_NEWS_ARGUMENT) ?: ""
            StatisticNewsScreen(navController = navHostController, href = href)
        }

        composable(
            route = ExtensionGraphScreen.Video.route,
            arguments = listOf(navArgument(VIDEO_ID_ARGUMENT) {
                type = NavType.StringType
            }, navArgument(VIDEO_MODE){
                type = NavType.IntType
            })
        ) {
            val videoId = it.arguments?.getString(VIDEO_ID_ARGUMENT) ?: ""
            val mode = it.arguments?.getInt(VIDEO_MODE) ?: 0
            ListeningScreen(navController = navHostController, videoId = videoId, mode = mode)
        }
        composable(route = ExtensionGraphScreen.Channel.route) {
            showBottomBar.value = false
            VideoScreen(navController = navHostController)
        }
        composable(route = ExtensionGraphScreen.SeeMoreVideo.route) {
            val parentEntry = remember(it) {
                navHostController.getBackStackEntry(ExtensionGraphScreen.Channel.route)
            }
            val viewModel: VideoViewModel = viewModel(parentEntry)
            SeeMoreVideoScreen(navController = navHostController, viewModel)
        }
        composable(
            route = ExtensionGraphScreen.VideoMode.route,
            arguments = listOf(navArgument(VIDEO_ID_ARGUMENT) {
                type = NavType.StringType
            }, navArgument(VIDEO_TITLE_ARGUMENT) {
                type = NavType.StringType
            }, navArgument(VIDEO_URL_ARGUMENT) {
                type = NavType.StringType
            })
        ) {
            val videoId = it.arguments?.getString(VIDEO_ID_ARGUMENT) ?: ""
            val title = it.arguments?.getString(VIDEO_TITLE_ARGUMENT) ?: ""
            val urlImage = it.arguments?.getString(VIDEO_URL_ARGUMENT) ?: ""
            VideoModeScreen(navController = navHostController,urlImage = urlImage, title = title, videoId = videoId)
        }
        composable(route = ExtensionGraphScreen.FairyTopic.route) {
            showBottomBar.value = false
            val fairyViewModel: FairyTailViewModel = viewModel(factory = AppViewModelProvider.Factory)
            FairyTailScreen(navController = navHostController, viewmodel = fairyViewModel)
        }
        composable(route = ExtensionGraphScreen.ReadFairy.route) {
            val parentEntry = remember(it) {
                navHostController.getBackStackEntry(ExtensionGraphScreen.FairyTopic.route)
            }
            val fairyViewModel: FairyTailViewModel = viewModel(parentEntry)
            ReadFairyTail(navController = navHostController, viewmodel = fairyViewModel)
        }
    }
}