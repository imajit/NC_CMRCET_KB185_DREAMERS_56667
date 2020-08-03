package com.example.sih2020.dbClasses.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class QuestionEntity (
    val string: String? = null,

    val answer: String? = null
){
    @PrimaryKey(autoGenerate = true)
    var id: Int =0
}