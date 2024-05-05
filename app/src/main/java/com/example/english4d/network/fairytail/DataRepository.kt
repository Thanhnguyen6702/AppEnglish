package com.example.english4d.network.fairytail

interface FairyTailRepository{
    suspend fun getFairyTail(): List<FairyTail>
}
class NetworkFairyTailRepository(private val apiFairyTail: ApiFairyTail): FairyTailRepository{
    override suspend fun getFairyTail() = apiFairyTail.getFairyTail()
}