package com.example.sih2020.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback
import androidx.core.content.ContextCompat
import java.util.*

class Permissions : OnRequestPermissionsResultCallback {
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissionsList: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            MULTIPLE_PERMISSIONS -> {
                if (grantResults.isNotEmpty()) {
                    var permissionsDenied = ""
                    for (per in permissionsList) {
                        if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                            permissionsDenied += """

                                $per
                                """.trimIndent()
                            //a.finishAffinity();
                        }
                    }
                    //Show permissionsDenied
                    updateViews(permissionsDenied)
                }
                return
            }
        }
    }

    private fun updateViews(permissionsDenied: String) {
        Log.e("Permissions denied", permissionsDenied)
    }

    companion object {
        const val MULTIPLE_PERMISSIONS = 10 // code you want.
        fun checkPermissions(activity: Activity?): Boolean {
            var permissions = arrayOfNulls<String>(0)
            permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                arrayOf(
                    Manifest.permission.USE_BIOMETRIC,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.CAMERA
                )
            } else {
                arrayOf(
                    Manifest.permission.USE_FINGERPRINT,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.CAMERA
                )
            }
            var result: Int
            val listPermissionsNeeded: MutableList<String?> =
                ArrayList()
            for (p in permissions) {
                result = ContextCompat.checkSelfPermission(activity!!, p!!)
                if (result != PackageManager.PERMISSION_GRANTED) {
                    listPermissionsNeeded.add(p)
                }
            }
            if (listPermissionsNeeded.isNotEmpty()) {
                ActivityCompat.requestPermissions(
                    activity!!,
                    listPermissionsNeeded.toTypedArray(),
                    MULTIPLE_PERMISSIONS
                )
                return false
            }
            return true
        }
    }
}