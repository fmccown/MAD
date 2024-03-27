package com.zybooks.studyhelper.ui

import android.content.Context
import android.util.Log
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

   fun getSubjects(): LiveData<List<Subject>> = studyRepo.getSubjects()
   val subjectList: LiveData<List<Subject>> = studyRepo.getSubjects()

   val selectedSubject = MutableLiveData<Subject>()

   fun getQuestions(subjectId: Long) = studyRepo.getQuestions(subjectId)
   val questionList: LiveData<List<Question>> =
      selectedSubject.switchMap { subject ->
         Log.d("McCown", "Getting questions for ${subject.id}")
         studyRepo.getQuestions(subject.id)
      }

   var currQuestionIndex by mutableIntStateOf(0)

   fun addSubject(subject: Subject) = studyRepo.addSubject(subject)
   fun deleteSubject(subject: Subject) = studyRepo.deleteSubject(subject)

   //fun addQuestion(question: Question) = studyRepo.addQuestion(question)

   fun addQuestion(question: Question) {
      studyRepo.addQuestion(question)
      if (questionList.value?.size != null) {
         currQuestionIndex = questionList.value?.size!!
      }
      Log.d("McCown", "NEW currQuestionIndex = $currQuestionIndex")

      /*
      CoroutineScope(Dispatchers.Main).launch {
         Log.d("McCown", "Calling addQuestion")
         studyRepo.addQuestion(question).join()
         Log.d("McCown", "question.id = ${question.id}")
         Log.d("McCown", "currQuestionIndex = $currQuestionIndex")

         /*
         questionList.observe(context, {

         })*/

         // Problem: questionList has not changed after adding the
         // new question. Need to somehow refresh questionList.
         if (questionList.value != null) {
            Log.d("McCown", "questionList.value.size = ${questionList.value?.size}")
         }
         //currQuestionIndex = questionList.value?.size ?: 0
         if (questionList.value?.size != null) {
            currQuestionIndex = questionList.value?.size!!
         }
         Log.d("McCown", "NEW currQuestionIndex = $currQuestionIndex")

         //selectedSubject.value = selectedSubject.value
      }*/

   }

   fun updateQuestion(question: Question) = studyRepo.updateQuestion(question)
   fun deleteQuestion(question: Question) = studyRepo.deleteQuestion(question)
}
