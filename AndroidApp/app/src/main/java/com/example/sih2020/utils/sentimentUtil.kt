package com.example.sih2020.utils

import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import com.example.sih2020.R
import com.example.sih2020.api.sentimentAnalysis
import com.example.sih2020.dbClasses.Records
import com.example.sih2020.dbClasses.qa

fun sentimentOnList(qList : MutableList<qa>, schoolId: String, record:Records, recordBitmap: Bitmap, context: Context){
    val analysisScore = sentimentAnalysis()
    val builder: AlertDialog.Builder = AlertDialog.Builder(context)
    builder.setCancelable(false)
    builder.setView(R.layout.progress_dialog)
    val dialog: AlertDialog = builder.create()
    dialog.setTitle("Sentiment Analysis inProgress...")

    qList.forEach {
        if(!dialog.isShowing) {
            dialog.show()
        }
        analysisScore.analyse(context,it,dialog)
    }
    findSchoolAndUpdateRecord(schoolId,record,recordBitmap,context)

}