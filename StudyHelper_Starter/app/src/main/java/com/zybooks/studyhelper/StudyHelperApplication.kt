package com.zybooks.studyhelper

import android.app.Application
import com.zybooks.studyhelper.data.StudyRepository

class StudyHelperApplication: Application() {
   // Needed to create ViewModels with the ViewModelProvider.Factory
   lateinit var studyRepository: StudyRepository

   // For onCreate() to run, android:name=".StudyHelperApplication" must
   // be added to <application> in AndroidManifest.xml
   override fun onCreate() {
      super.onCreate()
      studyRepository = StudyRepository(this.applicationContext)
   }
}