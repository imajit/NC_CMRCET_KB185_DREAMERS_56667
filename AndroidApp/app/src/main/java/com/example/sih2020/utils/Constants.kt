package com.example.sih2020.utils

import android.graphics.Bitmap
import com.example.sih2020.dbClasses.Records
import com.example.sih2020.dbClasses.Users
import com.example.sih2020.dbClasses.gpsCoordinates
import com.example.sih2020.dbClasses.qa

class Constants {
    companion object{
        lateinit var photo: Bitmap
        lateinit var schoolId: String
        lateinit var officerId: String
        lateinit var overallReview: String
        lateinit var gpsSnap: gpsCoordinates
        lateinit var creationDate : String
        var questionsCount: Int = 0
        var qList: MutableList<qa> = mutableListOf()
        var mapList: MutableList<Map<String,Records>> = mutableListOf()
    }
}