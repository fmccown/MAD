package com.zybooks.bottomnavdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.zybooks.bottomnavdemo.ui.theme.BottomNavDemoTheme
import kotlinx.serialization.Serializable


class MainActivity : ComponentActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContent {
         BottomNavDemoTheme {
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

sealed class Routes {
   @Serializable
   data object Home

   @Serializable
   data object Messages

   @Serializable
   data object Favorites
}

enum class AppScreen(val route: Any, val title: String, val icon: ImageVector) {
   HOME(Routes.Home, "Home", Icons.Default.Home),
   MESSAGES(Routes.Messages, "Messages", Icons.Default.Email),
   FAVORITES(Routes.Favorites, "Favorites", Icons.Default.Favorite)
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
         startDestination = Routes.Home,
         modifier = modifier.padding(innerPadding)
      ) {
         composable<Routes.Home> {
            Home()
         }
         composable<Routes.Messages> {
            Messages()
         }
         composable<Routes.Favorites> {
            Favorites()
         }
      }
   }
}

@Composable
fun Home() {
   Text(
      AppScreen.HOME.title,
      textAlign = TextAlign.Center,
      fontSize = 80.sp,
      modifier = Modifier
         .fillMaxSize()
         .wrapContentHeight(),
   )
}

@Composable
fun Messages() {
   Text(
      AppScreen.MESSAGES.title,
      textAlign = TextAlign.Center,
      fontSize = 80.sp,
      modifier = Modifier
         .fillMaxSize()
         .wrapContentHeight(),
   )
}

@Composable
fun Favorites() {
   Text(
      AppScreen.FAVORITES.title,
      textAlign = TextAlign.Center,
      fontSize = 80.sp,
      modifier = Modifier
         .fillMaxSize()
         .wrapContentHeight(),
   )
}

@Composable
fun BottomNavBar(navController: NavController) {
   val backStackEntry by navController.currentBackStackEntryAsState()
   val currentRoute = backStackEntry?.destination?.route

   NavigationBar {
      AppScreen.entries.forEach { item ->
         NavigationBarItem(
            selected = currentRoute?.endsWith(item.route.toString()) == true,
            onClick = {
               navController.navigate(item.route) {
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

@Preview(
   showBackground = true,
   device = "spec:id=reference_phone,shape=Normal,width=411,height=591,unit=dp,dpi=420"
)
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