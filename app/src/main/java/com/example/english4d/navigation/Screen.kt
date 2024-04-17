package com.example.english4d.navigation

const val READNEWS_ARGUMENT_TOPIC = "topic"
const val READNEWS_ARGUMENT_HREF = "href"
const val NEWVOCAB_ARGUMENT = "id"
const val HOME_ARGUMENT ="id"
sealed class Screen(val route: String) {
    object Splash: Screen(route = "splash")
    object Home : Screen(route = "home/{$HOME_ARGUMENT}"){
        fun passData(id: Int): String{
            return "home/$id"
        }
    }
    object NewsTopic : Screen(route = "news_topic")
    object ReadNews : Screen(route = "read_news/{$READNEWS_ARGUMENT_TOPIC}/{$READNEWS_ARGUMENT_HREF}") {
        fun passData(
            topic: String,
            href: String
        ): String {
            return "read_news/$topic/$href"
        }
    }
    object NewVocab: Screen(route = "new_vocab/{$NEWVOCAB_ARGUMENT}"){
        fun passID(
            id:Int
        ):String{
            return "new_vocab/$id"
        }
    }
    object TopicsVocab: Screen(route = "topic_vocab")
}