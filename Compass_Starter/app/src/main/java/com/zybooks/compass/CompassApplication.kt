package com.zybooks.compass

import android.app.Application

class CompassApplication : Application() {

   // For onCreate() to run, android:name=".CompassApplication" must
   // be added to <application> in AndroidManifest.xml
   override fun onCreate() {
      super.onCreate()
   }
}