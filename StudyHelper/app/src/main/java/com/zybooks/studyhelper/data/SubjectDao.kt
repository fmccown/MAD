package com.zybooks.studyhelper.data

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SubjectDao {
   @Query("SELECT * FROM Subject WHERE id = :id")
   fun getSubject(id: Long): LiveData<Subject?>

   @Query("SELECT * FROM Subject ORDER BY title COLLATE NOCASE")
   fun getSubjects(): LiveData<List<Subject>>

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   fun addSubject(subject: Subject): Long

   @Update
   fun updateSubject(subject: Subject)

   @Delete
   fun deleteSubject(subject: Subject)
}

