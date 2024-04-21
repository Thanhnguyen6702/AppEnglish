package com.example.english4d

import android.app.Application
import com.example.english4d.data.AppContainer
import com.example.english4d.data.DataAppContainer

class DataApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DataAppContainer(this)
    }
}