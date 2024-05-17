package com.zybooks.todolist.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zybooks.todolist.data.AppPreferences
import com.zybooks.todolist.data.PreferenceStorage

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
   modifier: Modifier = Modifier,
   onUpClick: () -> Unit = {}
) {
   val store = PreferenceStorage(LocalContext.current)
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
         verticalArrangement = Arrangement.spacedBy(20.dp)
      ) {
         Column(
            modifier = Modifier
               .padding(innerPadding)
               .fillMaxWidth()
               .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
         ) {
            // TODO: Add preference composables here

         }
      }
   }
}