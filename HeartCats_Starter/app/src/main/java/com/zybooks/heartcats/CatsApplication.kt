package com.zybooks.heartcats

import android.app.Application
import com.zybooks.heartcats.data.CatRepository

class CatsApplication: Application() {
   lateinit var catsRepository: CatRepository

   // For onCreate() to run, android:name=".CatsApplication" must
   // be added to <application> in AndroidManifest.xml
   override fun onCreate() {
      super.onCreate()
      catsRepository = CatRepository()
   }
}