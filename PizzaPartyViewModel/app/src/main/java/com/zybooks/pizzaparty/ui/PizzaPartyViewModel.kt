package com.zybooks.pizzaparty.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.zybooks.pizzaparty.HungerLevel
import com.zybooks.pizzaparty.PizzaCalculator

class PizzaPartyViewModel : ViewModel() {
   var numPeople by mutableStateOf("")

   var hungerLevel by mutableStateOf(HungerLevel.MEDIUM)

   var totalPizzas by mutableIntStateOf(0)
      private set

   fun calculateNumPizzas() {
      val calc = PizzaCalculator(numPeople.toIntOrNull() ?: 0, hungerLevel)
      totalPizzas = calc.totalPizzas
   }
}