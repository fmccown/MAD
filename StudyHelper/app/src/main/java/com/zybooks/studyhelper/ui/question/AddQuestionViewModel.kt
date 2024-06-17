package com.zybooks.studyhelper.ui.question

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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

class AddQuestionViewModel(
   savedStateHandle: SavedStateHandle,
   private val studyRepo: StudyRepository
) : ViewModel() {

   companion object {
      val Factory: ViewModelProvider.Factory = viewModelFactory {
         initializer {
            val application = (this[APPLICATION_KEY] as StudyHelperApplication)
            AddQuestionViewModel(this.createSavedStateHandle(), application.studyRepository)
         }
      }
   }

   // Get subject ID from composable()'s route argument list
   private val subjectId: Long = checkNotNull(savedStateHandle["subjectId"])

   var question by mutableStateOf(Question(0))
      private set

   fun changeQuestion(ques: Question) {
      question = ques
   }

   fun addQuestion() {
      question.subjectId = subjectId
      studyRepo.addQuestion(question)
   }
}
