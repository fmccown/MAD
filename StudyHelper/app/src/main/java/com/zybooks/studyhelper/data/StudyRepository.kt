package com.zybooks.studyhelper.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StudyRepository private constructor(context: Context) {
   companion object {
      private var instance: StudyRepository? = null

      fun getInstance(context: Context): StudyRepository {
         if (instance == null) {
            instance = StudyRepository(context)
         }
         return instance!!
      }
   }

   private val databaseCallback = object : RoomDatabase.Callback() {
      override fun onCreate(db: SupportSQLiteDatabase) {
         super.onCreate(db)

         CoroutineScope(Dispatchers.IO).launch {
            instance?.addStarterData()
         }
      }
   }

   private val database : StudyDatabase = Room.databaseBuilder(
      context,
      StudyDatabase::class.java,
      "study.db"
   )
      .addCallback(databaseCallback)
      .build()

   private val subjectDao = database.subjectDao()
   private val questionDao = database.questionDao()

   fun getSubject(subjectId: Long): LiveData<Subject?> = subjectDao.getSubject(subjectId)

   fun getSubjects(): LiveData<List<Subject>> = subjectDao.getSubjects()

   fun addSubject(subject: Subject) {
      CoroutineScope(Dispatchers.IO).launch {
         subject.id = subjectDao.addSubject(subject)
      }
   }

   fun deleteSubject(subject: Subject) {
      CoroutineScope(Dispatchers.IO).launch {
         subjectDao.deleteSubject(subject)
      }
   }

   fun getQuestion(questionId: Long): LiveData<Question?> = questionDao.getQuestion(questionId)

   fun getQuestions(subjectId: Long): LiveData<List<Question>> = questionDao.getQuestions(subjectId)

   fun addQuestion(question: Question) {
      CoroutineScope(Dispatchers.IO).launch {
         question.id = questionDao.addQuestion(question)
      }
   }

   fun updateQuestion(question: Question) {
      CoroutineScope(Dispatchers.IO).launch {
         questionDao.updateQuestion(question)
      }
   }

   fun deleteQuestion(question: Question) {
      CoroutineScope(Dispatchers.IO).launch {
         questionDao.deleteQuestion(question)
      }
   }

   private fun addStarterData() {
      var subjectId = subjectDao.addSubject(Subject(title = "Math"))
      questionDao.addQuestion(
         Question(
            text = "What is 2 + 3?",
            answer = "2 + 3 = 5",
            subjectId = subjectId
         )
      )
      questionDao.addQuestion(
         Question(
            text = "What is pi?",
            answer = "The ratio of a circle's circumference to its diameter.",
            subjectId = subjectId
         )
      )

      subjectId = subjectDao.addSubject(Subject(title = "History"))
      questionDao.addQuestion(
         Question(
            text = "On what date was the U.S. Declaration of Independence adopted?",
            answer = "July 4, 1776",
            subjectId = subjectId
         )
      )

      subjectDao.addSubject(Subject(title = "Computing"))
   }
}

