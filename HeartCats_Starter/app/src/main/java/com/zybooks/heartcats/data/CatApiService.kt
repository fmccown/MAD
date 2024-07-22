package com.zybooks.heartcats.data

import retrofit2.http.GET

interface CatApiService {
   @GET("search?limit=10")
   suspend fun getCats(): List<Cat>
}