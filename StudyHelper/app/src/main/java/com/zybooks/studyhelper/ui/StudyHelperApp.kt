package com.zybooks.studyhelper.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.zybooks.studyhelper.data.Subject
import com.zybooks.studyhelper.ui.theme.StudyHelperTheme

enum class StudyScreen() {
   SUBJECT,
   QUESTION,
   ADD_QUESTION,
   EDIT_QUESTION
}

@Composable
fun StudyHelperApp(
   modifier: Modifier = Modifier,
   viewModel: StudyViewModel = viewModel()
) {
   val navController = rememberNavController()
   //val subjectList by viewModel.subjectList.observeAsState(emptyList())
   val subjectList by viewModel.getSubjects().observeAsState(emptyList())
   val questionList by viewModel.questionList.observeAsState(emptyList())

   NavHost(
      navController = navController,
      startDestination = StudyScreen.SUBJECT.name
   ) {
      composable(route = StudyScreen.SUBJECT.name) {
         SubjectScreen(
            subjectList = subjectList,
            onSubjectClick = { subject ->
               viewModel.selectedSubject.value = subject
               viewModel.currQuestionIndex = 0
               navController.navigate(StudyScreen.QUESTION.name)
            },
            onAddSubject = { subject ->
               if (subject.trim() != "") {
                  viewModel.addSubject(Subject(title = subject))
               }
            },
            onDeleteSubjects = { subjects ->
               for (subject in subjects) {
                  viewModel.deleteSubject(subject)
               }
            }
         )
      }
      composable(route = StudyScreen.QUESTION.name) {
         // tHIS MAY HAVE DONE IT
         Log.d("McCown", "Rendering Questions screen")
         Log.d("McCown", "currQuestionIndex = ${viewModel.currQuestionIndex}")
         if (questionList.isNotEmpty() && viewModel.currQuestionIndex == -1) {
            Log.d("McCown", "TOO SMALL, making 0")
            viewModel.currQuestionIndex = 0
         }
         /*
         else if (viewModel.currQuestionIndex >= questionList.size) {
            Log.d("McCown", "TOO LARGE, making ${questionList.size - 1}")
            //viewModel.currQuestionIndex = questionList.size - 1
         }
*/

         QuestionScreen(
            subject = viewModel.selectedSubject.value!!,
            questionList = questionList,
            currQuestionIndex = viewModel.currQuestionIndex,
            onUpClick = { navController.popBackStack() },
            onLeftClick = {
               if (viewModel.currQuestionIndex == 0) {
                  viewModel.currQuestionIndex = questionList.size - 1
               } else {
                  viewModel.currQuestionIndex--
               }
            },
            onRightClick = {
               if (viewModel.currQuestionIndex == questionList.size - 1) {
                  viewModel.currQuestionIndex = 0
               } else {
                  viewModel.currQuestionIndex++
               }
            },
            onAddClick = {
               navController.navigate(StudyScreen.ADD_QUESTION.name)
            },
            onDeleteClick = {
               // Deleting last question is a special case
               if (viewModel.currQuestionIndex == questionList.size - 1) {
                  viewModel.currQuestionIndex--
               }

               viewModel.deleteQuestion(it)
            },
            onEditClick = {
               navController.navigate(StudyScreen.EDIT_QUESTION.name)
            }
         )
      }
      composable(route = StudyScreen.ADD_QUESTION.name) {
         AddEditQuestionScreen(
            subject = viewModel.selectedSubject.value!!,
            onUpClick = { navController.popBackStack() },
            onSaveClick = {
               viewModel.addQuestion(it)
               navController.popBackStack()
            }
         )
      }
      composable(route = StudyScreen.EDIT_QUESTION.name) {
         AddEditQuestionScreen(
            subject = viewModel.selectedSubject.value!!,
            question = questionList[viewModel.currQuestionIndex],
            onUpClick = { navController.popBackStack() },
            onSaveClick = {
               viewModel.updateQuestion(it)
               navController.popBackStack()
            }
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

