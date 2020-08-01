package com.example.sih2020.dbClasses.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface QuestionDao {

    @Insert
    suspend fun addanswer( note: QuestionEntity)


    @Query("select * from QuestionEntity order by id asc")
    suspend fun getAllAnswers(): List<QuestionEntity>



    @Update
    suspend fun updateanswer(note: QuestionEntity)
}