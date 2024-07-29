package com.zybooks.sensordemo

import android.app.Activity.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.zybooks.sensordemo.ui.SensorApp
import com.zybooks.sensordemo.ui.theme.SensorDemoTheme

class MainActivity : ComponentActivity() {
   private lateinit var sensorManager: SensorManager

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContent {
         SensorDemoTheme {
            Surface(
               modifier = Modifier.fillMaxSize(),
               color = MaterialTheme.colorScheme.background
            ) {
               SensorApp()
            }
         }
      }

      sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

      listSensors(sensorManager)
      verifySensor(sensorManager)
   }
}

fun listSensors(sensorManager: SensorManager) {
   val deviceSensors: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)

   for (sensor in deviceSensors) {
      println("Sensor: ${sensor.name} - ${sensor.type}")
   }
}

fun verifySensor(sensorManager: SensorManager) {
   val tempSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)

   if (tempSensor == null) {
      println("No ambient temperature sensor on this device")
   } else {
      println("Vendor: ${tempSensor.vendor}")
      println("Version: ${tempSensor.version}")
      println("Min delay: ${tempSensor.minDelay}")
   }
}
