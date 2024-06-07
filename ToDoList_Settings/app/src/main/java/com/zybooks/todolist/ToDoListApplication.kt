package com.zybooks.todolist

import android.app.Application
import android.content.Context

class ToDoListApplication: Application() {
   // Application context is needed to create the ViewModels with the
   // ViewModelProvider.Factory
   lateinit var appContext: Context

   // For onCreate() to run, android:name=".ToDoListApplication" must
   // be added to <application> in AndroidManifest.xml
   override fun onCreate() {
      super.onCreate()
      appContext = this.applicationContext
   }
}