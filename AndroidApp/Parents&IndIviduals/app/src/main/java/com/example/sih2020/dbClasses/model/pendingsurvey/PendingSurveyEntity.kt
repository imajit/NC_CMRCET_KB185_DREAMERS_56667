package com.example.sih2020.dbClasses.model.pendingsurvey

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.sih2020.dbClasses.Records

@Entity
data class PendingSurveyEntity (
    val schoolId: String? = null,

    val records: Records? = null
){
    @PrimaryKey(autoGenerate = true)
    var id: Int =0
}