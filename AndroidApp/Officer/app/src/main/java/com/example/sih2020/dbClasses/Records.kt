package com.example.sih2020.dbClasses

import java.text.DateFormat

class Records {
    lateinit var gpsLocation : gpsCoordinates
    lateinit var officerId : String
    lateinit var questions : MutableList<qa>
    lateinit var creationDate : String
    lateinit var overallReview : String

}