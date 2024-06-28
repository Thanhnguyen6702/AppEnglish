package com.example.english4d.di

import android.app.Application
import com.example.english4d.di.AppContainer
import com.example.english4d.di.DataAppContainer

class DataApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DataAppContainer(this)
    }
}