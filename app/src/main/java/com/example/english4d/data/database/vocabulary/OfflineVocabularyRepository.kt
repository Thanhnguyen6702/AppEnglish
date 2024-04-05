package com.example.english4d.data.database.vocabulary

import kotlinx.coroutines.flow.Flow

class OfflineVocabularyRepository(
    private val vocabularyDao: VocabularyDao,
    private val statisticDao: StatisticDao,
    private val topicsDAO: TopicsDAO,
    private val themeDao: ThemeDao
): VocabularyRepository {
    override fun getUnlearned(): Flow<List<Vocabulary>> = vocabularyDao.getUnlearned()

    override fun getLearning(): Flow<List<Vocabulary>> = vocabularyDao.getLearning()

    override fun getMaster(): Flow<List<Vocabulary>> = vocabularyDao.getMaster()

    override suspend fun getRevise(): List<Vocabulary> = vocabularyDao.getRevise()

    override suspend fun getNewVocabulary(): List<Vocabulary> {
        TODO("Not yet implemented")
    }
}