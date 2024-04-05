package com.zybooks.studyhelper.ui.subject

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.zybooks.studyhelper.StudyHelperApplication
import com.zybooks.studyhelper.data.StudyRepository
import com.zybooks.studyhelper.data.Subject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.stateIn

class SubjectViewModel(context: Context) : ViewModel() {

   companion object {
      val Factory: ViewModelProvider.Factory = viewModelFactory {
         initializer {
            val application = (this[APPLICATION_KEY] as StudyHelperApplication)
            SubjectViewModel(application.appContext)
         }
      }
   }

   private val studyRepo = StudyRepository.getInstance(context)

   var uiState by mutableStateOf(SubjectScreenUiState(
      subjectList = studyRepo.getSubjects().filterNotNull()
         .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
         )
      )
   )
      private set

   fun addSubject(title: String) {
      val subTitle = title.trim()
      if (subTitle != "") {
         studyRepo.addSubject(Subject(title = subTitle))
      }
   }

   fun selectSubjectForDeleting(subject: Subject) {
      val selected = uiState.selectedSubjects.contains(subject)
      val selectedSubjects = if (selected) {
         uiState.selectedSubjects.minus(subject)
      } else {
         uiState.selectedSubjects.plus(subject)
      }

      uiState = uiState.copy(
         selectedSubjects = selectedSubjects,
         inSelectionMode = selectedSubjects.isNotEmpty()
      )
   }

   fun stopDeleting() {
      uiState = uiState.copy(
         selectedSubjects = emptySet(),
         inSelectionMode = false
      )
   }

   fun deleteSelectedSubjects() {
      for (subject in uiState.selectedSubjects) {
         studyRepo.deleteSubject(subject)
      }
      stopDeleting()
   }

   fun showSubjectDialog() {
      uiState = uiState.copy(
         isSubjectDialogVisible = true
      )
   }

   fun hideSubjectDialog() {
      uiState = uiState.copy(
         isSubjectDialogVisible = false
      )
   }
}

data class SubjectScreenUiState(
   val subjectList: StateFlow<List<Subject>>,
   val selectedSubjects: Set<Subject> = emptySet(),
   val inSelectionMode: Boolean = false,
   val isSubjectDialogVisible: Boolean = false
)