package com.example.english4d.navigation

const val READNEWS_ARGUMENT_TOPIC = "topic"
const val READNEWS_ARGUMENT_HREF = "href"
const val NEWVOCAB_ARGUMENT = "id"
const val HOME_ARGUMENT = "id"
const val STATISTIC_NEWS_ARGUMENT = "href"
const val WORDSBOOK_ARGUMENT = "index"
const val VIDEO_ARGUMENT = "video_id"
object Routes {
    const val RootGraph = "rootGraph"
    const val MainGraph = "mainGraph"
    const val SplashGraph = "splashGraph"
    const val HomeGraph = "homeGraph"
    const val PronunciationGraph = "pronunciationGraph"
    const val ExtensionGraph = "extensionGraph"
}
sealed class HomeGraphScreen(val route: String){
    object Home : HomeGraphScreen(route = "home/{$HOME_ARGUMENT}") {
        fun passData(id: Int): String {
            return "home/$id"
        }
    }
    object NewVocab : HomeGraphScreen(route = "new_vocab/{$NEWVOCAB_ARGUMENT}") {
        fun passID(
            id: Int
        ): String {
            return "new_vocab/$id"
        }
    }
    object TopicsVocab : HomeGraphScreen(route = "topic_vocab")
    object ReviseVocab : HomeGraphScreen(route = "revise_vocab")
    object FinishVocab : HomeGraphScreen(route = "finish_vocab")
    object WordsBook : HomeGraphScreen(route = "words_book/{$WORDSBOOK_ARGUMENT}") {
        fun passIndex(index: Int): String {
            return "words_book/$index"
        }
    }
}

sealed class PronunciationGraphScreen(val route: String){
    object Pronunciation: PronunciationGraphScreen(route = "pronunciation")
    object StatisticPronunciation: PronunciationGraphScreen(route = "statistic_pronunciation")
}
sealed class ExtensionGraphScreen(val route: String){
    object Extension: ExtensionGraphScreen("extension")
    object NewsTopic : ExtensionGraphScreen(route = "news_topic")
    object ReadNews :
        ExtensionGraphScreen(route = "read_news/{$READNEWS_ARGUMENT_TOPIC}/{$READNEWS_ARGUMENT_HREF}") {
        fun passData(
            topic: String,
            href: String
        ): String {
            return "read_news/$topic/$href"
        }
    }
    object QuestionStatistic: ExtensionGraphScreen(route = "question_statistic/{$STATISTIC_NEWS_ARGUMENT}"){
        fun passHref(href: String): String{
            return "question_statistic/$href"
        }
    }
    object Video: ExtensionGraphScreen(route = "video/{$VIDEO_ARGUMENT}"){
        fun passId(videoId: String): String{
            return "video/$videoId"
        }
    }
    object Channel: ExtensionGraphScreen(route = "channel")
    object FairyTopic: ExtensionGraphScreen(route = "fairy_topic")
    object ReadFairy: ExtensionGraphScreen(route = "read_fairy")
    object SeeMoreVideo: ExtensionGraphScreen(route = "see_more_video")
    object SeeMoreNews: ExtensionGraphScreen(route = "see_more_news")
}
sealed class SplashNavScreen(val route: String){
    object Splash : SplashNavScreen(route = "splash")
}
