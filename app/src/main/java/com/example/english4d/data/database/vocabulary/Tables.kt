package com.example.english4d.data.database.vocabulary

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(
    tableName = "Vocabulary",
    foreignKeys = [ForeignKey(
        entity = Topics::class,
        parentColumns = ["id"],
        childColumns = ["id_topic"],
        onDelete = ForeignKey.CASCADE

    )]
)
data class Vocabulary(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val english: String,
    val vietnamese: String,
    val pronunciation: String,
    val image: String,
    val id_topic: Int,
)

@Entity(
    tableName = "Statistic",
    foreignKeys = [ForeignKey(
        entity = Vocabulary::class,
        parentColumns = ["id"],
        childColumns = ["id_vocab"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Statistic(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val unlearned: Int = 0,
    val learning: Int = 0,
    val master: Int = 0,
    val id_vocab: Int,
    val check_day: Int = 3211,
    val isStudy: Int = 0
)

@Entity(
    tableName = "Topics",
    foreignKeys = [ForeignKey(
        entity = Theme::class,
        parentColumns = ["id"],
        childColumns = ["id_theme"],
        onDelete = ForeignKey.CASCADE
    )]
)
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
    val id: Int,
    val title: String
)

@Entity(
    tableName = "Definitions",
    foreignKeys = [ForeignKey(
        entity = Vocabulary::class,
        parentColumns = ["id"],
        childColumns = ["id_vocab"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Definitions(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val id_vocab: Int,
    val definition: String,
    val partofspeech: String
)

@Entity(
    tableName = "Examples",
    foreignKeys = [ForeignKey(
        entity = Vocabulary::class,
        parentColumns = ["id"],
        childColumns = ["id_vocab"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Examples(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val id_vocab: Int,
    val example: String
)

@Entity(
    tableName = "Pronunciation",
    foreignKeys = [ForeignKey(
        entity = Vocabulary::class,
        parentColumns = ["id"],
        childColumns = ["id_vocab"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Pronunciation(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val id_vocab: Int,
    val score: Int
)
data class PronunciationWithVocabulary(
    @Embedded val pronunciation: Pronunciation,
    @Relation(
        parentColumn = "id_vocab",
        entityColumn = "id",
        entity = Vocabulary::class
    )
    val vocabulary: Vocabulary
)