package com.zybooks.locationdemo

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.zybooks.locationdemo.ui.theme.LocationDemoTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainActivity : ComponentActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContent {
         LocationDemoTheme {
            Surface(
               modifier = Modifier.fillMaxSize(),
               color = MaterialTheme.colorScheme.background
            ) {
               PermissionDemo(
                  permission = Manifest.permission.ACCESS_FINE_LOCATION,
                  onGranted = {
                     CurrentLocation()
                     //LocationDemo()
                  }
               )
            }
         }
      }
   }
}

@SuppressLint("MissingPermission")
@Composable
fun LocationDemo() {
   val context = LocalContext.current
   val locationClient = remember {
      LocationServices.getFusedLocationProviderClient(context)
   }

   LaunchedEffect(Unit) {
      val lastLocation = locationClient.lastLocation.await()
      if (lastLocation != null) {
         println("Last known location " +
               "lat: ${lastLocation.latitude} " +
               "long: ${lastLocation.longitude}")
      }

      val currentLocation = locationClient.getCurrentLocation(
         Priority.PRIORITY_HIGH_ACCURACY,
         CancellationTokenSource().token).await()
      if (currentLocation != null) {
         println("Current location " +
               "lat: ${currentLocation.latitude} " +
               "long: ${currentLocation.longitude}")
      }
   }
}

@Composable
fun PermissionDemo(
   permission: String,
   onGranted: @Composable () -> Unit,
) {
   val context = LocalContext.current
   var hasPermission by remember { mutableStateOf(false) }

   val permissionLauncher = rememberLauncherForActivityResult(
      ActivityResultContracts.RequestPermission()
   ) { isGranted ->
      hasPermission = isGranted
   }

   LaunchedEffect(Unit) {
      if (ContextCompat.checkSelfPermission(context, permission)
               == PackageManager.PERMISSION_GRANTED) {
         hasPermission = true
      }
      else {
         permissionLauncher.launch(permission)
      }
   }

   if (hasPermission) {
      onGranted()
   }
}

@SuppressLint("MissingPermission")
@Composable
fun CurrentLocation() {
   var locationInfo by remember { mutableStateOf("") }
   val coroutineScope = rememberCoroutineScope()
   val context = LocalContext.current
   val locationClient = remember {
      LocationServices.getFusedLocationProviderClient(context)
   }

   Column(
      modifier = Modifier
         .fillMaxSize()
         .padding(10.dp),
      verticalArrangement = Arrangement.spacedBy(8.dp)
   ) {
      Button(
         onClick = {
            locationInfo = "Fetching..."
            coroutineScope.launch(Dispatchers.IO) {
               val location = locationClient.lastLocation.await()
               locationInfo = if (location == null) {
                  "No last known location."
               } else {
                  "Last known location is\n" +
                        "lat : ${location.latitude}\n" +
                        "long : ${location.longitude}"
               }
            }
         },
      ) {
         Text("Get last known location")
      }
      Button(
         onClick = {
            locationInfo = "Fetching..."
            coroutineScope.launch(Dispatchers.IO) {
               val location = locationClient.getCurrentLocation(
                  Priority.PRIORITY_HIGH_ACCURACY,
                  CancellationTokenSource().token).await()
               locationInfo = if (location == null) {
                  "Can't get location."
               } else {
                  "Current location is\n" +
                        "lat : ${location.latitude}\n" +
                        "long : ${location.longitude}"
               }
            }
         },
      ) {
         Text("Get current location")
      }
      Text(locationInfo)
   }
}
