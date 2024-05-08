package com.example.english4d.navigation.graphs

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.english4d.navigation.PronunciationGraphScreen
import com.example.english4d.ui.pronuciation.PronunciationAssessmentScreen
import com.example.english4d.ui.pronuciation.PronunciationStatisticScreen

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
            PronunciationAssessmentScreen(navController = navController)
        }
    }
}