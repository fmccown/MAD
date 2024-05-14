package com.zybooks.todolist.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

enum class AppScreen() {
   LIST,
   SETTINGS
}

@Composable
fun ToDoListApp() {
   val navController = rememberNavController()

   NavHost(
      navController = navController,
      startDestination = AppScreen.LIST.name
   ) {
      composable(route = AppScreen.LIST.name) {
         ToDoScreen(
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