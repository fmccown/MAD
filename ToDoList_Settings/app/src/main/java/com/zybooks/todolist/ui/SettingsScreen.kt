package com.zybooks.todolist.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zybooks.todolist.data.AppPreferences
import com.zybooks.todolist.data.PrefStorage
import com.zybooks.todolist.data.TaskOrder
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
   modifier: Modifier = Modifier,
   //settingsViewModel: SettingsViewModel = viewModel(),
   onUpClick: () -> Unit = {}
) {
   val store = PrefStorage(LocalContext.current)
   val appPrefs = store.appPreferencesFlow.collectAsStateWithLifecycle(AppPreferences())
   val coroutineScope = rememberCoroutineScope()

   Scaffold(
      topBar = {
         TopAppBar(
            title = { Text("Settings") },
            colors = TopAppBarDefaults.topAppBarColors(
               containerColor = MaterialTheme.colorScheme.primaryContainer,
               titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            navigationIcon = {
               IconButton(onClick = onUpClick) {
                  Icon(Icons.Filled.ArrowBack, "Back")
               }
            }
         )
      }
   ) { innerPadding ->
      Column(
         modifier = Modifier
            .padding(innerPadding)
            .fillMaxWidth()
            .padding(8.dp),
         verticalArrangement = Arrangement.spacedBy(16.dp)
      ) {
         val taskOptions = TaskOrder.entries.map { it.text }
         val selectedIndex = when (appPrefs.value.taskOrder) {
            TaskOrder.NEWEST_IS_LAST -> 0
            TaskOrder.NEWEST_IS_FIRST -> 1
            else -> 2
         }

         ListPreference(
            title = "Task order",
            values = taskOptions,
            selectedIndex = selectedIndex,
            onItemClick = {
               coroutineScope.launch {
                  val selectedOrder = when (it) {
                     0 -> TaskOrder.NEWEST_IS_LAST
                     1 -> TaskOrder.NEWEST_IS_FIRST
                     else -> TaskOrder.ALPHABETIC
                  }
                  store.saveTaskOrder(selectedOrder)
               }
            }
         )
         SwitchPreference(
            title = "Confirm delete",
            checked = appPrefs.value.confirmDelete,
            onCheckChange = {
               coroutineScope.launch {
                  store.saveConfirmDelete(it)
               }
            }
         )
         SliderPreference(
            title = "Number of tasks",
            initValue = appPrefs.value.numTasks,
            valueRange = 1..20,
            onValueChangeFinished = {
               coroutineScope.launch {
                  println("Saving $it")
                  store.saveNumTasks(it)
               }
            }
         )
      }
   }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListPreference(
   title: String,
   values: List<String>,
   selectedIndex: Int,
   onItemClick: (Int) -> Unit,
   modifier: Modifier = Modifier
) {
   var expanded by remember { mutableStateOf(false) }

   Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween,
      modifier = modifier.fillMaxWidth()
   ) {
      Text(
         text = title,
         fontSize = 20.sp
      )
      ExposedDropdownMenuBox(
         expanded = expanded,
         onExpandedChange = { expanded = it },
      ) {
         TextField(
            value = values[selectedIndex],
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = modifier
               .menuAnchor()
               .width(200.dp)
         )
         ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
         ) {
            values.forEachIndexed { index, item ->
               DropdownMenuItem(
                  text = { Text(item) },
                  onClick = {
                     expanded = false
                     onItemClick(index)
                  }
               )
            }
         }
      }
   }
}

@Composable
fun SwitchPreference(
   title: String,
   checked: Boolean,
   onCheckChange: (Boolean) -> Unit,
   modifier: Modifier = Modifier
) {
   Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween,
      modifier = modifier.fillMaxWidth()
   ) {
      Text(
         text = title,
         fontSize = 20.sp
      )
      Switch(
         checked = checked,
         onCheckedChange = onCheckChange
      )
   }
}

@Composable
fun SliderPreference(
   title: String,
   initValue: Int,
   valueRange: ClosedRange<Int>,
   onValueChangeFinished: (Int) -> Unit,
   modifier: Modifier = Modifier
) {
   var sliderPosition by remember { mutableFloatStateOf(initValue.toFloat()) }

   Column {
      Text(
         text = title,
         fontSize = 20.sp
      )
      Row(
         verticalAlignment = Alignment.CenterVertically,
         modifier = modifier.fillMaxWidth()
      ) {
         Slider(
            value = sliderPosition,
            steps = valueRange.endInclusive - valueRange.start - 1,
            valueRange = valueRange.start.toFloat()..valueRange.endInclusive.toFloat(),
            onValueChange = { sliderPosition = it.roundToInt().toFloat() },
            onValueChangeFinished = {
               onValueChangeFinished(sliderPosition.toInt())
            },
            modifier = modifier.weight(1f)
         )
         Text(sliderPosition.toInt().toString())
      }
   }

   // Set initial slider position when initValue changes
   LaunchedEffect(initValue) {
      sliderPosition = initValue.toFloat()
   }
}