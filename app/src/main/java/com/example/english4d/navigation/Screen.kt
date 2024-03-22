package com.example.english4d.navigation

const val READNEWS_ARGUMENT_TOPIC = "topic"
const val READNEWA_ARGUMENT_HREF = "href"

sealed class Screen(val route: String) {
    object Home : Screen(route = "home")
    object NewsTopic : Screen(route = "news_topic")
    object ReadNews : Screen(route = "read_news?topic={topic}&href={href}") {
        fun passData(
            topic: String,
            href: String
        ): String {
            return "read_news?topic=$topic&href=$href"
        }
    }
}