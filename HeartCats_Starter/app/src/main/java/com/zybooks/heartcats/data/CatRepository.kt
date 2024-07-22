package com.zybooks.heartcats.data

class CatRepository(private val catsApiService: CatApiService) {
   suspend fun getCats(): List<Cat> = catsApiService.getCats()
}