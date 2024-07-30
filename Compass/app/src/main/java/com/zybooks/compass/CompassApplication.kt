package com.zybooks.compass

import android.app.Application
import com.zybooks.compass.sensor.AccelerometerSensor
import com.zybooks.compass.sensor.MagnetometerSensor

class CompassApplication : Application() {
   lateinit var accelerometerSensor: AccelerometerSensor
   lateinit var magnetometerSensor: MagnetometerSensor

   // For onCreate() to run, android:name=".CompassApplication" must
   // be added to <application> in AndroidManifest.xml
   override fun onCreate() {
      super.onCreate()
      accelerometerSensor = AccelerometerSensor(this)
      magnetometerSensor = MagnetometerSensor(this)
   }
}