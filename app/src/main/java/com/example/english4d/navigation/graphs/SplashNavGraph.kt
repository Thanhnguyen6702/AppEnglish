package com.example.english4d.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.english4d.navigation.Routes
import com.example.english4d.navigation.SplashNavScreen
import com.example.english4d.ui.splash.SplashScreen

fun NavGraphBuilder.splashNavGraph(rootNavController: NavHostController) {
    navigation(
        route = Routes.SplashGraph,
        startDestination = SplashNavScreen.Splash.route
    ){
       composable(route = SplashNavScreen.Splash.route){
           SplashScreen(navController = rootNavController)
       }
    }
}