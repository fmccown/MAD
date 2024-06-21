package com.zybooks.petadoption

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.zybooks.petadoption.ui.PetApp
import com.zybooks.petadoption.ui.theme.PetAdoptionTheme

class MainActivity : ComponentActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContent {
         PetAdoptionTheme {
            Surface(
               modifier = Modifier.fillMaxSize(),
               color = MaterialTheme.colorScheme.background
            ) {
               PetApp()
            }
         }
      }
   }
}
