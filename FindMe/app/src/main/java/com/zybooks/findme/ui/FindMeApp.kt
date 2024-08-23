package com.zybooks.findme.ui

import android.annotation.SuppressLint
import android.content.pm.PackageManager
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
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.tasks.await

val startingLatLong = LatLng(0.0, 0.0)
val defaultCameraPosition = CameraPosition.fromLatLngZoom(startingLatLong, 11f)

@SuppressLint("MissingPermission")
@Composable
fun FindMeApp() {
   val context = LocalContext.current
   val locationClient = remember {
      LocationServices.getFusedLocationProviderClient(context)
   }

   var currentLatLng by remember { mutableStateOf(startingLatLong) }
   val cameraPositionState = rememberCameraPositionState { defaultCameraPosition }

   GoogleMap(
      cameraPositionState = cameraPositionState,
      properties = MapProperties(mapType = MapType.NORMAL)
   ) {
      if (currentLatLng != startingLatLong) {
         Marker(
            state = MarkerState(currentLatLng),
            title = "Here You Are!"
         )
      }
   }

   LaunchedEffect(Unit) {
      val currentLocation = locationClient.getCurrentLocation(
         Priority.PRIORITY_HIGH_ACCURACY,
         CancellationTokenSource().token).await()

      if (currentLocation != null) {
         currentLatLng = LatLng(currentLocation.latitude, currentLocation.longitude)

         cameraPositionState.animate(
            update = CameraUpdateFactory.newLatLngZoom(
               LatLng(currentLatLng.latitude, currentLatLng.longitude),
               15f
            ),
            durationMs = 1000
         )
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
