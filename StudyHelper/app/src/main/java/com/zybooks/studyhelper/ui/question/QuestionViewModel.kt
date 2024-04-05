package com.zybooks.studyhelper.ui.question

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.zybooks.studyhelper.StudyHelperApplication
import com.zybooks.studyhelper.data.Question
import com.zybooks.studyhelper.data.StudyRepository
import com.zybooks.studyhelper.data.Subject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

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

   private val studyRepo = StudyRepository.getInstance(context)

   // Get subject ID from composable()'s argument list and load subject from database
   private val subjectId: Long = checkNotNull(savedStateHandle["subjectId"])

   private val currQuestionNum = MutableStateFlow(1)
   private val currQuestion = MutableStateFlow(Question())

   @OptIn(ExperimentalCoroutinesApi::class)
   val uiState: StateFlow<QuestionScreenUiState> = transformedFlow()
      .stateIn(
         scope = viewModelScope,
         started = SharingStarted.WhileSubscribed(5_000),
         initialValue = QuestionScreenUiState(),
      )

   private fun transformedFlow() = combine(
      studyRepo.getSubject(subjectId).filterNotNull(),
      studyRepo.getQuestions(subjectId),
      currQuestionNum,
      currQuestion
   ) { subject, questions, currNum, currQuest ->
      QuestionScreenUiState(
         subject = subject,
         questionList = questions,
         currQuestion = if (currQuest.id == 0L && questions.isNotEmpty()) questions.first() else currQuest,
         currQuestionNum = currNum,
         totalQuestions = questions.size
      )
   }

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
      val question = uiState.value.currQuestion
      val index = uiState.value.currQuestionNum - 1
      println("deleteQuestion: index = $index")

      // If deleting the very last question
      when (uiState.value.questionList.size) {
         1 -> {
            currQuestion.value = Question()
         }
         uiState.value.currQuestionNum -> {
            // Deleting last question, so previous question becomes currQuestion
            println("Setting currQuestionNum = $index")
            currQuestion.value = uiState.value.questionList[index - 1]
            currQuestionNum.value = index
         }
         else -> {
            // Move to next question
            currQuestion.value = uiState.value.questionList[index + 1]
         }
      }

      studyRepo.deleteQuestion(question)
   }
}

data class QuestionScreenUiState(
   val subject: Subject = Subject(),
   val currQuestion: Question = Question(),
   val questionList: List<Question> = emptyList(),
   val currQuestionNum: Int = 1,
   val totalQuestions: Int = 0
)
