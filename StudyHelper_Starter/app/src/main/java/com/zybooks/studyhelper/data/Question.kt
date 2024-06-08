package com.zybooks.studyhelper.data

data class Question(
   var id: Long = 0,
   var text: String = "",
   var answer: String = "",
   var subjectId: Long = 0
)