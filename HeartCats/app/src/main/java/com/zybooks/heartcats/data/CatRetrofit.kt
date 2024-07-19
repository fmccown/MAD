package com.zybooks.heartcats.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

class CatRetrofit {
   private val baseUrl = "https://api.thecatapi.com/v1/images/"

   val catsApiService: CatApiService by lazy {
      val retrofit: Retrofit = Retrofit.Builder()
         .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
         .baseUrl(baseUrl)
         .build()
      retrofit.create(CatApiService::class.java)
   }
}