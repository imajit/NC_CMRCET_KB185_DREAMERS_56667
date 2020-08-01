package com.example.sih2020.api

import android.app.Activity
import android.content.ContentValues.TAG
import android.util.Log
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class sentimentAnalysis() {
    fun analyse(answer: String,activity: Activity){
        val client = OkHttpClient()
        val request = OkHttpRequest(client)
        val url = "https://monitoringappsih.herokuapp.com/nlp"
        val map: HashMap<String, String> = hashMapOf("review" to answer)
        request.POST(url, map, object: Callback {
            override fun onResponse(call: Call?, response: Response) {
                val responseData = response.body()?.string()
                activity.runOnUiThread{
                    try {
                        val json = JSONObject(responseData)
                        Log.d(TAG, "onResponse: $json")
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

            }

            override fun onFailure(call: Call?, e: IOException?) {
                println("Request Failure.")
            }
        })
    }
}