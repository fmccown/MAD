package com.zybooks.studyhelper.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao {
   @Query("SELECT * FROM Question WHERE id = :id")
   fun getQuestion(id: Long): Flow<Question?>

   @Query("SELECT * FROM Question WHERE subject_id = :subjectId ORDER BY id")
   fun getQuestions(subjectId: Long): Flow<List<Question>>

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   fun addQuestion(question: Question): Long

   @Update
   fun updateQuestion(question: Question)

   @Delete
   fun deleteQuestion(question: Question)
}

