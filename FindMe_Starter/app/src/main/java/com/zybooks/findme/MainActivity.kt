package com.zybooks.findme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.zybooks.findme.ui.CheckPermission
import com.zybooks.findme.ui.FindMeApp
import com.zybooks.findme.ui.theme.FindMeTheme

class MainActivity : ComponentActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContent {
         FindMeTheme {
            Surface(
               modifier = Modifier.fillMaxSize(),
               color = MaterialTheme.colorScheme.background
            ) {
               CheckPermission(
                  permission = android.Manifest.permission.ACCESS_FINE_LOCATION,
                  onGranted = { FindMeApp() }
               )
            }
         }
      }
   }
}
