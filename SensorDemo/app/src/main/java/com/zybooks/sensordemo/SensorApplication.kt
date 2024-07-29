package com.zybooks.sensordemo

import android.app.Application

class SensorApplication: Application() {
   lateinit var proximitySensor: ProximitySensor

   // For onCreate() to run, android:name=".SensorApplication" must
   // be added to <application> in AndroidManifest.xml
   override fun onCreate() {
      super.onCreate()
      proximitySensor = ProximitySensor(this)
   }
}