package com.zybooks.sharedatademo

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.compose.foundation.layout.Column
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
      Button(onClick = { shareMessage(context) }) {
         Text("Share URL")
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
   }
}

private fun viewWebpage(context: Context) {
   val webpage = Uri.parse("http://www.zybooks.com/")
   val intent = Intent(Intent.ACTION_VIEW, webpage)
   context.startActivity(intent)
}

private fun dialNumber(context: Context) {
   val phoneNumber = Uri.parse("tel:111-222-3333")
   val intent = Intent(Intent.ACTION_DIAL, phoneNumber)
   context.startActivity(intent)
}

private fun viewMap(context: Context) {
   val location = Uri.parse("geo:0,0?q=1600+Pennsylvania+Ave+NW,+Washington,+DC")
   val intent = Intent(Intent.ACTION_VIEW, location)

   // Hard-coded to only open in Google Maps
   //intent.setPackage("com.google.android.apps.maps")
   context.startActivity(intent)
}

private fun shareMessage(context: Context) {
   val intent = Intent(Intent.ACTION_SEND).apply {
      type = "text/plain"
      putExtra(Intent.EXTRA_SUBJECT, "Helpful website")
      putExtra(Intent.EXTRA_TEXT, "https://stackoverflow.com/")
   }

   context.startActivity(
      Intent.createChooser(intent,"Pet Adoption")
   )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
   ShareDataDemoTheme {
      ShareDataScreen()
   }
}