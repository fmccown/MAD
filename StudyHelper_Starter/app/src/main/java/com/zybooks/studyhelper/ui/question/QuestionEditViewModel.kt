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

class QuestionEditViewModel(
   savedStateHandle: SavedStateHandle,
   context: Context
) : ViewModel() {

   companion object {
      val Factory: ViewModelProvider.Factory = viewModelFactory {
         initializer {
            val application = (this[APPLICATION_KEY] as StudyHelperApplication)
            QuestionEditViewModel(this.createSavedStateHandle(), application.appContext)
         }
      }
   }

   // Get from composable()'s argument list
   private val questionId: Long = checkNotNull(savedStateHandle["questionId"])

   var question by mutableStateOf(Question(0))
      private set

   fun changeQuestion(ques: Question) {
      question = ques
   }

   fun updateQuestion() {
      // TODO: Complete this function
   }

   init {
      // TODO: Initialize question
   }
}
