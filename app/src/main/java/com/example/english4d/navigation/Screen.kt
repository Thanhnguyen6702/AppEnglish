package com.example.english4d.navigation

const val READNEWS_ARGUMENT_TOPIC = "topic"
const val READNEWS_ARGUMENT_HREF = "href"
const val NEWVOCAB_ARGUMENT = "id"
const val HOME_ARGUMENT = "id"
const val WORDSBOOK_ARGUMENT = "index"
const val VIDEO_ARGUMENT = "video_id"
sealed class Screen(val route: String) {
    object Splash : Screen(route = "splash")
    object Home : Screen(route = "home/{$HOME_ARGUMENT}") {
        fun passData(id: Int): String {
            return "home/$id"
        }
    }

    object NewsTopic : Screen(route = "news_topic")
    object ReadNews :
        Screen(route = "read_news/{$READNEWS_ARGUMENT_TOPIC}/{$READNEWS_ARGUMENT_HREF}") {
        fun passData(
            topic: String,
            href: String
        ): String {
            return "read_news/$topic/$href"
        }
    }

    object NewVocab : Screen(route = "new_vocab/{$NEWVOCAB_ARGUMENT}") {
        fun passID(
            id: Int
        ): String {
            return "new_vocab/$id"
        }
    }

    object TopicsVocab : Screen(route = "topic_vocab")
    object ReviseVocab : Screen(route = "revise_vocab")
    object FinishVocab : Screen(route = "finish_vocab")
    object WordsBook : Screen(route = "words_book/{$WORDSBOOK_ARGUMENT}") {
        fun passIndex(index: Int): String {
            return "words_book/$index"
        }
    }
    object Main: Screen(route = "main")
    object Pronunciation: Screen(route = "pronunciation")
    object Video: Screen(route = "video/{$VIDEO_ARGUMENT}"){
        fun passId(videoId: String): String{
            return "video/$videoId"
        }
    }
    object Channel: Screen(route = "channel")
    object FairyTopic: Screen(route = "fairy_topic")
    object ReadFairy: Screen(route = "read_fairy")
    object SeeMoreVideo: Screen(route = "see_more_video")
    object SeeMoreNews: Screen(route = "see_more_news")
}