package com.example.english4d

import android.content.Context
import android.content.SharedPreferences
import java.time.LocalDate

class PreferencesManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    fun saveCurrentDate(){
        val today = LocalDate.now()
        sharedPreferences.edit().putString("date",today.toString()).apply()
    }
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