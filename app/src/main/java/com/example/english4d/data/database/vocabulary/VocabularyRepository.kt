package com.example.english4d.data.database.vocabulary

import kotlinx.coroutines.flow.Flow

interface VocabularyRepository {
    // Get daily learning data
   fun getUnlearned(): Flow<List<Vocabulary>>
   fun getLearning(): Flow<List<Vocabulary>>
   fun getMaster(): Flow<List<Vocabulary>>

   //get review data
   suspend fun getRevise(): List<Vocabulary>
   //get new Vocabulary
   suspend fun getNewVocabulary(): List<Vocabulary>
}

