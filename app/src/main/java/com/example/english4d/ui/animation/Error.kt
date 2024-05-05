package com.example.english4d.ui.animation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import com.example.english4d.R
import com.example.english4d.ui.theme.TypeText

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    onClick: ()->Unit = {}
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_hight)),
            text = "Không thể kết nối đến server",
            style = TypeText.h5.copy(fontWeight = FontWeight.Medium)
        )
        ElevatedButton(onClick = onClick, colors = ButtonDefaults.buttonColors(containerColor = colorResource(
            id = R.color.green_100
        ))) {
            Text(text = "Thử lại")
        }
    }
}