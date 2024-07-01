package com.example.english4d.data.database.wordstore

import kotlinx.coroutines.flow.Flow

class OfflineMyWordRepository(
    val myWordDao: MyWordDao
) : MyWordRepository {
    override suspend fun insertTopical(topic: MyWordTopic): Long = myWordDao.insertTopic(topic)

    override fun getTopics(): Flow<List<MyWordTopic>> = myWordDao.getTopics()

    override suspend fun insertMyWord(word: MyWord): Long = myWordDao.insertMyWord(word)
    override suspend fun getMyWordDetail(id: Long): MyWordWithDetails = myWordDao.getMyWordDetail(id)


    override suspend fun insertMyWordDefinition(detail: MyWordDefinition): Long = myWordDao.insertMyWordDefinition(detail)

    override suspend fun insertMyWordExample(example: MyWordExample): Long = myWordDao.insertMyWordExample(example)

    override suspend fun insertMyWordAntonym(antonym: MyWordAntonym) = myWordDao.insertMyWordAntonym(antonym)

}