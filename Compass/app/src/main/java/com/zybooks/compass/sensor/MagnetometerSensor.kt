package com.zybooks.compass.sensor

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor

class MagnetometerSensor(context: Context) : AndroidSensor(
   context = context,
   sensorFeature = PackageManager.FEATURE_SENSOR_COMPASS,
   sensorType = Sensor.TYPE_MAGNETIC_FIELD
)