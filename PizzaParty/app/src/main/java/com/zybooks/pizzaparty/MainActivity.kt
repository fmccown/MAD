package com.zybooks.pizzaparty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zybooks.pizzaparty.ui.theme.PizzaPartyTheme

class MainActivity : ComponentActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContent {
         PizzaPartyTheme {
            Surface(
               modifier = Modifier.fillMaxSize(),
               color = MaterialTheme.colorScheme.background
            ) {
               PizzaPartyScreen()
            }
         }
      }
   }
}

@Composable
fun PizzaPartyScreen(modifier: Modifier = Modifier) {
   var totalPizzas by remember { mutableIntStateOf(0) }

   Column(
      modifier = modifier.padding(10.dp)
   ) {
      Text(
         text = "Pizza Party",
         fontSize = 38.sp,
         modifier = modifier.padding(bottom = 16.dp)
      )
      EditNumberField(
         labelText = "Number of people?",
         modifier = modifier.padding(bottom = 16.dp)
      )
      RadioButtonGroup(
         labelText = "How hungry?",
         radioOptions = listOf("Light", "Medium", "Ravenous"),
         selectedValue = "Medium",
         modifier = modifier
      )
      Text(
         text = "Total pizzas: $totalPizzas",
         fontSize = 22.sp,
         modifier = modifier.padding(top = 16.dp, bottom = 16.dp)
      )
      Button(
         onClick = { },
         modifier = modifier.fillMaxWidth()
      ) {
         Text("Calculate")
      }
   }
}

@Composable
fun EditNumberField(
   labelText: String,
   modifier: Modifier = Modifier
) {
   var textInput by remember { mutableStateOf("") }

   TextField(
      value = textInput,
      onValueChange = { textInput = it },
      label = { Text(labelText) },
      singleLine = true,
      keyboardOptions = KeyboardOptions(
         keyboardType = KeyboardType.Number
      ),
      modifier = modifier.fillMaxWidth()
   )
}

@Composable
fun RadioButtonGroup(
   labelText: String,
   radioOptions: List<String>,
   selectedValue: String,
   modifier: Modifier = Modifier
) {
   var selectedOption by remember { mutableStateOf(selectedValue) }
   val isSelectedOption: (String) -> Boolean = { selectedOption == it }

   Column {
      Text(labelText)
      radioOptions.forEach { option ->
         Row(
            modifier = modifier
               .selectable(
                  selected = isSelectedOption(option),
                  onClick = { selectedOption = option },
                  role = Role.RadioButton
               )
               .padding(8.dp)
         ) {
            RadioButton(
               selected = isSelectedOption(option),
               onClick = null,
               modifier = modifier.padding(end = 8.dp)
            )
            Text(
               text = option,
               modifier = modifier.fillMaxWidth()
            )
         }
      }
   }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
   PizzaPartyTheme {
      PizzaPartyScreen()
   }
}