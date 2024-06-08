package com.zybooks.studyhelper.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.zybooks.studyhelper.ui.question.QuestionAddScreen
import com.zybooks.studyhelper.ui.question.QuestionEditScreen
import com.zybooks.studyhelper.ui.question.QuestionScreen
import com.zybooks.studyhelper.ui.subject.SubjectScreen
import com.zybooks.studyhelper.ui.theme.StudyHelperTheme

enum class StudyScreen {
   SUBJECT,
   QUESTION,
   ADD_QUESTION,
   EDIT_QUESTION
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
         val subjectId = backStackEntry.arguments?.getLong("subjectId")
         QuestionScreen(
            onUpClick = { navController.popBackStack() },
            onAddClick = {
               navController.navigate("${StudyScreen.ADD_QUESTION.name}/${subjectId}")
            },
            onEditClick = { questionId ->
               navController.navigate("${StudyScreen.EDIT_QUESTION.name}/${questionId}")
            }
         )
      }
      composable(
         route = "${StudyScreen.ADD_QUESTION.name}/{subjectId}",
         arguments = listOf(navArgument("subjectId") {
            type = NavType.LongType
         })
      ) {
         QuestionAddScreen(
            onUpClick = { navController.popBackStack() },
            onSaveClick = {
               navController.popBackStack()
               // TODO: Navigate to new question
            }
         )
      }
      composable(
         route = "${StudyScreen.EDIT_QUESTION.name}/{questionId}",

         // Specify question ID argument so QuestionEditViewModel can determine question being edited
         arguments = listOf(navArgument("questionId") {
            type = NavType.LongType
         })
      ) {
         QuestionEditScreen(
            onUpClick = { navController.popBackStack() },
            onSaveClick = { navController.popBackStack() }
         )
      }
   }
}

@Preview(showBackground = true)
@Composable
fun StudyHelperAppPreview() {
   StudyHelperTheme {
      StudyHelperApp()
   }
}

