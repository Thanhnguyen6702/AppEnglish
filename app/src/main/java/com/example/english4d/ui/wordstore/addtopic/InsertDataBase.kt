package com.example.english4d.ui.wordstore.addtopic


import com.example.english4d.data.database.wordstore.DictionaryResponse
import com.example.english4d.data.database.wordstore.MyWord
import com.example.english4d.data.database.wordstore.MyWordAntonym
import com.example.english4d.data.database.wordstore.MyWordDao
import com.example.english4d.data.database.wordstore.MyWordDefinition
import com.example.english4d.data.database.wordstore.MyWordExample

suspend fun insertMyWordDatabase(myWordDao: MyWordDao, dictionaryMyWord: DictionaryResponse, topic_id:Long){
    val word = MyWord(
        english = dictionaryMyWord.response?:"",
        pronunciation = dictionaryMyWord.data?.phonetic,
        topic_id = topic_id
    )
    val wordId = myWordDao.insertMyWord(word)

    // Insert Definitions
    dictionaryMyWord.data?.definitions?.forEach { definition ->
        val definitionEntity = MyWordDefinition(
            myword_id = wordId,
            part_of_speech = definition.partOfSpeech?:"",
            definition_en = definition.definitionEN?:"",
            definition_vi = definition.definitionVI?:""
        )
        val definitionId = myWordDao.insertMyWordDefinition(definitionEntity)

        // Insert Examples

            val exampleEntity = MyWordExample(
                definition_id = definitionId,
                example_en = definition.examples?.exampleEN ?:"",
                example_vi = definition.examples?.exampleVI?:""
            )
            myWordDao.insertMyWordExample(exampleEntity)

    }

    dictionaryMyWord.data?.antonyms?.forEach { item ->
            val antonym = MyWord(
                english = item.word?:"",
                pronunciation = item.phonetic?:"",
                topic_id = null
            )
            val antonymId = myWordDao.insertMyWord(antonym)
            item.definitions?.forEach { definition ->
                val definitionAntonym = MyWordDefinition(
                    myword_id = antonymId,
                    part_of_speech = definition.partOfSpeech?:"",
                    definition_en = definition.definitionEN?:"",
                    definition_vi = definition.definitionVI?:"")
                val definitionId = myWordDao.insertMyWordDefinition(definitionAntonym)
                val exampleAntonym = MyWordExample(
                    definition_id = definitionId,
                    example_en = definition.examples?.exampleEN ?:"",
                    example_vi = definition.examples?.exampleVI?:""
                )
                myWordDao.insertMyWordExample(exampleAntonym)
        }
        myWordDao.insertMyWordAntonym(MyWordAntonym(wordId, antonymId))
    }
    }
