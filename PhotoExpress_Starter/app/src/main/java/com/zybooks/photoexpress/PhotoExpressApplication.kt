package com.zybooks.photoexpress

import android.app.Application
import com.zybooks.photoexpress.data.ImageRepository

class PhotoExpressApplication : Application() {
   lateinit var imageRepo: ImageRepository

   // For onCreate() to run, android:name=".PhotoExpressApplication" must
   // be added to <application> in AndroidManifest.xml
   override fun onCreate() {
      super.onCreate()
      imageRepo = ImageRepository(this.applicationContext)
   }
}