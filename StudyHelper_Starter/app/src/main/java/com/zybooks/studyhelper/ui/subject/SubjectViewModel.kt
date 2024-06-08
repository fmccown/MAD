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

   private val selectedSubjects = MutableStateFlow(emptySet<Subject>())
   private val inSelectionMode = MutableStateFlow(false)
   private val isSubjectDialogVisible = MutableStateFlow(false)

   // TODO: Modify to update SubjectScreenUiState
   val uiState: StateFlow<SubjectScreenUiState> = MutableStateFlow(SubjectScreenUiState())

   fun addSubject(title: String) {
      // TODO: Complete this function
   }

   fun selectSubjectForDeleting(subject: Subject) {
      val selected = uiState.value.selectedSubjects.contains(subject)
      selectedSubjects.value = if (selected) {
         uiState.value.selectedSubjects.minus(subject)
      } else {
         uiState.value.selectedSubjects.plus(subject)
      }

      inSelectionMode.value = selectedSubjects.value.isNotEmpty()
   }

   fun stopDeleting() {
      selectedSubjects.value = emptySet()
      inSelectionMode.value = false
   }

   fun deleteSelectedSubjects() {
      // TODO: Complete this function
   }

   fun showSubjectDialog() {
      isSubjectDialogVisible.value = true
   }

   fun hideSubjectDialog() {
      isSubjectDialogVisible.value = false
   }
}

data class SubjectScreenUiState(
   val subjectList: List<Subject> = emptyList(),
   val selectedSubjects: Set<Subject> = emptySet(),
   val inSelectionMode: Boolean = false,
   val isSubjectDialogVisible: Boolean = false
)