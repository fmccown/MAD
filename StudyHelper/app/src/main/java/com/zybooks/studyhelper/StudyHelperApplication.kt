package com.zybooks.studyhelper

import android.app.Application
import android.content.Context

class StudyHelperApplication: Application() {
   // Application context is needed to create the ViewModels with the
   // ViewModelProvider.Factory
   lateinit var appContext: Context

   // For onCreate() to run, android:name=".StudyHelperApplication" must
   // be added to <application> in AndroidManifest.xml
   override fun onCreate() {
      super.onCreate()
      appContext = this.applicationContext
   }
}