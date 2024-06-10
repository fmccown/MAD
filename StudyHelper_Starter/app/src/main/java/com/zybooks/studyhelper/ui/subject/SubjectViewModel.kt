package com.zybooks.studyhelper.ui.subject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.zybooks.studyhelper.StudyHelperApplication
import com.zybooks.studyhelper.data.StudyRepository
import com.zybooks.studyhelper.data.Subject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SubjectViewModel(private val studyRepo: StudyRepository) : ViewModel() {

   companion object {
      val Factory: ViewModelProvider.Factory = viewModelFactory {
         initializer {
            val application = (this[APPLICATION_KEY] as StudyHelperApplication)
            SubjectViewModel(application.studyRepository)
         }
      }
   }

   val uiState: StateFlow<SubjectScreenUiState> = MutableStateFlow(
      SubjectScreenUiState(
         subjectList = studyRepo.getSubjects()
      )
   )
}

data class SubjectScreenUiState(
   val subjectList: List<Subject> = emptyList(),
   val isSubjectDialogVisible: Boolean = false
)