package com.zybooks.heartcats.data

class CatRepository(private val catsApiService: CatApiService) {
   suspend fun getCatImages(): List<CatImage> = catsApiService.getCatImages()
}