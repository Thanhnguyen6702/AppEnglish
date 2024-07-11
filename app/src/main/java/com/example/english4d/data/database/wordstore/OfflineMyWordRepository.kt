package com.example.english4d.data.database.wordstore

import kotlinx.coroutines.flow.Flow

class OfflineMyWordRepository(
    val myWordDao: MyWordDao
) : MyWordRepository {
    override suspend fun insertTopic(topic: MyWordTopic): Long = myWordDao.insertTopic(topic)
    override suspend fun deleteTopic(id: Long) = myWordDao.deleteTopic(id)
    override suspend fun renameTopic(id: Long, name: String) = myWordDao.updateTopic(id, name)

    override fun getTopics(): Flow<List<MyWordTopic>> = myWordDao.getTopics()
    override suspend fun getTopicByNames(names: String): MyWordTopic? = myWordDao.getTopicByName(names)

    override suspend fun getTopic(id: Long): TopicWithWords = myWordDao.getTopic(id)
    override suspend fun setTopicStudy(id: Long, study: Int) = myWordDao.updateTopicStudy(id = id, study = study)

    override suspend fun insertMyWord(word: MyWord): Long = myWordDao.insertMyWord(word)
    override suspend fun isMyWordExist(english: String): Int = myWordDao.isWordExist(english)

    override suspend fun deleteMyWord(id: Long)  = myWordDao.deleteMyWord(id)

    override suspend fun getMyWordDetail(id: Long): MyWordWithDetails = myWordDao.getMyWordDetail(id)


    override suspend fun insertMyWordDefinition(detail: MyWordDefinition): Long = myWordDao.insertMyWordDefinition(detail)

    override suspend fun insertMyWordExample(example: MyWordExample): Long = myWordDao.insertMyWordExample(example)

    override suspend fun insertMyWordAntonym(antonym: MyWordAntonym) = myWordDao.insertMyWordAntonym(antonym)

}