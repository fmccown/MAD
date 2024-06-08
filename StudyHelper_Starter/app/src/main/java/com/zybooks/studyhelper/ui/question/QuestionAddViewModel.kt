package com.zybooks.studyhelper.ui.question

import android.content.Context
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

class QuestionAddViewModel(
   savedStateHandle: SavedStateHandle,
   context: Context
) : ViewModel() {

   companion object {
      val Factory: ViewModelProvider.Factory = viewModelFactory {
         initializer {
            val application = (this[APPLICATION_KEY] as StudyHelperApplication)
            QuestionAddViewModel(this.createSavedStateHandle(), application.appContext)
         }
      }
   }

   var question by mutableStateOf(Question(0))
      private set

   fun changeQuestion(ques: Question) {
      question = ques
   }

   // Get subject ID from composable()'s argument list
   private val subjectId: Long = checkNotNull(savedStateHandle["subjectId"])

   fun addQuestion() {
      // TODO: Complete this function
   }
}
