package com.zybooks.studyhelper.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.zybooks.studyhelper.ui.question.QuestionScreen
import com.zybooks.studyhelper.ui.subject.SubjectScreen

enum class StudyScreen {
   SUBJECT,
   QUESTION,
}

@Composable
fun StudyHelperApp() {
   val navController = rememberNavController()

   NavHost(
      navController = navController,
      startDestination = StudyScreen.SUBJECT.name
   ) {
      composable(route = StudyScreen.SUBJECT.name) {
         SubjectScreen(
            onSubjectClick = { subject ->
               navController.navigate("${StudyScreen.QUESTION.name}/${subject.id}")
            }
         )
      }

      composable(
         route = "${StudyScreen.QUESTION.name}/{subjectId}",

         // Specify subject ID argument so QuestionViewModel can determine selected subject
         arguments = listOf(navArgument("subjectId") {
            type = NavType.LongType
         })
      ) { backStackEntry ->
         QuestionScreen(
            onUpClick = { navController.popBackStack() },
            onAddClick = { },
            onEditClick = { }
         )
      }
   }
}


