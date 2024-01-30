package com.zybooks.lifecycle

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text

private const val TAG = "LifecycleDemo"

class MainActivity : ComponentActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)

      Log.d(TAG, "onCreate")

      setContent {
         Text("Lifecycle demo")
      }
   }

   override fun onStart() {
      super.onStart()
      Log.d(TAG, "onStart")
   }

   override fun onStop() {
      super.onStop()
      Log.d(TAG, "onStop")
   }

   override fun onDestroy() {
      super.onDestroy()
      Log.d(TAG, "onDestroy")
   }

   override fun onPause() {
      super.onPause()
      Log.d(TAG, "onPause")
   }

   override fun onResume() {
      super.onResume()
      Log.d(TAG, "onResume")
   }
}