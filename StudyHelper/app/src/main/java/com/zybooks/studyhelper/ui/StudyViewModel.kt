package com.zybooks.studyhelper.ui

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.zybooks.studyhelper.data.Question
import com.zybooks.studyhelper.data.StudyRepository
import com.zybooks.studyhelper.data.Subject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StudyViewModel(context: Context) : ViewModel() {
   private val studyRepo = StudyRepository.getInstance(context)

   //fun getSubjects(): LiveData<List<Subject>> = studyRepo.getSubjects()
   val subjectList: LiveData<List<Subject>> = studyRepo.getSubjects()

   val selectedSubject = MutableLiveData<Subject>()

   fun getQuestions(subjectId: Long) = studyRepo.getQuestions(subjectId)
   val questionList: LiveData<List<Question>> =
      selectedSubject.switchMap { subject ->
         Log.d("McCown", "Getting questions for ${subject.id}")
         _currQuestionIndex = 0
         studyRepo.getQuestions(subject.id)
      }

   private var _currQuestionIndex by mutableIntStateOf(0)
   val currQuestionIndex : Int
      get() = _currQuestionIndex

   fun prevQuestion() {
      if (_currQuestionIndex == 0) {
         _currQuestionIndex = questionList.value?.size?.minus(1) ?: 0
      } else {
         _currQuestionIndex--
      }
   }

   fun nextQuestion() {
      if (_currQuestionIndex == (questionList.value?.size?.minus(1) ?: 0)) {
         _currQuestionIndex = 0
      } else {
         _currQuestionIndex++
      }
   }

   fun addSubject(subject: Subject) = studyRepo.addSubject(subject)

   fun deleteSubject(subject: Subject) {
      studyRepo.deleteSubject(subject)

      // Deleting last question is a special case
      if (_currQuestionIndex > 0 &&
         _currQuestionIndex == (questionList.value?.size?.minus(1) ?: 1)) {

         _currQuestionIndex--
      }
   }

   fun addQuestion(question: Question) {
      studyRepo.addQuestion(question)

      if (questionList.value?.size != null) {
         _currQuestionIndex = questionList.value?.size!!
      }
      Log.d("McCown", "NEW currQuestionIndex = $_currQuestionIndex")
   }

   fun updateQuestion(question: Question) = studyRepo.updateQuestion(question)
   fun deleteQuestion(question: Question) = studyRepo.deleteQuestion(question)
}
