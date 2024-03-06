package com.zybooks.griddemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zybooks.griddemo.ui.theme.GridDemoTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContent {
         GridDemoTheme {
            Surface(
               modifier = Modifier.fillMaxSize(),
               color = MaterialTheme.colorScheme.background
            ) {
               VerticalGrid()
               //DemoHorizontalGrid()
               //DemoVerticalStaggeredGrid()
               //DemoHorizontalStaggeredGrid()
            }
         }
      }
   }
}

@Preview
@Composable
fun LazyVerticalGridPreview() {
   GridDemoTheme {
      VerticalGrid2()
   }
}

@Preview
@Composable
fun VerticalGrid() {
   LazyVerticalGrid(
      columns = GridCells.Adaptive(100.dp),
      //columns = GridCells.Fixed(5),
      //columns = GridCells.FixedSize(150.dp),
      //modifier = Modifier.width(350.dp)
   ) {
      items(90) { item ->
         Card(
            modifier = Modifier.height(100.dp).padding(4.dp)
         ) {
            Text("Item $item", Modifier.padding(6.dp))
         }
      }
   }
}

@Composable
fun VerticalGrid2() {
   LazyVerticalGrid(
      columns = GridCells.Adaptive(100.dp),
      contentPadding = PaddingValues(10.dp, 5.dp),
      horizontalArrangement = Arrangement.spacedBy(20.dp),
      verticalArrangement = Arrangement.spacedBy(15.dp),
      //modifier = Modifier.width(350.dp)
   ) {
      items(9) { item ->
         Text(
            text = "Item $item",
            color = Color.White,
            modifier = Modifier
               .height(30.dp).background(Color.Blue))
      }
   }
}

@Preview
@Composable
fun VerticalGrid3() {
   val emojiList = listOf("👟", "🏀", "🧸️", "👻", "🐼", "🧲", "🎸", "😁", "❄️")

   LazyVerticalGrid(
      columns = GridCells.Fixed(3),
      contentPadding = PaddingValues(all = 20.dp),
      horizontalArrangement = Arrangement.spacedBy(10.dp),
      verticalArrangement = Arrangement.spacedBy(40.dp)
   ) {
      items(emojiList) { item ->
         Box(
            modifier = Modifier.background(Color(
               red = Random.nextInt(0, 255),
               green = Random.nextInt(0, 255),
               blue = Random.nextInt(0, 255)
            )),
            contentAlignment = Alignment.Center,
         )
         {
            Text(
               text = item,
               fontSize = 50.sp,
               modifier = Modifier.padding(24.dp)
            )
         }
      }
   }
}

@Preview
@Composable
fun DemoHorizontalGrid() {
   Row {
      LazyHorizontalGrid(
         rows = GridCells.Adaptive(minSize = 100.dp),
         //rows = GridCells.Fixed(4),
         //rows = GridCells.FixedSize(100.dp)
         modifier = Modifier.height(310.dp)
      ) {
         items(5) { item ->
            Card(
               modifier = Modifier
                  //.size(100.dp)
                  .padding(4.dp)
            ) {
               Text(
                  text = "Item $item",
                  modifier = Modifier.padding(6.dp)
               )
            }
         }
      }
   }
}

@Preview
@Composable
fun DemoVerticalStaggeredGrid() {
   val numItems = 30
   val sizes = IntArray(numItems) { Random.nextInt(80, 220) }

   LazyVerticalStaggeredGrid(
      columns = StaggeredGridCells.Adaptive(150.dp),
      //columns = StaggeredGridCells.Fixed(3),
      //columns = StaggeredGridCells.FixedSize(100.dp),
      //verticalItemSpacing = 4.dp,
      //horizontalArrangement = Arrangement.spacedBy(4.dp),
      modifier = Modifier.width(350.dp),
      content = {
         items(numItems) { item ->
            Card(
               modifier = Modifier
                  .padding(4.dp)
                  .size(sizes[item].dp)
            ) {
               Text(
                  text = "Item $item",
                  modifier = Modifier.padding(6.dp)
               )
            }
         }
      }
   )
}

@Preview
@Composable
fun DemoHorizontalStaggeredGrid() {
   val numItems = 30
   val sizes = IntArray(numItems) { Random.nextInt(80, 220) }

   LazyHorizontalStaggeredGrid(
      rows = StaggeredGridCells.Adaptive(100.dp),
      //verticalItemSpacing = 4.dp,
      //horizontalArrangement = Arrangement.spacedBy(4.dp),
      modifier = Modifier.height(300.dp),
      content = {
         items(numItems) { item ->
            Card(
               modifier = Modifier
                  .padding(4.dp)
                  .size(sizes[item].dp)
            ) {
               Text(
                  text = "Item $item",
                  modifier = Modifier.padding(6.dp)
               )
            }
         }
      }
   )
}