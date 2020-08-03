package com.example.sih2020.dbClasses.model.pendingsurvey

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sih2020.dbClasses.model.QuestionDao
import com.example.sih2020.dbClasses.model.QuestionDatabase

abstract class PendingSurveyDatabase: RoomDatabase(){

    abstract fun getPendingSurveyDao() : PendingSurveyDao

    companion object{

        @Volatile private var instance : PendingSurveyDatabase? = null

        private  val LOCK = Any()

        operator fun invoke(context: Context) = instance?: synchronized(LOCK){
            instance?: builddatabase(context).also {
                instance = it
            }
        }


        private  fun builddatabase(context: Context)= Room.databaseBuilder(
            context.applicationContext,
            PendingSurveyDatabase::class.java,
            "PendingSurveyDataBase"
        ).build()


    }

}