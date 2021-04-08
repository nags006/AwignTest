package com.spidy.awigntest.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.widget.Toast
import androidx.core.app.ActivityCompat

class Utils
{
    companion object
    {
            fun checkPermissions(context: Context): Boolean {
            val permissionState = ActivityCompat.checkSelfPermission(context.applicationContext, Manifest.permission.ACCESS_COARSE_LOCATION)
            return permissionState == PackageManager.PERMISSION_GRANTED
        }

         fun distance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Float {
            val loc1 = Location("")
            loc1.setLatitude(lat1)
            loc1.setLongitude(lon1)

            val loc2 = Location("")
            loc2.setLatitude(lat2)
            loc2.setLongitude(lon2)
            val distanceInMeters: Float = loc1.distanceTo(loc2)
            return distanceInMeters
        }

        fun Context.toast(message:String)
        {
            Toast.makeText(this,message, Toast.LENGTH_LONG).show()
        }


    }
}