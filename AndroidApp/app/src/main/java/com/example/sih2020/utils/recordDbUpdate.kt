package com.example.sih2020.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Toast
import com.example.sih2020.R
import com.example.sih2020.api.faceRecog
import com.example.sih2020.dbClasses.Records
import com.example.sih2020.dbClasses.gpsCoordinates
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import kotlin.math.abs


var database = FirebaseDatabase.getInstance()
var dRef = database.reference
var storage = FirebaseStorage.getInstance()
var sRef = storage.reference

fun updateRecordToDb(schoolIndex: String, record: Records,context: Context){
    val recRef = dRef.child("School").child(schoolIndex).child("Records")
    val builder: AlertDialog.Builder = AlertDialog.Builder(context)
    builder.setCancelable(false)
    builder.setView(R.layout.progress_dialog)
    val dialog: AlertDialog = builder.create()
    dialog.setTitle("Updating Record")
    dialog.show()
    val listener = object : ValueEventListener{
        override fun onCancelled(error: DatabaseError) {
            dialog.dismiss()
        }

        override fun onDataChange(snapshot: DataSnapshot) {
            Log.d("Task 123", "onDataChange: ${snapshot.childrenCount}")
            recRef.child(snapshot.childrenCount.toString()).setValue(record)
            dialog.dismiss()
            Toast.makeText(context,"Uploaded",Toast.LENGTH_LONG).show()
            (context as Activity).onBackPressed()
            clearData(context)
        }
    }
    recRef.addListenerForSingleValueEvent(listener)
}
fun faceRecognise(schoolIndex: String,record: Records,recordBitmap: Bitmap,context: Context){
    val builder: AlertDialog.Builder = AlertDialog.Builder(context)
    builder.setCancelable(false)
    builder.setView(R.layout.progress_dialog)
    val dialog: AlertDialog = builder.create()
    dialog.setTitle("Verifying Face")
    dialog.show()
    var registerBitmap: Bitmap

    val localFile = File.createTempFile("images", "jpg")

    val ref = sRef.child("userImage").child(record.officerId)
    ref.getFile(localFile)
        .addOnSuccessListener {
            registerBitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            dialog.dismiss()
            var faceRec = faceRecog()
            if(faceRec.start(registerBitmap,recordBitmap) > 80){
                updateRecordToDb(schoolIndex,record,context)
                Toast.makeText(context, "Face matched", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(context, "Face not matching", Toast.LENGTH_LONG).show()
            }
        }
        .addOnFailureListener { e ->
            dialog.dismiss()
            Toast.makeText(context, "Face not found", Toast.LENGTH_LONG).show()
        }


}

fun checkGpsRange(schoolIndex: String, record: Records,recordBitmap: Bitmap, context: Context){
    val builder: AlertDialog.Builder = AlertDialog.Builder(context)
    builder.setCancelable(false)
    builder.setView(R.layout.progress_dialog)
    val dialog: AlertDialog = builder.create()
    dialog.setTitle("Validating GPS")
    dialog.show()

    val listener = object : ValueEventListener{
        override fun onCancelled(error: DatabaseError) {
            dialog.dismiss()
        }

        override fun onDataChange(snapshot: DataSnapshot) {
            val gpsVar: gpsCoordinates = gpsCoordinates()
            val x = snapshot.value as Map<*, *>
            gpsVar.lat = (x["lat"]).toString().toDouble()
            gpsVar.long = (x["long"]).toString().toDouble()

            Log.d("GPS", "onDataChange: ${snapshot.value} ${record.gpsLocation.lat} ${record.gpsLocation.long}")

            if(inGpsRange(gpsVar,record.gpsLocation)){
                dialog.dismiss()
                faceRecognise(schoolIndex, record,recordBitmap, context)
            }else {
                dialog.dismiss()
                Toast.makeText(context,"GPS Location Invalid",Toast.LENGTH_LONG).show()

            }
        }
    }
    dRef.child("School").child(schoolIndex).child("SchoolGPS").addListenerForSingleValueEvent(listener)
}

fun inGpsRange(sGps: gpsCoordinates, rGps: gpsCoordinates): Boolean {
    return (abs(sGps.lat - rGps.lat)) < 0.5 && (abs(sGps.long - rGps.long)) < 0.5
}

fun findSchoolAndUpdateRecord(schoolId: String,record: Records,recordBitmap: Bitmap,context: Context){
    val builder: AlertDialog.Builder = AlertDialog.Builder(context)
    builder.setCancelable(false)
    builder.setView(R.layout.progress_dialog)
    val dialog: AlertDialog = builder.create()
    dialog.setTitle("Finding School")
    dialog.show()

    val listener = object : ValueEventListener{
        override fun onCancelled(error: DatabaseError) {
            dialog.dismiss()
        }

        override fun onDataChange(snapshot: DataSnapshot) {
            snapshot.children.forEach {
                if(it.key.toString() == schoolId ){
                    checkGpsRange(it.value.toString(),record,recordBitmap,context)
                    dialog.dismiss()
                    return
                }
            }
            dialog.dismiss()
            Toast.makeText(context,"School Not Found",Toast.LENGTH_LONG).show()
        }

    }
    dRef.child("schoolIndexMap").addListenerForSingleValueEvent(listener)
}