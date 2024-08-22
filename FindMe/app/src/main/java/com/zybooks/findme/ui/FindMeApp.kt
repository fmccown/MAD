package com.zybooks.findme.ui

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
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
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import kotlinx.coroutines.tasks.await

val singapore = LatLng(1.3588227, 103.8742114)

val defaultCameraPosition = CameraPosition.fromLatLngZoom(singapore, 11f)

@SuppressLint("MissingPermission")
@Composable
fun FindMeApp() {
   val context = LocalContext.current
   val locationClient = remember {
      LocationServices.getFusedLocationProviderClient(context)
   }



   //var currentLocation by remember { mutableStateOf<Location?>(null) }

   var cameraPositionState = rememberCameraPositionState { defaultCameraPosition }

   var mapProperties by remember {
      mutableStateOf(MapProperties(mapType = MapType.NORMAL))
   }
   var uiSettings by remember { mutableStateOf(MapUiSettings(compassEnabled = false)) }


   GoogleMap(
      cameraPositionState = cameraPositionState,
      properties = mapProperties,
      uiSettings = uiSettings,
      onPOIClick = {
         Log.d("FindMeApp", "POI clicked: ${it.name}")
      },
   ) {

   }

   /*
   LaunchedEffect(Unit) {
      val currentLocation = locationClient.getCurrentLocation(
         Priority.PRIORITY_HIGH_ACCURACY,
         CancellationTokenSource().token).await()

      if (currentLocation != null) {
         println("Current location " +
               "lat: ${currentLocation.latitude} " +
               "long: ${currentLocation.longitude}")

         cameraPositionState.move(
            CameraUpdateFactory.newLatLng(
               LatLng(currentLocation.latitude, currentLocation.longitude)
            )
         )
      }
   }*/
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
