package com.zybooks.studyhelper.ui.subject

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.zybooks.studyhelper.StudyHelperApplication
import com.zybooks.studyhelper.data.Subject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SubjectViewModel(context: Context) : ViewModel() {

   companion object {
      val Factory: ViewModelProvider.Factory = viewModelFactory {
         initializer {
            val application = (this[APPLICATION_KEY] as StudyHelperApplication)
            SubjectViewModel(application.appContext)
         }
      }
   }

   val uiState: StateFlow<SubjectScreenUiState> = MutableStateFlow(
      SubjectScreenUiState(
         subjectList = listOf(
            Subject(id = 1, title = "Algebra"),
            Subject(id = 2, title = "Computer Science"),
            Subject(id = 3, title = "US History"),
            Subject(id = 4, title = "Greek"),
            Subject(id = 5, title = "Poetry"),
            Subject(id = 6, title = "Biology"),
            Subject(id = 7, title = "Accounting"),
         )
      )
   )
}

data class SubjectScreenUiState(
   val subjectList: List<Subject> = emptyList(),
   val selectedSubjects: Set<Subject> = emptySet(),
   val inSelectionMode: Boolean = false,
   val isSubjectDialogVisible: Boolean = false
)