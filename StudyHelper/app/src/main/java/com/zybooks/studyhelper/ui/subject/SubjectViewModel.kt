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
   private val isSubjectDialogVisible = MutableStateFlow(false)

   val uiState: StateFlow<SubjectScreenUiState> =
      combine(
         studyRepo.getSubjects(),
         selectedSubjects,
         isSubjectDialogVisible
      ) { subjects, selectSubs, dialogVisible ->
         SubjectScreenUiState(
            subjectList = subjects,
            selectedSubjects = selectSubs,
            isSubjectDialogVisible = dialogVisible
         )
      }
      .stateIn(
         scope = viewModelScope,
         started = SharingStarted.WhileSubscribed(5000L),
         initialValue = SubjectScreenUiState(),
      )

   fun addSubject(title: String) {
      studyRepo.addSubject(Subject(title = title))
   }

   fun selectSubject(subject: Subject) {
      val selected = selectedSubjects.value.contains(subject)
      selectedSubjects.value = if (selected) {
         selectedSubjects.value.minus(subject)
      } else {
         selectedSubjects.value.plus(subject)
      }
   }

   fun hideCab() {
      selectedSubjects.value = emptySet()
   }

   fun deleteSelectedSubjects() {
      for (subject in selectedSubjects.value) {
         studyRepo.deleteSubject(subject)
      }
      hideCab()
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
   val isCabVisible: Boolean = selectedSubjects.isNotEmpty(),
   val isSubjectDialogVisible: Boolean = false
)