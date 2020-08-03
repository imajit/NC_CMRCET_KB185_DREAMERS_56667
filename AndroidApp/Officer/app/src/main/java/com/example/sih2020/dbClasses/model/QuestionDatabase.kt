package com.example.sih2020.dbClasses.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [QuestionEntity::class],
    version = 1
)
abstract class QuestionDatabase :RoomDatabase(){

    abstract fun getquestionDao() : QuestionDao

    companion object{

        @Volatile private var instance : QuestionDatabase? = null

        private  val LOCK = Any()

        operator fun invoke(context: Context) = instance?: synchronized(LOCK){
            instance?: builddatabase(context).also {
                instance = it
            }
        }


        private  fun builddatabase(context: Context)= Room.databaseBuilder(
            context.applicationContext,
            QuestionDatabase::class.java,
            "questiondatabase"
        ).build()


    }

}