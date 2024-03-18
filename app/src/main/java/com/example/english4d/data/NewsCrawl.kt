package com.example.english4d.data

import org.jsoup.Jsoup
import java.io.IOException

class NewsCrawl {
    suspend fun getItemNews(topic: String): List<NewsItem>? {
        try {
            // Kết nối đến trang web và parse HTML
            val doc = Jsoup.connect("https://e.vnexpress.net/news/${topic}").get()

            // Tìm thẻ div có id là "vnexpress_folder_list_news"
            val div = doc.selectFirst("div#vnexpress_folder_list_news")
            val contentList = mutableListOf<NewsItem>()

            // Duyệt qua tất cả các phần tử con của div
            div?.children()?.take(10)?.forEach { element ->
                // Kiểm tra nếu phần tử là thẻ div có class là "item_list_folder"
                if (element.tagName() == "div" && element.hasClass("item_list_folder")) {
                    val h2Element = element.selectFirst("h2.title_news_site")
                    val title = h2Element?.text() ?: ""
                    val href = h2Element?.selectFirst("a")?.attr("href") ?: ""

                    val thumbElement = element.selectFirst("div.thumb_size.thumb_left")
                    val image = thumbElement?.selectFirst("img")?.attr("src") ?: ""
                    if (title ==""||href==""||image=="") return@forEach
                    contentList.add(NewsItem(title = title, image = image, href = href))
                }

            }
            return contentList.toList()

        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    }
}