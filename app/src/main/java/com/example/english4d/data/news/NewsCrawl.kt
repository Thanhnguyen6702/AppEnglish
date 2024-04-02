package com.example.english4d.data.news

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
                    if (title == "" || href == "" || image == "") return@forEach
                    contentList.add(NewsItem(title = title, image = image, href = href))
                }

            }
            return contentList.toList()

        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    }

    suspend fun getContent(url: String): List<NewsContent> {
        try {
            // Connect to data
            val doc = Jsoup.connect(url).get()
            // Thẻ chứa thông tin cần crawl
            val image = doc.selectFirst(".thumb_detail_top img")?.attr("src")?:""
            val div = doc.selectFirst("div.fck_detail")
            val contentList = mutableListOf<NewsContent>()
            contentList.add(NewsContent("image",image))
            // Duyệt qua tất cả các phần tử con của div
            div?.children()?.forEach { element ->
                // Kiểm tra nếu phần tử là thẻ p có class là "nomal"
                if (element.tagName() == "p" && element.hasClass("Normal")) {
                    contentList.add(NewsContent("text",element.text()))
                } else if (element.tagName() == "table") {
                    // Nếu là một bảng, lặp qua các ảnh trong bảng và thêm vào danh sách
                    element.select("td img").forEach { imageElement ->
                        contentList.add(NewsContent("image", imageElement.attr("src")))
                    }
                }
            }
            return contentList.toList()
        } catch (e: IOException) {
            e.printStackTrace()
            return listOf(NewsContent("",""))
        }
    }
}