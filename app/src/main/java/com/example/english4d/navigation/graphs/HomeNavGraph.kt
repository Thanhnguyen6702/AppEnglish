package com.example.english4d.navigation.graphs

import android.annotation.SuppressLint
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
import com.example.english4d.navigation.HOME_ARGUMENT
import com.example.english4d.navigation.HomeGraphScreen
import com.example.english4d.navigation.NEWVOCAB_ARGUMENT
import com.example.english4d.navigation.WORDSBOOK_ARGUMENT
import com.example.english4d.ui.AppViewModelProvider
import com.example.english4d.ui.home.HomeScreen
import com.example.english4d.ui.home.HomeViewModel
import com.example.english4d.ui.home.WordsBookScreen
import com.example.english4d.ui.topic.TopicScreen
import com.example.english4d.ui.vocabulary.FinishScreen
import com.example.english4d.ui.vocabulary.NewVocabularyScreen
import com.example.english4d.ui.vocabulary.ReviseScreen
import com.example.english4d.ui.vocabulary.ReviseViewModel


@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun HomeNavGraph(
    innerPadding: PaddingValues,
    navHostController: NavHostController = rememberNavController(),
    showBottomBar :MutableState<Boolean>
) {
    NavHost(
        navController = navHostController,
        startDestination = HomeGraphScreen.Home.route,
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                tween(TIME_TRANSITION)
            )
        }
    ) {
        composable(
            route = HomeGraphScreen.Home.route,
            arguments = listOf(navArgument(HOME_ARGUMENT) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            })
        ) {
            showBottomBar.value = true
            val id = it.arguments?.getString(HOME_ARGUMENT)?.toIntOrNull()
            val homeViewModel:HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
            HomeScreen(id = id, navController = navHostController, innerPadding = innerPadding, viewModel = homeViewModel)
        }

        composable(
            route = HomeGraphScreen.WordsBook.route,
            arguments = listOf(navArgument(WORDSBOOK_ARGUMENT) {
                type = NavType.IntType
            })
        ) {
            showBottomBar.value = false
            val index = it.arguments?.getInt(WORDSBOOK_ARGUMENT)!!
            val parentEntry = remember {
                navHostController.getBackStackEntry(HomeGraphScreen.Home.route)
            }
            val homeViewModel:HomeViewModel = viewModel(parentEntry)
            WordsBookScreen(
                navController = navHostController,
                viewModel = homeViewModel,
                index = index
            )
        }

        composable(
            route = HomeGraphScreen.NewVocab.route,
            arguments = listOf(navArgument(NEWVOCAB_ARGUMENT) {
                type = NavType.IntType
            })
        ) {
            showBottomBar.value = false
            val id = it.arguments?.getInt(NEWVOCAB_ARGUMENT)
            NewVocabularyScreen(id = id, navController = navHostController)
        }
        composable(route = HomeGraphScreen.TopicsVocab.route) {
            showBottomBar.value = false
            TopicScreen(navController = navHostController)
        }
        composable(route = HomeGraphScreen.ReviseVocab.route) {
            showBottomBar.value = false
            val reviseViewModel:ReviseViewModel = viewModel(factory = AppViewModelProvider.Factory)
            ReviseScreen(navController = navHostController, viewModel = reviseViewModel)
        }
        composable(route = HomeGraphScreen.FinishVocab.route) {
            val parentEntry = remember(this) {
                navHostController.getBackStackEntry(HomeGraphScreen.ReviseVocab.route)
            }
            val reviseViewModel:ReviseViewModel = viewModel(parentEntry)
            FinishScreen(
                navController = navHostController,
                viewModel = reviseViewModel
            )
        }
    }
}
