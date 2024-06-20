package com.zybooks.studyhelper.data

data class Subject(
   var id: Long = 0,
   var title: String = "",
   var creationTime: Long = System.currentTimeMillis()
)