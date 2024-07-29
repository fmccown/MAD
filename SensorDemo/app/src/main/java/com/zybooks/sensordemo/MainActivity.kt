package com.zybooks.sensordemo

import android.app.Activity.SENSOR_SERVICE
import android.content.Context
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zybooks.sensordemo.ui.SensorApp
import com.zybooks.sensordemo.ui.SensorViewModel
import com.zybooks.sensordemo.ui.theme.SensorDemoTheme

class MainActivity : ComponentActivity() {
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

      listSensors(this)
      verifySensor(this)
   }

   private fun listSensors(context: Context) {
      val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
      val deviceSensors: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)

      for (sensor in deviceSensors) {
         println("Sensor: ${sensor.name} - ${sensor.type}")
      }
   }

   private fun verifySensor(context: Context) {
      val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
      val tempSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)

      if (tempSensor == null) {
         println("No ambient temperature sensor on this device")
      } else {
         println("Vendor: ${tempSensor.vendor}")
         println("Version: ${tempSensor.version}")
         println("Min delay: ${tempSensor.minDelay}")
      }
   }

   override fun onPause() {
      super.onPause()
   }

   override fun onResume() {
      super.onResume()
   }
}



