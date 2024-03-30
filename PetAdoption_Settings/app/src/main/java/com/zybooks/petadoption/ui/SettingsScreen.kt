package com.zybooks.petadoption.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zybooks.petadoption.data.MAX_AGE

@Composable
fun SettingsScreen(
   modifier: Modifier = Modifier,
   settingsViewModel: SettingsViewModel = viewModel(
      factory = SettingsViewModel.Factory
   )
) {
   val uiState = settingsViewModel.uiState.collectAsState().value

   Column {
      SettingCheck(
         caption = "Include dogs",
         isChecked = uiState.dogsChecked,
         onClick = settingsViewModel::includeDogs
      )
      SettingCheck(
         caption = "Include cats",
         isChecked = uiState.catsChecked,
         onClick = settingsViewModel::includeCats
      )
      SettingCheck(
         caption = "Include others",
         isChecked = uiState.othersChecked,
         onClick = settingsViewModel::includeOthers
      )
      SettingSlider(
         caption = "Maximum age",
         min = 1,
         max = MAX_AGE,
         currentValue = uiState.maxAge,
         onValueChange = settingsViewModel::setMaxAge
      )
   }
}

@Composable
fun SettingCheck(
   caption: String,
   isChecked: Boolean = true,
   onClick: (Boolean) -> Unit
) {
   Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween,
      modifier = Modifier.fillMaxWidth().padding(10.dp)
   ) {
      Column {
         Text(
            text = caption,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
         )
         Text(if (isChecked) "On" else "Off")
      }
      Checkbox(
         checked = isChecked,
         onCheckedChange = { onClick(it) }
      )
   }
}

@Composable
fun SettingSlider(
   caption: String,
   min: Int,
   max: Int,
   currentValue: Int,
   onValueChange: (Int) -> Unit
) {
   Column(modifier = Modifier.fillMaxWidth().padding(10.dp)) {
      Text(
         text = caption,
         fontSize = 20.sp,
         fontWeight = FontWeight.Bold
      )
      Text(currentValue.toString())

      Slider(
         value = currentValue.toFloat(),
         steps = max,
         valueRange = min.toFloat()..max.toFloat(),
         onValueChange = { onValueChange(it.toInt()) }
      )
   }
}