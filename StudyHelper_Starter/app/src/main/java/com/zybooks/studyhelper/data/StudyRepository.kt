package com.zybooks.studyhelper.data

import android.content.Context

class StudyRepository(context: Context) {

   private val subjectList = listOf(
      Subject(id = 1, title = "Algebra"),
      Subject(id = 2, title = "Computer Science"),
      Subject(id = 3, title = "US History"),
      Subject(id = 4, title = "Greek"),
      Subject(id = 5, title = "Poetry"),
      Subject(id = 6, title = "Biology"),
      Subject(id = 7, title = "Accounting")
   )

   fun getSubjects() = subjectList

   fun getSubject(subjectId: Long): Subject? {
      return subjectList.find { it.id == subjectId }
   }

   fun getQuestions(subjectId: Long): List<Question> {
      if (subjectId == 1L) {
         return listOf(
            Question(
               id = 1,
               text = "What is the y-intercept of y = 4x + 1?",
               answer = "y-intercept = 1",
               subjectId = 1
            ),
            Question(
               id = 2,
               text = "What is the slope between the points (2, 6) and (4, -3)?",
               answer = "Slope = (-3 - 6) / (4 - 2) = -9 / 2",
               subjectId = 1
            )
         )
      } else {
         return emptyList()
      }
   }
}