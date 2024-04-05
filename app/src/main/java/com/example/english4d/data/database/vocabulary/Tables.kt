package com.example.english4d.data.database.vocabulary

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "Vocabulary",
    foreignKeys = [ForeignKey(
        entity = Topics::class,
        parentColumns = ["id"],
        childColumns =  ["id_topic"],
        onDelete = ForeignKey.CASCADE

    )])
data class Vocabulary (
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val english: String,
    val vietnamese: String,
    val pronunciation: String,
    val image: String,
    val id_topic: Int
)

@Entity(tableName = "Statistic",
    foreignKeys = [ForeignKey(
        entity = Vocabulary::class,
        parentColumns = ["id"],
        childColumns = ["id_vocab"],
        onDelete = ForeignKey.CASCADE
    )])
data class Statistic(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val unlearned: Int = 0 ,
    val learning: Int = 0 ,
    val master: Int = 0,
    val id_vocab: Int
)
@Entity(tableName = "Topic",
    foreignKeys = [ForeignKey(
        entity = Theme::class,
        parentColumns = ["id"],
        childColumns = ["id_theme"],
        onDelete = ForeignKey.CASCADE
    )])
data class Topics(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val topic: String,
    val image: String,
    val id_theme: Int
)

@Entity(tableName = "Theme")
data class Theme(
    @PrimaryKey(autoGenerate = false)
    val id: Int ,
    val title: String
)