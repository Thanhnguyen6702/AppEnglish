package com.example.englishe4.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.english4d.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    type: String,
    navController: NavHostController,
    title : String
) {
    androidx.compose.material3.TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = TopAppBarDefaults.smallTopAppBarColors(
            // Access default colors
            containerColor = Color.White,
        ),
        title = {
            Row (
                modifier = Modifier
                    .height(60.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { /*TODO*/ }) {
                    Icon(
                        modifier = Modifier
                            .size(20.dp),
                        painter = painterResource(id = R.drawable.icon_backpress) ,
                        contentDescription = "BACKPRESS"
                    )
                }
                Text(
                    modifier = Modifier,
                    text = title,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    )

                )
                if(type == "default") {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_book),
                            contentDescription = "BOX"
                        )
                    }
                }
                else{
                    IconButton(onClick = {
                      //  navController.navigate(Screen.BottomBar.Home.bRouter)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_play),
                            contentDescription = "BOX"
                        )
                    }
                }

            }

        }
    )

}

@Preview(showBackground = true)
@Composable
fun dahda() {
    TopAppBar(type = "main", navController = rememberNavController(), title = "Kho từ của tôi")
}