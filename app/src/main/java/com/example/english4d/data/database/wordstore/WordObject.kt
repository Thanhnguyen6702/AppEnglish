package com.example.english4d.data.database.wordstore

import com.google.gson.annotations.SerializedName

data class DictionaryResponse(
    @SerializedName("entry") val entry: String?,
    @SerializedName("response") var response: String?,
    @SerializedName("vietnamese") val vietnamese: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("data") val data: DictionaryData?
)

data class DictionaryData(
    @SerializedName("phonetic") val phonetic: String?,
    @SerializedName("definitions") val definitions: List<Definition>?,
    @SerializedName("antonyms") val antonyms: List<Antonym>?
)

data class Definition(
    @SerializedName("partOfSpeech") val partOfSpeech: String?,
    @SerializedName("definition_en") val definitionEN: String?,
    @SerializedName("definition_vi") val definitionVI: String?,
    @SerializedName("example") val examples: Example?
)

data class Example(
    @SerializedName("example_en") val exampleEN: String?,
    @SerializedName("example_vi") val exampleVI: String?
)

data class Antonym(
    @SerializedName("word") val word: String?,
    @SerializedName("phonetic") val phonetic: String?,
    @SerializedName("definitions") val definitions: List<Definition>?
)