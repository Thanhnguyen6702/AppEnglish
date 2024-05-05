package com.example.english4d.network.fairytail

import retrofit2.http.GET

interface ApiFairyTail {
    @GET("getFairyTail.php")
    suspend fun getFairyTail(): List<FairyTail>
}