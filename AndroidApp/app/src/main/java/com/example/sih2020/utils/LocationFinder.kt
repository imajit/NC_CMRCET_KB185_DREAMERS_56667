package com.example.sih2020.utils

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import androidx.appcompat.app.AlertDialog

class LocationFinder(var context: Context) : Service(),
    LocationListener {
    private var longitude: Double = 0.0
    private var latitude: Double = 0.0
    var isGPSEnabled = false

    // flag for network status
    var isNetworkEnabled = false

    // flag for GPS status
    var canGetLocation = false
    var location: Location? = null


    // Declaring a Location Manager
    protected var locationManager: LocationManager? = null
    override fun onLocationChanged(location: Location) {}
    override fun onStatusChanged(
        provider: String,
        status: Int,
        extras: Bundle
    ) {
    }

    override fun onProviderEnabled(provider: String) {}
    override fun onProviderDisabled(provider: String) {}
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    fun getLatitude(): Double {
        if (location != null) {
            return location!!.latitude
        }
        return 0.0
    }

    fun getLongitude(): Double {
        if (location != null) {
            return location!!.longitude
        }
        return 0.0
    }

    fun canGetLocation(): Boolean {
        return canGetLocation
    }

    fun showSettingsAlert() {
        val alertDialog =
            AlertDialog.Builder(context)
        alertDialog.setTitle("GPS settings")
        alertDialog.setMessage("GPS is not enabled.Do you want to go to settings menu ?")
        alertDialog.setPositiveButton("Settings") { dialog, which ->
            val intent =
                Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            context.startActivity(intent)
        }
        alertDialog.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
        alertDialog.show()
    }

    companion object {
        // The minimum distance to change Updates in meters
        private const val MIN_DISTANCE_CHANGE_FOR_UPDATES: Long = 10 // 10 meters

        // The minimum time between updates in milliseconds
        private const val MIN_TIME_BW_UPDATES = 200 * 10 * 1 // 2 seconds
            .toLong()

        @SuppressLint("MissingPermission")
        fun getLocation(locationFinder: LocationFinder): Location? {
            try {
                locationFinder.locationManager = locationFinder.context
                    .getSystemService(Context.LOCATION_SERVICE) as LocationManager
                // getting GPS status
                locationFinder.isGPSEnabled = locationFinder.locationManager!!
                    .isProviderEnabled(LocationManager.GPS_PROVIDER)
                // getting network status
                locationFinder.isNetworkEnabled = locationFinder.locationManager!!
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER)
                if (!locationFinder.isGPSEnabled && !locationFinder.isNetworkEnabled) {
                    // no network provider is enabled
                    // Log.e(“Network-GPS”, “Disable”);
                } else {
                    locationFinder.canGetLocation = true
                    // First get location from Network Provider
                    if (locationFinder.isNetworkEnabled) {
                        locationFinder.locationManager!!.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(), locationFinder
                        )
                        // Log.e(“Network”, “Network”);
                        if (locationFinder.locationManager != null) {
                            locationFinder.location = locationFinder.locationManager!!
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                            if (locationFinder.location != null) {
                                locationFinder.latitude = locationFinder.location!!.latitude
                                locationFinder.longitude = locationFinder.location!!.longitude
                            }
                        }
                    } else  // if GPS Enabled get lat/long using GPS Services
                        if (locationFinder.isGPSEnabled) {
                            if (locationFinder.location == null) {
                                locationFinder.locationManager!!.requestLocationUpdates(
                                    LocationManager.GPS_PROVIDER,
                                    MIN_TIME_BW_UPDATES,
                                    MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(),
                                    locationFinder
                                )
                                //Log.e(“GPS Enabled”, “GPS Enabled”);
                                if (locationFinder.locationManager != null) {
                                    locationFinder.location = locationFinder.locationManager!!
                                        .getLastKnownLocation(LocationManager.GPS_PROVIDER)
                                    if (locationFinder.location != null) {
                                        locationFinder.latitude = locationFinder.location!!.latitude
                                        locationFinder.longitude = locationFinder.location!!.longitude
                                    }
                                }
                            }
                        }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return locationFinder.location
        }
    }

    init {
        Companion.getLocation(this)
    }
}