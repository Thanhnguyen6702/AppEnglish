package com.example.english4d.data.database.vocabulary

import kotlinx.coroutines.flow.Flow

class OfflineVocabularyRepository(
    private val vocabularyDao: VocabularyDao,
    private val statisticDao: StatisticDao,
    private val topicsDAO: TopicsDAO,
    private val themeDao: ThemeDao,
    private val definitionsDao: DefinitionsDao,
    private val examplesDao: ExamplesDao
): VocabularyRepository {
    override fun getUnlearned(): Flow<List<Vocabulary>> = vocabularyDao.getUnlearned()

    override fun getLearning(): Flow<List<Vocabulary>> = vocabularyDao.getLearning()

    override fun getMaster(): Flow<List<Vocabulary>> = vocabularyDao.getMaster()

    override suspend fun getRevise(): List<Vocabulary> = vocabularyDao.getRevise()

    override suspend fun getNewVocabulary(topicID: Int?): List<Vocabulary> = vocabularyDao.getNewVocab(topicID)
    override suspend fun getTopic(topicID: Int): Topics = topicsDAO.getTopic(topicID)
    override suspend fun getDefinition(id: Int): List<Definitions> = definitionsDao.getDefinitions(id)
    override suspend fun getExample(id: Int): List<Examples>  = examplesDao.getExamples(id)

}