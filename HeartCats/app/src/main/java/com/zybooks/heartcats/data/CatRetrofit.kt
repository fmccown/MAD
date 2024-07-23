package com.zybooks.heartcats.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CatRetrofit {
   private val baseUrl = "https://api.thecatapi.com/v1/images/"

   val catsApiService: CatApiService by lazy {
      val retrofit: Retrofit = Retrofit.Builder()
         .addConverterFactory(GsonConverterFactory.create())
         .baseUrl(baseUrl)
         .build()
      retrofit.create(CatApiService::class.java)
   }
}