package com.zybooks.sensordemo.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.zybooks.sensordemo.ProximitySensor
import com.zybooks.sensordemo.SensorApplication

class SensorViewModel(private val proximitySensor: ProximitySensor) : ViewModel() {
   var proximity by mutableFloatStateOf(0f)

   companion object {
      val Factory: ViewModelProvider.Factory = viewModelFactory {
         initializer {
            val application = (this[APPLICATION_KEY] as SensorApplication)
            SensorViewModel(application.proximitySensor)
         }
      }
   }

   fun startListening() {
      proximitySensor.startListening { values ->
         proximity = values[0]
      }
   }

   fun stopListening() {
      proximitySensor.stopListening()
   }
}