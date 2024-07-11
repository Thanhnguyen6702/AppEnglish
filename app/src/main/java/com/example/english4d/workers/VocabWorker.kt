package com.example.english4d.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.english4d.data.database.wordstore.MyWord
import com.example.english4d.data.database.wordstore.MyWordDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import kotlin.random.Random

class VocabWorker(
    context: Context,
    params: WorkerParameters,
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val database = MyWordDataBase.getDatabase(applicationContext)
            val topics = database.myWordDao().getTopics().first()
            var vocab: MutableList<MyWord> = mutableListOf()
            val topicIsSelected = topics.filter { it.is_study%10 == 1 }
            topics.forEach {
                val study = if (it.is_study % 10 == 1) it.is_study / 10 else it.is_study - 1
                database.myWordDao().updateTopicStudy(it.id, study)
            }
            topicIsSelected.forEach { topic ->
                vocab.addAll(database.myWordDao().getTopic(topic.id).words)
            }
            if (vocab.isNotEmpty()) {
                val random = Random.nextInt(vocab.size)
                val noti =
                    "${vocab[random].english} ${vocab[random].pronunciation}\n${vocab[random].vietnamese}"
                makeStatusNotification(noti, applicationContext)
            }
            Result.success()
        } catch (throwable: Throwable) {
            Result.failure()
        }
    }
}
