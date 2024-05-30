package com.zybooks.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.zybooks.todolist.ui.ToDoScreen
import com.zybooks.todolist.ui.theme.ToDoListTheme

class MainActivity : ComponentActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContent {
         ToDoListTheme {
            Surface(
               modifier = Modifier.fillMaxSize(),
               color = MaterialTheme.colorScheme.background
            ) {
               ToDoScreen()
            }
         }
      }
   }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
   ToDoListTheme {
      ToDoScreen()
   }
}