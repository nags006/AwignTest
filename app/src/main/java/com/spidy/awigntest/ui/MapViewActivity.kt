package com.spidy.awigntest.ui

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.spidy.awigntest.R
import com.spidy.awigntest.util.Utils
import com.spidy.awigntest.util.Utils.Companion.toast
import kotlinx.android.synthetic.main.activity_maps.*


class MapViewActivity : AppCompatActivity(),OnMapReadyCallback,GoogleMap.OnMarkerDragListener
{
    private lateinit var mMap: GoogleMap
    private var locationManager : LocationManager? = null
    private lateinit var userLoc : LatLng
    var mapCircle: Circle? = null


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        imggps.setOnClickListener(View.OnClickListener {
            getLocation()
        })
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?
        if(!Utils.checkPermissions(this))
        {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 100)
        }
        else
        {
            getLocation()
        }
    }

    private fun getLocation()
    {
        try
        {
            locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener)
        }
        catch (ex: SecurityException)
        {
            toast("Something went wrong")
        }
    }

    private  val locationListener : LocationListener = object : LocationListener
    {
        override fun onLocationChanged(p0: Location)
        {
            userLoc = LatLng(p0.latitude, p0.longitude)
            if(this@MapViewActivity :: mMap.isInitialized)
            {
                mMap.clear()
                mapCircle?.remove()
                mMap.addMarker(MarkerOptions().position(userLoc).title("Current Location").draggable(true))
                mMap.moveCamera(CameraUpdateFactory.newLatLng(userLoc))
                mapCircle =  mMap.addCircle(
                        CircleOptions()
                                .center(userLoc)
                                .radius(10000.0)
                                .strokeColor(Color.BLUE)
                                .fillColor(Color.parseColor("#e8f4f8"))
                )
            }

        }

    }

    override fun onMapReady(googleMap: GoogleMap)
    {
        mMap = googleMap
        mMap.setOnMarkerDragListener(this)
        toast("User Map Loaded successfully")
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            getLocation()
        }
        else
        {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 100)
        }
    }

    override fun onMarkerDragStart(p0: Marker?)
    {

    }

    override fun onMarkerDrag(p0: Marker)
    {

    }

    override fun onMarkerDragEnd(p0: Marker)
    {
        val dist: Float =  Utils.distance(userLoc.latitude, userLoc.longitude, p0.position.latitude, p0.position.longitude)
        if(dist >= 10000)
        {
            p0.position = userLoc
            toast("User restricted outside of geographical region")
        }

    }



}