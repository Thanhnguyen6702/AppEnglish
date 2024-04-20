package com.example.english4d.data.database.vocabulary

import com.example.english4d.ui.topic.ItemTopic
import kotlinx.coroutines.flow.Flow

interface VocabularyRepository {
    // Get daily learning data
   fun getUnlearned(): Flow<List<Vocabulary>>
   fun getLearning(): Flow<List<Vocabulary>>
   fun getMaster(): Flow<List<Vocabulary>>

   //get review data
   suspend fun getRevise(): List<Vocabulary>
   suspend fun getVocabulary(): List<Vocabulary>
   //get new Vocabulary
   suspend fun getNewVocabulary(topicID: Int?): List<Vocabulary>
   suspend fun getCompletionRate(topicID: Int): CompletionRate

   suspend fun getTopic(topicID: Int): Topics
   suspend fun getDefinition(id: Int): List<Definitions>
   suspend fun getExample(id: Int): List<Examples>
   suspend fun getItemTopic(): List<ItemTopic>
   suspend fun insertStatistic(statistic: Statistic)
   suspend fun chooseAnswer(isCorrect: Int,id: Int)
   suspend fun getItemStatistic(id: Int): Statistic
   suspend fun insertExamples(examples: List<Examples>)
   suspend fun insertDefinitions(definitions: List<Definitions>)
   suspend fun insertVocab(vocabularies: List<Vocabulary>)
   suspend fun insertTopic(topics: List<Topics>)
   suspend fun insertTheme(themes: List<Theme>)

}
data class CompletionRate(
   val id: Int,
   val totalVocabulary: Int,
   val unlearnedVocabulary: Int
)
