package com.example.english4d.navigation

const val READNEWS_ARGUMENT_TOPIC = "topic"
const val READNEWS_ARGUMENT_HREF = "href"
const val NEWVOCAB_ARGUMENT = "id"
sealed class Screen(val route: String) {
    object Splash: Screen(route = "splash")
    object Home : Screen(route = "home")
    object NewsTopic : Screen(route = "news_topic")
    object ReadNews : Screen(route = "read_news?$READNEWS_ARGUMENT_TOPIC={topic}&$READNEWS_ARGUMENT_HREF={href}") {
        fun passData(
            topic: String,
            href: String
        ): String {
            return "read_news?topic=$topic&href=$href"
        }
    }
    object NewVocab: Screen(route = "new_vocab?$NEWVOCAB_ARGUMENT={id}"){
        fun passID(
            id:Int
        ):String{
            return "new_vocab?$NEWVOCAB_ARGUMENT=$id"
        }
    }
}