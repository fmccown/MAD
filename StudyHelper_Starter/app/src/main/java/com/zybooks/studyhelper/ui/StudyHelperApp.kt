package com.zybooks.studyhelper.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import com.zybooks.studyhelper.ui.question.QuestionScreen
import com.zybooks.studyhelper.ui.subject.SubjectScreen
import kotlinx.serialization.Serializable

sealed class Routes {
   @Serializable
   data object Subject

   @Serializable
   data class Question(
      val subjectId: Long,
      val showLastQuestion: Boolean = false
   )
}

@Composable
fun StudyHelperApp() {
   val navController = rememberNavController()

   NavHost(
      navController = navController,
      startDestination = Routes.Subject
   ) {
      composable<Routes.Subject> {
         SubjectScreen(
            onSubjectClick = { subject ->
               navController.navigate(
                  Routes.Question(subjectId = subject.id)
               )
            }
         )
      }
      composable<Routes.Question> { backStackEntry ->
         QuestionScreen(
            onUpClick = { navController.navigateUp() },
         )
      }
   }
}


