package com.example.english4d

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.english4d.navigation.graphs.RootNavGraph

const val PERMISSION_REQUEST_CODE = 123

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            RootNavGraph()
        }
    }
}
