package com.zybooks.studyhelper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zybooks.studyhelper.ui.StudyHelperApp
import com.zybooks.studyhelper.ui.StudyViewModel
import com.zybooks.studyhelper.ui.theme.StudyHelperTheme

class MainActivity : ComponentActivity() {

   // NullPointerException
   //private val appContext = this.applicationContext

   /*
   private val viewModel by viewModels<StudyViewModel>(
      factoryProducer = {
         object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
               return StudyViewModel(appContext) as T
            }
         }
      }
   )*/

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)

      val appContext = this.applicationContext

      setContent {
         StudyHelperTheme {
            Surface(
               modifier = Modifier.fillMaxSize(),
               color = MaterialTheme.colorScheme.background
            ) {
               /*
               val viewModel: StudyViewModel = viewModel(
                  it,
                  "StudyViewModel",

               )*/

               // Idea for this combines code from Jetpack Compose 1.5 Essentials and
               // https://medium.com/@jpmtech/intro-to-room-using-jetpack-compose-38d078cdb43d
               val viewModel by viewModels<StudyViewModel>(
                  factoryProducer = {
                     object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                           return StudyViewModel(appContext) as T
                        }
                     }
                  }
               )

               StudyHelperApp(viewModel = viewModel)
            }
         }
      }
   }
}

