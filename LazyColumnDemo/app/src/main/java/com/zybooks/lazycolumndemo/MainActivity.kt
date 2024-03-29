package com.zybooks.lazycolumndemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zybooks.lazycolumndemo.ui.theme.LazyColumnDemoTheme

class MainActivity : ComponentActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContent {
         LazyColumnDemoTheme {
            // A surface container using the 'background' color from the theme
            Surface(
               modifier = Modifier.fillMaxSize(),
               color = MaterialTheme.colorScheme.background
            ) {
               //LazyColumnDemo()
               LazyRowAndColumnDemo()
            }
         }
      }
   }
}

@Composable
fun LazyColumnDemo() {
   LazyColumn (
      horizontalAlignment = Alignment.CenterHorizontally
   ) {
      items(50) { index ->
         Text(
            text = "LazyColumn item ${index + 1}",
         )
      }
   }
}

@Preview
@Composable
fun LazyColumnDemo2(modifier: Modifier = Modifier) {
   val subjects =
      listOf("Physics", "History", "Literature", "Arithmetic", "Geography", "Physics")
   LazyColumn(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.SpaceEvenly
   ) {
      items(subjects) { subject ->
         Text(text = subject,
            fontSize = 100.sp)
      }
   }
}

@Preview
@Composable
fun LazyRowDemo() {
   LazyRow {
      item {
         Text(
            text = "Start",
            color = Color.White,
            modifier = Modifier
               .size(70.dp)
               .background(Color.Blue)
         )
      }
      items(6) { index ->
         Text(
            text = "Item ${index + 1}",
            color = Color.Black,
            modifier = Modifier
               .height(70.dp)
               .width(70.dp)
               .background(Color.Yellow)
         )
      }
      item {
         Text(
            text = "End",
            color = Color.White,
            modifier = Modifier
               .height(70.dp)
               .width(70.dp)
               .background(Color.Blue)
         )
      }
   }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun LazyRowAndColumnDemo() {
   Column {
      LazyRow (modifier = Modifier.padding(all = 10.dp)) {
         stickyHeader {
            Text(
               text = "Row Header",
               color = Color.White,
               modifier = Modifier.background(Color.Black)
            )
         }
         items(10) { index ->
            Text(
               text = "Row item ${index + 1}",
               color = Color.Black,
               modifier = Modifier.background(Color.Yellow)
            )
         }
      }
      LazyColumn (
         horizontalAlignment = Alignment.CenterHorizontally,
         modifier = Modifier.fillMaxWidth().height(300.dp)
      ) {
         stickyHeader {
            Text(
               text = "Column Header",
               color = Color.White,
               modifier = Modifier.background(Color.Black)
            )
         }
         items(40) { index ->
            Text(
               text = "Column item ${index + 1}",
               color = Color.White,
               modifier = Modifier.background(Color.Blue)
            )
         }
      }
   }

}