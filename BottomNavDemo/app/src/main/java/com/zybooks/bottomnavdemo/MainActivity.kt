package com.zybooks.bottomnavdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.zybooks.bottomnavdemo.ui.theme.BottomNavDemoTheme


class MainActivity : ComponentActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContent {
         BottomNavDemoTheme {
            // A surface container using the 'background' color from the theme
            Surface(
               modifier = Modifier.fillMaxSize(),
               color = MaterialTheme.colorScheme.background
            ) {
               BottomNavBarDemoApp()
            }
         }
      }
   }
}

enum class AppScreen(val title: String, val icon: ImageVector) {
   HOME("Home", Icons.Default.Home),
   MESSAGES("Messages", Icons.Default.Email),
   FAVORITES("Favorites", Icons.Default.Favorite)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavBarDemoApp(
   modifier: Modifier = Modifier
) {
   val navController = rememberNavController()

   Scaffold(
      topBar = {
         TopAppBar(
            title = { Text("Bottom Nav Bar Demo") }
         )
      },
      bottomBar = {
         BottomNavBar(navController)
      }
   ) { innerPadding ->
      NavHost(
         navController = navController,
         startDestination = AppScreen.HOME.name,
         modifier = modifier.padding(innerPadding)
      ) {
         composable(route = AppScreen.HOME.name) {
            Screen(AppScreen.HOME.title)
         }
         composable(route = AppScreen.MESSAGES.name) {
            Screen(AppScreen.MESSAGES.title)
         }
         composable(route = AppScreen.FAVORITES.name) {
            Screen(AppScreen.FAVORITES.title)
         }
      }
   }
}

@Composable
fun Screen(title: String) {
   Box(
      Modifier.fillMaxSize()
   ) {
      Text(
         title,
         fontSize = 80.sp,
         modifier = Modifier.align(Alignment.Center)
      )
   }
}

@Composable
fun BottomNavBar(navController: NavController) {
   val backStackEntry by navController.currentBackStackEntryAsState()
   val currentRoute = backStackEntry?.destination?.route

   NavigationBar {
      AppScreen.entries.forEach { item ->
         NavigationBarItem(
            selected = currentRoute == item.name,
            onClick = {
               navController.navigate(item.name) {
                  popUpTo(navController.graph.startDestinationId)
               }
            },
            icon = {
               Icon(item.icon, contentDescription = item.title)
            },
            label = {
               Text(item.title)
            }
         )
      }
   }
}

@Preview(showBackground = true, device = "spec:id=reference_phone,shape=Normal,width=411,height=591,unit=dp,dpi=420")
@Composable
fun BottomNavBarPreview() {
   val navController = rememberNavController()

   BottomNavDemoTheme {
      BottomNavBar(navController)
   }
}

@Preview(showBackground = true)
@Composable
fun BottomNavBarDemoAppPreview() {
   BottomNavDemoTheme {
      BottomNavBarDemoApp()
   }
}