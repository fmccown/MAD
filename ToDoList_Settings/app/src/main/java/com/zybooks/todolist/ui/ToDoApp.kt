package com.zybooks.todolist.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zybooks.todolist.data.PreferenceStorage

enum class AppScreen {
   LIST,
   SETTINGS
}

@Composable
fun ToDoListApp() {
   val navController = rememberNavController()
   val viewModel = ToDoViewModel(PreferenceStorage(LocalContext.current))

   NavHost(
      navController = navController,
      startDestination = AppScreen.LIST.name
   ) {
      composable(route = AppScreen.LIST.name) {
         ToDoScreen(
            todoViewModel = viewModel,
            onClickSettings = {
               navController.navigate(AppScreen.SETTINGS.name)
            }
         )
      }

      composable(route = AppScreen.SETTINGS.name) {
         SettingsScreen(
            onUpClick = {
               navController.popBackStack()
            }
         )
      }
   }
}