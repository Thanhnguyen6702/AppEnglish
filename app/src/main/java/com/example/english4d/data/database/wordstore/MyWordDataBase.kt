package com.example.english4d.data.database.wordstore

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MyWordTopic::class, MyWord::class, MyWordDefinition::class, MyWordExample::class, MyWordAntonym::class], version = 1)
abstract class MyWordDataBase: RoomDatabase() {

    abstract fun myWordDao(): MyWordDao

    companion object {
        @Volatile
        private var INSTANCE: MyWordDataBase? = null
        fun getDatabase(context: Context): MyWordDataBase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    MyWordDataBase::class.java,
                    "my_word_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}