package com.example.english4d.data.database.question

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Article::class,Question::class], version = 0, exportSchema = false)
abstract class QuestionDatabase: RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun questionDao(): QuestionDao
    companion object{
        @Volatile
        private var Instance: QuestionDatabase? = null
        fun getDatabase(context: Context):QuestionDatabase{
            return Instance?: synchronized(this){
                Room.databaseBuilder(context,QuestionDatabase::class.java,"Question_Database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}