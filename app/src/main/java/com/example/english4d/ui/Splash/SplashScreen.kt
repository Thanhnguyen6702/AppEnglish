package com.example.english4d.ui.Splash

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.english4d.R
import com.example.english4d.navigation.Screen
import com.example.english4d.ui.AppViewModelProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("RememberReturnType")
@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    val offsetIconY =
        remember { Animatable(-300f) } // Sử dụng giá trị âm để hình ảnh bắt đầu ở trên cùng của màn hình
    val offsetTextY = remember { Animatable(500f) }
    LaunchedEffect(Unit) {
        launch {
            offsetIconY.animateTo(
                targetValue = 0f, // Điểm cuối cùng, hình ảnh sẽ di chuyển đến vị trí giữa màn hình (vị trí offset là 0)
                animationSpec = keyframes {
                    durationMillis = 3000
                    -300f at 0 using LinearOutSlowInEasing
                    250f at 2000
                    250f at 3000
                }
            )
        }
        launch {
            offsetTextY.animateTo(
                targetValue = 0f, // Điểm cuối cùng, hình ảnh sẽ di chuyển đến vị trí giữa màn hình (vị trí offset là 0)
                animationSpec = keyframes {
                    durationMillis = 3000
                    500f at 0 using LinearOutSlowInEasing
                    250f at 2000
                    250f at 3000
                }
            )
        }

        delay(3000L)
        if (uiState.updated) navController.navigate(Screen.Home.route)
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.icon_login),
            contentDescription = "Icon",
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.size_icon_splash))
                .padding(bottom = dimensionResource(id = R.dimen.padding_small))
                .offset(y = offsetIconY.value.dp)
        )
        Column(
            modifier = Modifier.offset(y = offsetTextY.value.dp)
        ) {
            Text(text = stringResource(id = R.string.app_name))
            Text(text = stringResource(id = R.string.wellcome_splash))
        }
    }
}
