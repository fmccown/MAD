package com.zybooks.todolist.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
   modifier: Modifier = Modifier,
   settingsViewModel: SettingsViewModel = viewModel(),
   onUpClick: () -> Unit = {}
) {
   Scaffold(
      topBar = {
         TopAppBar(
            title = { Text("Settings") },
            navigationIcon = {
               IconButton(onClick = onUpClick) {
                  Icon(Icons.Filled.ArrowBack,"Back")
               }
            }
         )
      }
   ) { innerPadding ->

   }
}