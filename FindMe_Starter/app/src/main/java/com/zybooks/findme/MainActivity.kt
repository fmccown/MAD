package com.zybooks.findme

import android.os.Bundle
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
import com.google.android.gms.maps.MapsInitializer
import com.zybooks.findme.ui.CheckPermission
import com.zybooks.findme.ui.FindMeApp
import com.zybooks.findme.ui.theme.FindMeTheme

class MainActivity : ComponentActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)


      // Trying to get rid of:
      // java.util.concurrent.ExecutionException: java.lang.SecurityException: GoogleCertificatesRslt: not allowed:
      // https://stackoverflow.com/questions/64196715/securityexception-googlecertificatesrslt-not-allowed
      // The error occurs on the API 33 emunlator but not on the API 34 one
      //MapsInitializer.initialize(this, MapsInitializer.Renderer.LATEST) {
      //   println("MapsInitializer: $it")
      //}

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
