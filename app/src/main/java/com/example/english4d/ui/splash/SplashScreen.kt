package com.example.english4d.ui.splash

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.english4d.R
import com.example.english4d.navigation.Routes
import com.example.english4d.navigation.SplashNavScreen
import com.example.english4d.ui.AppViewModelProvider
import kotlinx.coroutines.delay

@SuppressLint("RememberReturnType")
@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(uiState.updated) {
        if (uiState.updated) {
            delay(3000L) // Nếu cần đợi một thời gian cụ thể
            navController.navigate(Routes.MainGraph){
                popUpTo(SplashNavScreen.Splash.route){inclusive = true}
            }
        }
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.icon_login),
            contentDescription = "Icon",
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.size_icon_splash))
                .padding(bottom = dimensionResource(id = R.dimen.padding_small))
        )
    }
}


