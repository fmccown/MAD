package com.zybooks.heartcats

import android.app.Application
import com.zybooks.heartcats.data.CatApiService
import com.zybooks.heartcats.data.CatRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CatsApplication: Application() {
   lateinit var catsRepository: CatRepository

   // For onCreate() to run, android:name=".CatsApplication" must
   // be added to <application> in AndroidManifest.xml
   override fun onCreate() {
      super.onCreate()

      val catsApiService: CatApiService by lazy {
         val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.thecatapi.com/v1/images/")
            .build()
         retrofit.create(CatApiService::class.java)
      }

      catsRepository = CatRepository(catsApiService)
   }
}