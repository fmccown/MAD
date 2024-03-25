package com.zybooks.studyhelper.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [
   ForeignKey(entity = Subject::class,
      parentColumns = ["id"],
      childColumns = ["subject_id"],
      onDelete = ForeignKey.CASCADE)
])
data class Question(
   @PrimaryKey(autoGenerate = true)
   var id: Long = 0,

   var text: String = "",
   var answer: String = "",

   @ColumnInfo(name = "subject_id")
   var subjectId: Long = 0
)