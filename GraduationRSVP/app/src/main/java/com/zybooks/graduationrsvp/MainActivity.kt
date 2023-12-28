package com.zybooks.graduationrsvp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zybooks.graduationrsvp.ui.theme.GraduationRSVPTheme

class MainActivity : ComponentActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContent {
         GraduationRSVPTheme {
            // A surface container using the 'background' color from the theme
            Surface(
               modifier = Modifier.fillMaxSize(),
               color = MaterialTheme.colorScheme.background
            ) {
               GradScreen()
            }
         }
      }
   }
}

@Composable
fun GradScreen(modifier: Modifier = Modifier) {
   Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.SpaceEvenly,
      modifier = modifier.fillMaxHeight()
   ) {
      Text(
         text = stringResource(R.string.graduation_announcement),
         fontSize = 50.sp,
         color = Color.Red,
         textAlign = TextAlign.Center,
         fontWeight = FontWeight.Bold,
         lineHeight = 50.sp,
         modifier = modifier
      )
      Image(
         painter = painterResource(R.drawable.grad_cap),
         contentDescription = null,
         alpha = 0.3F
      )
      Row(verticalAlignment = Alignment.CenterVertically) {
         Icon(
            imageVector = Icons.Filled.Info,
            contentDescription = "Information",
            tint = Color.Gray,
            modifier = Modifier.padding(10.dp).size(40.dp)
         )
         Text(
            text = "May 14 - Ganus Hall - 2:00 pm",
            fontSize = 20.sp
         )
      }
   }
}

@Preview(showBackground = true)
@Composable
fun GradPreview() {
   GraduationRSVPTheme {
      GradScreen()
   }
}

