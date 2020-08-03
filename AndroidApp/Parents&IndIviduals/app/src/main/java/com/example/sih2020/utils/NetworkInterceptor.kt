package com.example.sih2020.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Message
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


class NetworkInterceptor(context: Context): Interceptor{

    private val applicationContext= context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isInternetAvailable())
            throw NOInternetException("Make sure You have an Active internet connection")

        return chain.proceed(chain.request())
    }

    private fun isInternetAvailable():Boolean{

        val connectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        connectivityManager.activeNetworkInfo.also {
            return it != null && it.isConnected
        }
    }

    class NOInternetException(message: String):IOException(message)
}