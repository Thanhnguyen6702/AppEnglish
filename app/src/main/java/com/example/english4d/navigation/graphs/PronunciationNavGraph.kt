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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.english4d.navigation.PronunciationGraphScreen
import com.example.english4d.ui.pronuciation.DetailStatisticPronunciationScreen
import com.example.english4d.ui.pronuciation.PronunciationAssessmentScreen
import com.example.english4d.ui.pronuciation.PronunciationAssessmentViewModel
import com.example.english4d.ui.pronuciation.PronunciationStatisticScreen

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun PronunciationNavGraph(
    innerPadding: PaddingValues,
    navController: NavHostController = rememberNavController(),
    showBottomBar : MutableState<Boolean>
)  {
    NavHost(
        navController = navController ,
        startDestination = PronunciationGraphScreen.StatisticPronunciation.route,
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                tween(TIME_TRANSITION)
            )
        }
    ) {
        composable(route = PronunciationGraphScreen.StatisticPronunciation.route){
            showBottomBar.value = true
            PronunciationStatisticScreen( navController = navController, innerPadding = innerPadding)
        }
        composable(route = PronunciationGraphScreen.Pronunciation.route) {
            showBottomBar.value = false
            val parentEntry = remember {
                navController.getBackStackEntry(PronunciationGraphScreen.StatisticPronunciation.route)
            }
            val viewModel:PronunciationAssessmentViewModel = viewModel(parentEntry)
            PronunciationAssessmentScreen(navController = navController, viewModel = viewModel)
        }
        composable(route = PronunciationGraphScreen.DetailStatisticPronunciation.route){
            showBottomBar.value = false
            val parentEntry = remember {
                navController.getBackStackEntry(PronunciationGraphScreen.StatisticPronunciation.route)
            }
            val viewModel:PronunciationAssessmentViewModel = viewModel(parentEntry)
            DetailStatisticPronunciationScreen(navController = navController, viewModel = viewModel)
        }
    }
}