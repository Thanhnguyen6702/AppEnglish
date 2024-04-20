package com.example.english4d

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

class PreferencesManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    @RequiresApi(Build.VERSION_CODES.O)
    fun saveCurrentDate(){
        val today = LocalDate.now()
        sharedPreferences.edit().putString("date",today.toString()).apply()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun isNewDay(): Boolean{
        val saveDate = sharedPreferences.getString("date",null)
        val today = LocalDate.now()
        return saveDate == null || LocalDate.parse(saveDate).isBefore(today)
    }
    fun setUpdated(){
        sharedPreferences.edit().putBoolean("updated",true).apply()
    }
    fun checkUpdated():Boolean{
        return sharedPreferences.getBoolean("updated",false)
    }
}