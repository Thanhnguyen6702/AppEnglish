package com.example.english4d.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.english4d.data.database.vocabulary.VocabularyDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class VocabWorker(
    context: Context,
    params: WorkerParameters,
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            try {
                val database = VocabularyDatabase.getDatabase(applicationContext)
                val vocab = database.vocabularyDao().getRevise()
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
}