package com.example.englishe4.presentation.cardFlip

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.english4d.data.database.wordstore.MyWord
import com.example.english4d.ui.theme.TypeText


@Composable
fun CardFlip(onFlip : ()-> Unit,  word: MyWord,isFont: Boolean){
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp),
        shape = RoundedCornerShape(5.dp),
        onClick = onFlip,
    )
    {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if(isFont){
                Text(text = word.english, style = TypeText.h6.copy(fontWeight = FontWeight.Medium))
            }else{
                Text(text = word.vietnamese, style = TypeText.h6.copy(fontWeight = FontWeight.Medium))
                Text(text = word.pronunciation?:"", style = TypeText.h7.copy(fontStyle = FontStyle.Italic))
            }
        }


    }
}