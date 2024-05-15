package com.example.english4d.data

import android.content.Context
import com.example.english4d.data.database.question.OfflineQuestionRepository
import com.example.english4d.data.database.question.QuestionDatabase
import com.example.english4d.data.database.question.QuestionRepository
import com.example.english4d.data.database.vocabulary.OfflineVocabularyRepository
import com.example.english4d.data.database.vocabulary.VocabularyDatabase
import com.example.english4d.data.database.vocabulary.VocabularyRepository
import com.example.english4d.data.news.NetworkNewsRepository
import com.example.english4d.data.news.NewsCrawl
import com.example.english4d.data.news.NewsRepository
import com.example.english4d.data.workers.VocabWorkerRepository
import com.example.english4d.data.workers.WorkerManagerRepository

interface AppContainer {
    val newsRepository: NewsRepository
    val vocabularyRepository: VocabularyRepository
    val questionRepository: QuestionRepository
    val workerRepository: VocabWorkerRepository
//    val captionTrackRepository: CaptionTrackRepository
}

class DataAppContainer(context: Context): AppContainer{
    private val newsCrawl = NewsCrawl()
    override val newsRepository: NewsRepository by lazy {
        NetworkNewsRepository(newsCrawl)
    }
    private val vocabDatabase = VocabularyDatabase.getDatabase(context)
    override val vocabularyRepository: OfflineVocabularyRepository by lazy {
        OfflineVocabularyRepository(
            vocabularyDao = vocabDatabase.vocabularyDao(),
            statisticDao = vocabDatabase.statisticDao(),
            topicsDAO = vocabDatabase.topicDao(),
            themeDao = vocabDatabase.themeDao(),
            definitionsDao = vocabDatabase.definitionsDao(),
            examplesDao = vocabDatabase.examplesDao(),
            pronunciationDao = vocabDatabase.pronunciationDao()
        )
    }
    private val questionDatabase = QuestionDatabase.getDatabase(context)
    override val questionRepository: QuestionRepository by lazy {
        OfflineQuestionRepository(
            articleDao = questionDatabase.articleDao(),
            questionDao = questionDatabase.questionDao()
        )
    }
    override val workerRepository: WorkerManagerRepository = WorkerManagerRepository(context)
    // override val captionTrackRepository: CaptionTrackRepository = OnlineCaptionTrackRepository().captionTrackRepository

}