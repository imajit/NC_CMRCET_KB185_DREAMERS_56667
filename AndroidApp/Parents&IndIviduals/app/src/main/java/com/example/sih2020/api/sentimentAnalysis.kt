package com.example.sih2020.api

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.example.sih2020.dbClasses.qa
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class sentimentAnalysis() {
    fun analyse(context: Context,q: qa,dialog: AlertDialog){
        val client = OkHttpClient()
        val request = OkHttpRequest(client)
        val url = "https://monitoringappsih.herokuapp.com/nlp"
        val map: HashMap<String, String> = hashMapOf("review" to q.answer)
        request.POST(url, map, object: Callback {
            override fun onResponse(call: Call?, response: Response) {
                if(dialog.isShowing){
                    dialog.dismiss()
                }
                val responseData = response.body()?.string()
                (context as Activity).runOnUiThread{
                    try {
                        val json = JSONObject(responseData)
                        Log.d(TAG, "onResponse: ${json.getString("analysis")}")
                        q.analysis = (json.getString("analysis")).toDouble() + 5
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        Log.d(TAG, "onResponse: Error")
                    }
                }

            }

            override fun onFailure(call: Call?, e: IOException?) {
                println("Request Failure."+e!!.message+" "+q.answer)
                if(dialog.isShowing){
                    dialog.dismiss()
                }
            }
        })
    }
}