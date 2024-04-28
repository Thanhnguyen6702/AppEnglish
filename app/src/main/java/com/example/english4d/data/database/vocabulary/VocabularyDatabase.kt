package com.example.english4d.data.database.vocabulary

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [Vocabulary::class, Topics::class, Statistic::class, Theme::class,Definitions::class,Examples::class,Pronunciation::class], version = 2, exportSchema = false)
abstract class VocabularyDatabase : RoomDatabase(){
    abstract fun vocabularyDao(): VocabularyDao
    abstract fun statisticDao(): StatisticDao
    abstract fun topicDao(): TopicsDAO
    abstract fun themeDao(): ThemeDao
    abstract fun definitionsDao(): DefinitionsDao
    abstract fun examplesDao():ExamplesDao
    abstract fun pronunciationDao(): PronunciationDao
    companion object{
        @Volatile
        private var Instance: VocabularyDatabase? = null
        fun getDatabase(context: Context): VocabularyDatabase {
            return Instance ?: synchronized(this){
                Room.databaseBuilder(context, VocabularyDatabase::class.java,"Vocabulary_Database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}