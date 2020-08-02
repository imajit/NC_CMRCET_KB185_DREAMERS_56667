package com.example.sih2020.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.sih2020.classes.PendingClass
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun loadData(context: Context): PendingClass {

    val sharedPreferences = context.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
    val gson = Gson()
    val jsonstring: String? = sharedPreferences.getString("School Survey List", null)
    val type = object : TypeToken<PendingClass?>() {}.type


    //pendingClass = gson.fromJson(jsonstring,type)
    var pc = PendingClass()
    pc = gson.fromJson(jsonstring, type)
    Log.d("Log", "loadData: ${pc.schoolId} ${pc.records.officerId}")

    if (pc == null) {
        Toast.makeText(context, "Pending class is null", Toast.LENGTH_SHORT).show()
    }

    return pc
}

fun clearData(context: Context) {

    val sharedPreferences = context.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.clear()
    editor.apply()
}