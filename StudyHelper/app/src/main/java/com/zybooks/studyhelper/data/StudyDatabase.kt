package com.zybooks.studyhelper.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Question::class, Subject::class], version = 1)
abstract class StudyDatabase : RoomDatabase() {

   abstract fun questionDao(): QuestionDao
   abstract fun subjectDao(): SubjectDao
}
