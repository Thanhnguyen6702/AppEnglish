package com.example.english4d.data.database.wordstore

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "myword",
    foreignKeys = [ForeignKey(
        entity = MyWordTopic::class,
        parentColumns = ["id"],
        childColumns = ["topic_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["english"], unique = true)]
    )
data class MyWord(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val english: String,
    val pronunciation: String?,
    val topic_id: Long?
)
@Entity(tableName = "myword_topic", indices = [Index(value = ["name"], unique = true)])
data class MyWordTopic(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String
)

@Entity(
    tableName = "myword_definition",
    foreignKeys = [ForeignKey(
        entity = MyWord::class,
        parentColumns = ["id"],
        childColumns = ["myword_id"],
        onDelete = ForeignKey.CASCADE
    )],
)
data class MyWordDefinition(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val myword_id: Long,
    val part_of_speech: String,
    val definition_en: String,
    val definition_vi: String
)

@Entity(
    tableName = "myword_example",
    foreignKeys = [ForeignKey(
        entity = MyWordDefinition::class,
        parentColumns = ["id"],
        childColumns = ["definition_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class MyWordExample(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val definition_id: Long,
    val example_en: String,
    val example_vi: String
)

@Entity(
    tableName = "myword_antonym",
    primaryKeys = ["vocab1_id", "vocab2_id"],
    foreignKeys = [
        ForeignKey(
            entity = MyWord::class,
            parentColumns = ["id"],
            childColumns = ["vocab1_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = MyWord::class,
            parentColumns = ["id"],
            childColumns = ["vocab2_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MyWordAntonym(
    val vocab1_id: Long,
    val vocab2_id: Long
)


data class TopicWithWords(
    @Embedded val topic: MyWordTopic,
    @Relation(
        parentColumn = "id",
        entityColumn = "topic_id"
    )
    val words: List<MyWord>
)

data class MyWordWithDetails(
    @Embedded val myword: MyWord,
    @Relation(
        parentColumn = "id",
        entityColumn = "vocab1_id",
        associateBy = Junction(MyWordAntonym::class, parentColumn = "vocab2_id", entityColumn = "vocab1_id")
    )
    val antonyms: List<MyWordAntonym>,
    @Relation(
        parentColumn = "id",
        entityColumn = "myword_id"
    )
    val definitions: List<MyWordDefinition>?,
    @Relation(
        parentColumn = "id",
        entityColumn = "definition_id"
    )
    val examples: List<MyWordExample>?
)
