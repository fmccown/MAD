package com.zybooks.studyhelper.ui.question

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.zybooks.studyhelper.StudyHelperApplication
import com.zybooks.studyhelper.data.Question
import com.zybooks.studyhelper.data.Subject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class QuestionViewModel(
   savedStateHandle: SavedStateHandle,
   context: Context
) : ViewModel() {

   companion object {
      val Factory: ViewModelProvider.Factory = viewModelFactory {
         initializer {
            val application = (this[APPLICATION_KEY] as StudyHelperApplication)
            QuestionViewModel(this.createSavedStateHandle(), application.appContext)
         }
      }
   }

   // Get from composable()'s argument list
   private val subjectId: Long = checkNotNull(savedStateHandle["subjectId"])

   private val currQuestionNum = MutableStateFlow(1)
   private val currQuestion = MutableStateFlow(Question())
   private val answerVisible = MutableStateFlow(true)

   private val testQuestions: List<Question> = listOf(
      Question(
         id = 1,
         text = "What is the slope and y-intercept of y = 4x + 1?",
         answer = "Slope = 4, y-intercept = 1",
         subjectId = 1
      ),
      Question(
         id = 2,
         text = "What is the slope between the points (2, 6) and (4, -3)?",
         answer = "Slope = (-3 - 6) / (4 - 2) = -9 / 2",
         subjectId = 1
      )
   )

   val uiState: StateFlow<QuestionScreenUiState> = MutableStateFlow(
      QuestionScreenUiState(
         subject = Subject(id = 1, title = "Algebra"),
         currQuestion = testQuestions[0],
         questionList = testQuestions,
         totalQuestions = 2
      )
   )

   fun prevQuestion() {
      val index = (uiState.value.currQuestionNum - 2 + uiState.value.totalQuestions) %
            uiState.value.totalQuestions
      currQuestion.value = uiState.value.questionList[index]
      currQuestionNum.value = index + 1
   }

   fun nextQuestion() {
      val index = uiState.value.currQuestionNum % uiState.value.totalQuestions
      currQuestion.value = uiState.value.questionList[index]
      currQuestionNum.value = index + 1
   }

   fun deleteQuestion() {
      // TODO: Complete function
   }

   fun toggleAnswer() {
      answerVisible.value = !answerVisible.value
   }
}

data class QuestionScreenUiState(
   val subject: Subject = Subject(),
   val currQuestion: Question = Question(),
   val questionList: List<Question> = emptyList(),
   val currQuestionNum: Int = 1,
   val totalQuestions: Int = 0,
   val answerVisible: Boolean = true
)
