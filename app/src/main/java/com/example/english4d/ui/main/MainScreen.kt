package com.example.english4d.ui.main

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DashboardCustomize
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Speaker
import androidx.compose.material.icons.outlined.DashboardCustomize
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Speaker
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.english4d.navigation.Routes
import com.example.english4d.navigation.graphs.MainNavGraph
import com.example.english4d.ui.theme.TypeText

sealed class BottomNavigationItem(
    val route: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
) {
    object Home : BottomNavigationItem(
        route = Routes.HomeGraph,
        title = "từ vựng",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
    )

    object Speaking : BottomNavigationItem(
        route = Routes.PronunciationGraph,
        title = "phát âm",
        selectedIcon = Icons.Filled.Speaker,
        unselectedIcon = Icons.Outlined.Speaker,
    )

    object Extension : BottomNavigationItem(
        route = Routes.ExtensionGraph,
        title = "mở rộng",
        selectedIcon = Icons.Filled.DashboardCustomize,
        unselectedIcon = Icons.Outlined.DashboardCustomize,
    )
}

//@Composable
//fun MainScreen(
//    navController: NavController,
//    viewModel: MainViewModel = viewModel()
//) {
//    val uiState by viewModel.uiState.collectAsState()
//    Scaffold(
//        bottomBar = {
//            MyBottomBar(
//                selectedItemIndex = uiState.selectedItemIndex,
//                onClick = { viewModel.setIndex(it) })
//        }
//    ) {
//        when (uiState.selectedItemIndex) {
//            0 -> HomeScreen(
//                navController = navController,
//                modifier = Modifier.padding(bottom = it.calculateBottomPadding())
//            )
//
//            1 -> PronunciationStatisticScreen(
//                navController = navController,
//                modifier = Modifier.padding(bottom = it.calculateBottomPadding())
//            )
//
//            2 -> ExtensionScreen(navController = navController)
//        }
//    }
//}



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    mainNavController :NavHostController = rememberNavController()
) {
    val items = listOf(
        BottomNavigationItem.Home,
        BottomNavigationItem.Speaking,
        BottomNavigationItem.Extension
    )
    val navBackStackEntry by mainNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val isShowBottomBar = remember {
        mutableStateOf(true)
    }
    Scaffold(
        bottomBar = {
            if (isShowBottomBar.value)
            NavigationBar {
                items.forEach { item ->
                    NavigationBarItem(
                        selected = currentRoute == item.route,
                        label = { Text(text = item.title, style = TypeText.h8) },
                        icon = {
                            Icon(
                                imageVector = if (currentRoute == item.route) item.selectedIcon
                                else item.unselectedIcon, contentDescription = item.title
                            )
                        },
                        onClick = {
                            if (currentRoute != item.route) {
                                mainNavController.graph.startDestinationRoute?.let {
                                    mainNavController.popBackStack(it, true)
                                }
                                mainNavController.navigate(item.route){
                                    launchSingleTop = true
                                }
                            }
                        }
                    )
                }
            }
        }
    ) {
            MainNavGraph(innerPadding = it,navController = mainNavController, showBottomBar = isShowBottomBar)
    }
}

