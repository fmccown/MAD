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
import com.zybooks.studyhelper.data.StudyRepository
import com.zybooks.studyhelper.data.Subject
import com.zybooks.studyhelper.ui.subject.SubjectViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class QuestionViewModel(
   savedStateHandle: SavedStateHandle,
   private val studyRepo: StudyRepository
) : ViewModel() {

   companion object {
      val Factory: ViewModelProvider.Factory = viewModelFactory {
         initializer {
            val application = (this[APPLICATION_KEY] as StudyHelperApplication)
            QuestionViewModel(
               savedStateHandle = this.createSavedStateHandle(),
               studyRepo = application.studyRepository
            )
         }
      }
   }

   // Get from composable()'s argument list
   private val subjectId: Long = checkNotNull(savedStateHandle["subjectId"])

   private val currQuestionNum = MutableStateFlow(1)
   private val currQuestion = MutableStateFlow(Question(id = 0L))
   private val answerVisible = MutableStateFlow(true)

   val uiState: StateFlow<QuestionScreenUiState> = MutableStateFlow(
      QuestionScreenUiState(
         subject = studyRepo.getSubject(subjectId)!!,
         currQuestion = if (studyRepo.getQuestions(subjectId).isEmpty()) Question() else
            studyRepo.getQuestions(subjectId).first(),
         questionList = studyRepo.getQuestions(subjectId),
         totalQuestions = studyRepo.getQuestions(subjectId).size
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
