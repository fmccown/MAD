package com.zybooks.studyhelper.ui.subject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.zybooks.studyhelper.StudyHelperApplication
import com.zybooks.studyhelper.data.StudyRepository
import com.zybooks.studyhelper.data.Subject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.stateIn

class SubjectViewModel(private val studyRepo: StudyRepository) : ViewModel() {

   companion object {
      val Factory: ViewModelProvider.Factory = viewModelFactory {
         initializer {
            val application = (this[APPLICATION_KEY] as StudyHelperApplication)
            SubjectViewModel(application.studyRepository)
         }
      }
   }

   private val selectedSubjects = MutableStateFlow(emptySet<Subject>())
   private val inSelectionMode = MutableStateFlow(false)
   private val isSubjectDialogVisible = MutableStateFlow(false)

   val uiState: StateFlow<SubjectScreenUiState> = transformedFlow()
      .stateIn(
         scope = viewModelScope,
         started = SharingStarted.WhileSubscribed(5000L),
         initialValue = SubjectScreenUiState(),
      )

   private fun transformedFlow() = combine(
      studyRepo.getSubjects(),
      selectedSubjects,
      inSelectionMode,
      isSubjectDialogVisible
   ) { subjects, selectSubs, inSelectMode, dialogVisible ->
      SubjectScreenUiState(
         subjectList = subjects,
         selectedSubjects = selectSubs,
         inSelectionMode = inSelectMode,
         isSubjectDialogVisible = dialogVisible
      )
   }

   fun addSubject(title: String) {
      val subTitle = title.trim()
      if (subTitle != "") {
         studyRepo.addSubject(Subject(title = subTitle))
      }
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
      for (subject in uiState.value.selectedSubjects) {
         studyRepo.deleteSubject(subject)
      }
      stopDeleting()
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