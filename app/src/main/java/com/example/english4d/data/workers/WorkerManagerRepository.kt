package com.example.english4d.data.workers

import android.content.Context
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.english4d.workers.VocabWorker
import java.util.concurrent.TimeUnit

class WorkerManagerRepository(context: Context) :
    VocabWorkerRepository {
    private val workerManager = WorkManager.getInstance(context)
    override fun applyVocab() {
            val vocabBuilder = PeriodicWorkRequestBuilder<VocabWorker>(2,TimeUnit.HOURS)
            workerManager.enqueue(vocabBuilder.build())
    }

    override fun cancelWork() {
        TODO("Not yet implemented")
    }

}