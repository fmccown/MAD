package com.zybooks.studyhelper.ui.question

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.stateIn

class QuestionViewModel(
   savedStateHandle: SavedStateHandle,
   private val studyRepo: StudyRepository
) : ViewModel() {

   companion object {
      val Factory: ViewModelProvider.Factory = viewModelFactory {
         initializer {
            val application = (this[APPLICATION_KEY] as StudyHelperApplication)
            QuestionViewModel(this.createSavedStateHandle(), application.studyRepository)
         }
      }
   }

   // Get from composable()'s argument list
   private val subjectId: Long = checkNotNull(savedStateHandle["subjectId"])

   private val currQuestionNum = MutableStateFlow(1)
   private val currQuestion = MutableStateFlow(Question())
   private val answerVisible = MutableStateFlow(true)

   val uiState: StateFlow<QuestionScreenUiState> = transformedFlow()
      .stateIn(
         scope = viewModelScope,
         started = SharingStarted.WhileSubscribed(5000L),
         initialValue = QuestionScreenUiState(),
      )

   private fun transformedFlow() = combine(
      studyRepo.getSubject(subjectId).filterNotNull(),
      studyRepo.getQuestions(subjectId),
      currQuestionNum,
      currQuestion,
      answerVisible
   ) { subject, questions, currNum, currQuest, ansVisible ->
      QuestionScreenUiState(
         subject = subject,
         questionList = questions,
         currQuestion = if (currQuest.id == 0L && questions.isNotEmpty()) questions.first()
            else if (questions.isEmpty()) currQuest else questions[currNum - 1],
         currQuestionNum = currNum,
         totalQuestions = questions.size,
         answerVisible = ansVisible
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

      // Special cases when deleting the very last question
      when (uiState.value.questionList.size) {
         1 -> {
            // Deleting very last question
            currQuestion.value = Question()
         }
         uiState.value.currQuestionNum -> {
            // Deleting last question, so previous question becomes currQuestion
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
