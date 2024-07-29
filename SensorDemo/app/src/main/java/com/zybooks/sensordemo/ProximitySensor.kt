package com.zybooks.sensordemo

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor

class ProximitySensor(context: Context) : AndroidSensor(
   context = context,
   sensorFeature = PackageManager.FEATURE_SENSOR_PROXIMITY,
   sensorType = Sensor.TYPE_PROXIMITY
)