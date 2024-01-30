package com.zybooks.pizzaparty.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zybooks.pizzaparty.HungerLevel
import com.zybooks.pizzaparty.ui.theme.PizzaPartyTheme

@Composable
fun PizzaPartyScreen(
   modifier: Modifier = Modifier,
   partyViewModel: PizzaPartyViewModel = viewModel()
) {
   val hungerItems = listOf("Light", "Medium", "Ravenous")

   Column(
      modifier = modifier.padding(10.dp)
   ) {
      Text(
         text = "Pizza Party",
         fontSize = 38.sp,
         modifier = modifier.padding(bottom = 16.dp)
      )
      NumberField(
         labelText = "Number of people?",
         textInput = partyViewModel.numPeople,
         onValueChange = { partyViewModel.numPeople = it },
         modifier = modifier.padding(bottom = 16.dp).fillMaxWidth()
      )
      RadioGroup(
         labelText = "How hungry?",
         radioOptions = hungerItems,
         selectedOption = when (partyViewModel.hungerLevel) {
            HungerLevel.LIGHT -> hungerItems[0]
            HungerLevel.MEDIUM -> hungerItems[1]
            else -> hungerItems[2]
         },
         onSelected = {
            partyViewModel.hungerLevel = when (it) {
               hungerItems[0] -> HungerLevel.LIGHT
               hungerItems[1] -> HungerLevel.MEDIUM
               else -> HungerLevel.RAVENOUS
            }
         },
         modifier = modifier
      )
      Text(
         text = "Total pizzas: " + partyViewModel.totalPizzas,
         fontSize = 22.sp,
         modifier = modifier.padding(top = 16.dp, bottom = 16.dp)
      )
      Button(
         onClick = { partyViewModel.calculateNumPizzas() },
         modifier = modifier.fillMaxWidth()
      ) {
         Text("Calculate")
      }
   }
}

@Composable
fun NumberField(
   labelText: String,
   textInput: String,
   onValueChange: (String) -> Unit,
   modifier: Modifier = Modifier
) {
   TextField(
      value = textInput,
      onValueChange = onValueChange,
      label = { Text(labelText) },
      singleLine = true,
      keyboardOptions = KeyboardOptions(
         keyboardType = KeyboardType.Number
      ),
      colors = TextFieldDefaults.colors(
         focusedContainerColor = Color(0xFFF2F2F2F2),
         unfocusedContainerColor = Color(0xFFF2F2F2F2),
         disabledContainerColor = Color.White,
      ),
      modifier = modifier
   )
}

@Composable
fun RadioGroup(
   labelText: String,
   radioOptions: List<String>,
   selectedOption: String,
   onSelected: (String) -> Unit,
   modifier: Modifier = Modifier
) {
   val isSelectedOption: (String) -> Boolean = { selectedOption == it }

   Column {
      Text(labelText)
      radioOptions.forEach { option ->
         Row(
            modifier = modifier
               .selectable(
                  selected = isSelectedOption(option),
                  onClick = { onSelected(option) },
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