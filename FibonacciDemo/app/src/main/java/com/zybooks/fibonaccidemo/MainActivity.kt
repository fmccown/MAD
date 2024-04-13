package com.zybooks.fibonaccidemo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zybooks.fibonaccidemo.ui.theme.FibonacciDemoTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

class MainActivity : ComponentActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContent {
         FibonacciDemoTheme {
            Surface(
               modifier = Modifier.fillMaxSize(),
               color = MaterialTheme.colorScheme.background
            ) {
               HelloDemo()
            }
         }
      }
   }
}

@Composable
fun FibonacciDemoWithCoroutine() {
   var answer by remember { mutableStateOf("") }
   var textInput by remember { mutableStateOf("40") }
   val coroutineScope = rememberCoroutineScope()

   Column {
      Row {
         TextField(
            value = textInput,
            onValueChange = { textInput = it },
            label = { Text("Number?") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
               keyboardType = KeyboardType.Number
            )
         )
         Button(onClick = {
            answer = ""
            val num = textInput.toLongOrNull() ?: 0

            // Start coroutine on a background thread
            coroutineScope.launch(Dispatchers.Default) {
               val fibNumber = fibonacci(num)
               answer = NumberFormat.getNumberInstance(Locale.US).format(fibNumber)
            }
         }) {
            Text("Fibonacci")
         }
      }

      Text("Result: $answer")
   }
}

suspend fun fibonacciSuspend(n: Long): Long {
   delay(10)
   return if (n <= 1) n else fibonacciSuspend(n - 1) +
         fibonacciSuspend(n - 2)
}

@Composable
fun FibonacciDemoForAnimation() {
   var answer by remember { mutableStateOf("") }
   var fibonacciJob: Job by remember { mutableStateOf(Job()) }
   val coroutineScope = rememberCoroutineScope()

   Column {
      Button(onClick = {
         answer = ""
         fibonacciJob = coroutineScope.launch(Dispatchers.Default) {
            val fibNumber = fibonacciSuspend(20)
            answer = NumberFormat.getNumberInstance(Locale.US).format(fibNumber)
         }
      }) {
         Text("Fibonacci")
      }
      Button(onClick = { fibonacciJob.cancel() }) {
         Text("Cancel")
      }
      Text("Result: $answer")
   }
}

@Composable
fun FibonacciDemoWithCancelCoroutine() {
   var answer by remember { mutableStateOf("") }
   var textInput by remember { mutableStateOf("40") }
   var fibonacciJob: Job by remember { mutableStateOf(Job()) }
   val coroutineScope = rememberCoroutineScope()

   Column {
      Row {
         TextField(
            value = textInput,
            onValueChange = { textInput = it },
            label = { Text("Number?") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
               keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.width(200.dp)
         )
         Button(onClick = {
            answer = ""
            val num = textInput.toLongOrNull() ?: 0

            // Start coroutine on a background thread
            fibonacciJob = coroutineScope.launch(Dispatchers.Default) {
               val fibNumber = fibonacciSuspend(num)
               answer = NumberFormat.getNumberInstance(Locale.US).format(fibNumber)
            }
         }) {
            Text("Fibonacci")
         }
         Button(onClick = {
            fibonacciJob.cancel()
         }) {
            Text("Cancel")
         }
      }

      Text("Result: $answer")
   }
}

@Composable
fun FibonacciDemo() {
   var answer by remember { mutableStateOf("") }
   var textInput by remember { mutableStateOf("40") }

   Column {
      Row {
         TextField(
            value = textInput,
            onValueChange = { textInput = it },
            label = { Text("Number?") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
               keyboardType = KeyboardType.Number
            )
         )
         Button(onClick = {
            val num = textInput.toLongOrNull() ?: 0

            // Run time-intensive operation on the main thread
            val fibNumber = fibonacci(num)
            answer = NumberFormat.getNumberInstance(Locale.US).format(fibNumber)
         }) {
            Text("Fibonacci")
         }
      }

      Text("Result: $answer")
   }
}

fun fibonacci(n: Long): Long {
   return if (n <= 1) n else fibonacci(n - 1) + fibonacci(n - 2)
}

@Composable
fun HelloDemo(modifier: Modifier = Modifier) {
   var message by remember { mutableStateOf("Hello Android!") }
   val coroutineScope = rememberCoroutineScope()

   Column {
      Text(
         text = message,
         modifier = modifier
      )
      Button(onClick = {
         coroutineScope.launch {
            message = getMessage()
         }
      }) {
         Text("Click Me")
      }
   }
}

suspend fun getMessage(): String {
   delay(2000L)
   return "Secret"
}

@Composable
fun DispatchersDemo() {
   val coroutineScope = rememberCoroutineScope()

   Button(onClick = {
      coroutineScope.launch {
         Log.d("Demo", "Coroutine 1: ${Thread.currentThread().name}")
      }
      coroutineScope.launch(Dispatchers.Default) {
         Log.d("Demo", "Coroutine 2: ${Thread.currentThread().name}")
      }
      coroutineScope.launch(Dispatchers.IO) {
         Log.d("Demo", "Coroutine 3: ${Thread.currentThread().name}")
      }
   }) {
      Text("Click Me")
   }
}

@Preview(showBackground = true)
@Composable
fun FibonacciDemoPreview() {
   FibonacciDemoTheme {
      FibonacciDemo()
   }
}
