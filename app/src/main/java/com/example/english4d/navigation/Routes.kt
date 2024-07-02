package com.example.english4d.navigation

const val READNEWS_ARGUMENT_TOPIC = "topic"
const val READNEWS_ARGUMENT_HREF = "href"
const val NEWVOCAB_ARGUMENT = "id"
const val HOME_ARGUMENT = "id"
const val STATISTIC_NEWS_ARGUMENT = "href"
const val WORDSBOOK_ARGUMENT = "index"
const val VIDEO_ID_ARGUMENT = "video_id"
const val VIDEO_URL_ARGUMENT = "image_url"
const val VIDEO_TITLE_ARGUMENT = "video_title"
const val VIDEO_MODE = "video_mode"
const val MYWORD_ARGUMENT = "id"

object Routes {
    const val RootGraph = "rootGraph"
    const val MainGraph = "mainGraph"
    const val SplashGraph = "splashGraph"
    const val HomeGraph = "homeGraph"
    const val PronunciationGraph = "pronunciationGraph"
    const val ExtensionGraph = "extensionGraph"
}

sealed class HomeGraphScreen(val route: String) {
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

sealed class PronunciationGraphScreen(val route: String) {
    object Pronunciation : PronunciationGraphScreen(route = "pronunciation")
    object StatisticPronunciation : PronunciationGraphScreen(route = "statistic_pronunciation")
    object DetailStatisticPronunciation :
        PronunciationGraphScreen(route = "detail_statistic_pronunciation")
}

sealed class ExtensionGraphScreen(val route: String) {
    object Extension : ExtensionGraphScreen("extension")
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

    object QuestionStatistic :
        ExtensionGraphScreen(route = "question_statistic/{$STATISTIC_NEWS_ARGUMENT}") {
        fun passHref(href: String): String {
            return "question_statistic/$href"
        }
    }

    object Video : ExtensionGraphScreen(route = "video/{$VIDEO_ID_ARGUMENT}/{$VIDEO_MODE}") {
        fun passId(videoId: String, mode: Int): String {
            return "video/$videoId/$mode"
        }
    }

    object Channel : ExtensionGraphScreen(route = "channel")
    object FairyTopic : ExtensionGraphScreen(route = "fairy_topic")
    object ReadFairy : ExtensionGraphScreen(route = "read_fairy")
    object SeeMoreVideo : ExtensionGraphScreen(route = "see_more_video")
    object VideoMode :
        ExtensionGraphScreen(route = "video_mode/{$VIDEO_ID_ARGUMENT}/{$VIDEO_TITLE_ARGUMENT}/{$VIDEO_URL_ARGUMENT}") {
        fun passData(
            videoId: String,
            videoTitle: String,
            urlImage: String
        ): String {
            return "video_mode/$videoId/$videoTitle/$urlImage"
        }
    }

    object SeeMoreNews : ExtensionGraphScreen(route = "see_more_news")
    object  WordStore : ExtensionGraphScreen(route = "word_store")
    object  AddWord : ExtensionGraphScreen(route = "add_word/{$MYWORD_ARGUMENT}"){
        fun passId(id: Long): String {
            return "add_word/$id"
        }
    }
    object DetailWord : ExtensionGraphScreen(route = "detail_word")
}


sealed class SplashNavScreen(val route: String) {
    object Splash : SplashNavScreen(route = "splash")
}
