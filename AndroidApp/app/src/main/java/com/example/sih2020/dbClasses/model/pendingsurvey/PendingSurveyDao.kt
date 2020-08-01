package com.example.sih2020.dbClasses.model.pendingsurvey

import androidx.room.*

@Dao
    interface PendingSurveyDao {

        @Insert
        suspend fun addList( note: PendingSurveyEntity)


        @Query("select * from PendingSurveyEntity order by id asc")
        suspend fun getAllList(): List<PendingSurveyEntity>



        @Update
        suspend fun updateList(note: PendingSurveyEntity)

        @Delete
        suspend fun deleteList(list: PendingSurveyEntity)


}