package com.example.english4d.data.database.vocabulary

import com.example.english4d.ui.topic.ItemTopic
import kotlinx.coroutines.flow.Flow

class OfflineVocabularyRepository(
    private val vocabularyDao: VocabularyDao,
    private val statisticDao: StatisticDao,
    private val topicsDAO: TopicsDAO,
    private val themeDao: ThemeDao,
    private val definitionsDao: DefinitionsDao,
    private val examplesDao: ExamplesDao,
    private val pronunciationDao: PronunciationDao
): VocabularyRepository {
    override fun getUnlearned(): Flow<List<Vocabulary>> = vocabularyDao.getUnlearned()

    override fun getLearning(): Flow<List<Vocabulary>> = vocabularyDao.getLearning()

    override fun getMaster(): Flow<List<Vocabulary>> = vocabularyDao.getMaster()

    override fun getRevise(): Flow<List<Vocabulary>> = vocabularyDao.getRevise()
    override suspend fun getVocabulary(): List<Vocabulary> = vocabularyDao.getVocabulary()
    override suspend fun getNewVocabulary(topicID: Int?): List<Vocabulary> = vocabularyDao.getNewVocab(topicID)
    override suspend fun getTopic(topicID: Int): Topics = topicsDAO.getTopic(topicID)
    override suspend fun getDefinition(id: Int): List<Definitions> = definitionsDao.getDefinitions(id)
    override suspend fun getExample(id: Int): List<Examples>  = examplesDao.getExamples(id)
    override suspend fun getItemTopic(): List<ItemTopic> = topicsDAO.getItemTopic()
    override suspend fun getCompletionRate(topicID: Int): CompletionRate = vocabularyDao.getCompletionRate(topicID)
    override suspend fun insertStatistic(statistic: Statistic) = statisticDao.insertStatistic(statistic)
    override suspend fun chooseAnswer(isCorrect: Int,id: Int)  = statisticDao.chooseAnswer(isCorrect,id)
    override suspend fun getItemStatistic(id: Int) = statisticDao.getItemStatistic(id)
    override suspend fun insertExamples(examples: List<Examples>) =examplesDao.insertExamples(examples)

    override suspend fun insertDefinitions(definitions: List<Definitions>) = definitionsDao.insertDefinitions(definitions)

    override suspend fun insertVocab(vocabularies: List<Vocabulary>) = vocabularyDao.insertVocab(vocabularies)

    override suspend fun insertTopic(topics: List<Topics>) = topicsDAO.insertTopic(topics)

    override suspend fun insertTheme(themes: List<Theme>) = themeDao.insertTheme(themes)
    override suspend fun updateIsStudyAndCheckDay() = statisticDao.updateIsStudyAndCheckDay()
    override suspend fun insertPronunciation(pronunciation: Pronunciation)  = pronunciationDao.insertPronunciation(pronunciation)
    override suspend fun getPronunciation(): Flow<List<PronunciationWithVocabulary>> = pronunciationDao.getPronunciation()
    override suspend fun getVocabWithoutPronunciation(): List<Vocabulary> = vocabularyDao.getVocabWithoutPronunciation()
}