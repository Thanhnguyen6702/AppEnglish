package com.example.english4d

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            val asyncDatabase = AsyncDatabase(LocalContext.current)
//            asyncDatabase.startAsyncDatabase()
             LayoutEnglish()
        }

    }
}



