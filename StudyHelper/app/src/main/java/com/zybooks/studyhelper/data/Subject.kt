package com.zybooks.studyhelper.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Subject(
   @PrimaryKey(autoGenerate = true)
   var id: Long = 0,

   var title: String = "",

   @ColumnInfo(name = "created")
   var creationTime: Long = System.currentTimeMillis()
)