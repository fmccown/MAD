package com.zybooks.findme.ui

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

@SuppressLint("MissingPermission")
@Composable
fun FindMeApp() {
   val context = LocalContext.current
   val locationClient = remember {
      LocationServices.getFusedLocationProviderClient(context)
   }

   LaunchedEffect(Unit) {
      var currentLocation: Location?
      withContext(Dispatchers.IO) {
         currentLocation = locationClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            CancellationTokenSource().token
         ).await()
      }

      currentLocation?.let {
         println("Current location: lat=${it.latitude}, long=${it.longitude}")
      }
   }
}

@Composable
fun CheckPermission(
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
