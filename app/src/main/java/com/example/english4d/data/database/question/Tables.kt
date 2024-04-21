package com.example.english4d.data.database.question

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "Question",
    foreignKeys = [
        ForeignKey(
            entity = Article::class,
            parentColumns = ["id"],
            childColumns = ["id_article"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Question(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val question: String,
    val options: Map<String, String>, // use MapConverter
    val answer: String,
    val explanation: String,
    val id_article: Long
)

@Entity(tableName = "Article")
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val href: String,
    val type: String
)

data class ArticleWithQuestion(
    @Embedded
    val article: Article,
    @Relation(
        parentColumn = "id",
        entityColumn = "id_article",
        entity = Question::class
    )
    val questions: List<Question>
)