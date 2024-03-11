package com.zybooks.sharedatademo

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.zybooks.sharedatademo.ui.theme.ShareDataDemoTheme

class MainActivity : ComponentActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContent {
         ShareDataDemoTheme {
            // A surface container using the 'background' color from the theme
            Surface(
               modifier = Modifier.fillMaxSize(),
               color = MaterialTheme.colorScheme.background
            ) {
               ShareDataScreen()
            }
         }
      }
   }
}

@Composable
fun ShareDataScreen(modifier: Modifier = Modifier) {
   val context = LocalContext.current

   Column {
      Button(onClick = {
         val webpageUri = Uri.parse("https://www.zybooks.com/")
         val intent = Intent(Intent.ACTION_VIEW, webpageUri)
         context.startActivity(intent)
      }) {
         Text("View Webpage")
      }
      Button(onClick = { viewMap(context) }) {
         Text("View Map")
      }
      Button(onClick = { dialNumber(context) }) {
         Text("Dial Number")
      }
      Button(onClick = { viewWebpage(context) }) {
         Text("View Webpage")
      }
      Button(onClick = { noActivityFound(context) }) {
         Text("No Activity Found")
      }
      Button(onClick = { shareMessage(context) }) {
         Text("Share Message")
      }
   }
}

fun viewWebpage(context: Context) {
   val webpage = Uri.parse("https://www.zybooks.com/")
   val intent = Intent(Intent.ACTION_VIEW, webpage)
   context.startActivity(intent)
}

fun dialNumber(context: Context) {
   val phoneNumber = Uri.parse("tel:111-222-3333")
   val intent = Intent(Intent.ACTION_DIAL, phoneNumber)
   context.startActivity(intent)
}

fun noActivityFound(context: Context) {
   try {
      val location = Uri.parse("geo:0,0?q=1600+Pennsylvania+Ave+NW,+Washington,+DC")

      // No activity is likely registered for the send action with geo URI
      val intent = Intent(Intent.ACTION_SEND, location)
      context.startActivity(intent)
   }
   catch (ex: ActivityNotFoundException) {
      Log.d("TAG", "No activity found")
   }
}

fun viewMap(context: Context) {
   val location = Uri.parse("geo:0,0?q=1600+Pennsylvania+Ave+NW,+Washington,+DC")
   val intent = Intent(Intent.ACTION_SEND, location)

   // Hard-coded to only open in Google Maps
   //intent.setPackage("com.google.android.apps.maps")
   context.startActivity(intent)
}

fun shareMessage(context: Context) {
   val intent = Intent(Intent.ACTION_SEND).apply {
      type = "text/plain"
      putExtra(Intent.EXTRA_SUBJECT, "Helpful website")
      putExtra(Intent.EXTRA_TEXT, "https://stackoverflow.com/")
   }

   val chooserIntent = Intent.createChooser(intent,"Share URL")
   context.startActivity(chooserIntent)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
   ShareDataDemoTheme {
      ShareDataScreen()
   }
}