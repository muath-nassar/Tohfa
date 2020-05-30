package com.iuglab.tohfa.ui_elements.user.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.iuglab.tohfa.R
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : AppCompatActivity(),
    OnMapReadyCallback,
    GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener,
    LocationListener {

    var lat : Double = 0.0
    var lon : Double = 0.0
    var title : String? = ""
    private lateinit var mMap: GoogleMap
    var mLocationRequest: LocationRequest? = null
    var mGoogleApiClient: GoogleApiClient? = null
    var mLastLocation: Location? = null
    var mCurrLocationMarker: Marker? = null
    val MY_PERMISSIONS_REQUEST_LOCATION = 1
    val MY_PERMISSIONS_REQUEST_LOCATION2 = 2
    var mFusedLocationClient: FusedLocationProviderClient? = null
    var mLocationCallback : LocationCallback?  = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_maps)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mLocationCallback = LocationCallback()

        lat = intent.getDoubleExtra("lat",31.502715)
        lon = intent.getDoubleExtra("lon",34.465643)
        title = intent.getStringExtra("title")

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onPause() {
        super.onPause()
        //stop location updates when Activity is no longer active
        mFusedLocationClient?.removeLocationUpdates(mLocationCallback)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.mapType = GoogleMap.MAP_TYPE_HYBRID

        val center = LatLng(lat,lon)
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(center,16f))
        val marker = MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        mMap.addMarker(marker.position(center).title(title).snippet("Product Found Here"))
        // Map Settings //
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isScrollGesturesEnabled = true
        mMap.uiSettings.isRotateGesturesEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true
        mMap.uiSettings.isMyLocationButtonEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.setAllGesturesEnabled(true)
        mMap.isMyLocationEnabled = true

        mMap.setOnMyLocationButtonClickListener {

            val service: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val enabled: Boolean = service.isProviderEnabled(LocationManager.GPS_PROVIDER)
            if (!enabled) {
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                Toast.makeText(applicationContext,"Please Turn On GPS, To Find Your Location",Toast.LENGTH_LONG).apply {
                    setGravity(0,0,0)
                    show()
                }
                startActivity(intent)
            }else{
                Toast.makeText(applicationContext,"Your location is being Determined, Please wait...",Toast.LENGTH_LONG).apply {
                    setGravity(0,0,0)
                    show()
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        buildGoogleApiClient()
                    } else {
                        checkLocationPermission()
                    }
                } else {
                    buildGoogleApiClient()
                }
            }

            return@setOnMyLocationButtonClickListener true
        }

        // PolyLine //
        val polyline = mMap.addPolyline(
            PolylineOptions()
                .add(LatLng(31.592924,34.490083))
                .add(LatLng(31.539429,34.566322))
                .add(LatLng(31.500913, 34.513014))
                .add(LatLng(31.364428, 34.363945))
                .add(LatLng(31.304625, 34.372697))
                .add(LatLng(31.220731, 34.266426))
                .add(LatLng(31.322784, 34.219541))
                .add(LatLng(31.410658, 34.317545))
                .add(LatLng(31.520889, 34.429366))
                .add(LatLng(31.592924,34.490083))
                .color(Color.rgb(111, 40, 160))
                .width(12f)
                .visible(true) // defult
        )
        polyline.tag = "GazaBorder"

        mMap.setOnPolylineClickListener { polyline ->
            if (polyline.tag == "GazaBorder"){
                Toast.makeText(applicationContext,"\n   Gaza City Support Only Now   \n", Toast.LENGTH_LONG).show()
            }
        }
    }

    @Synchronized
    protected fun buildGoogleApiClient() {
        mGoogleApiClient = GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build()
        mMap.isMyLocationEnabled = true

        if (mLastLocation != null){
            val lat2: Double? = mLastLocation!!.latitude
            val log2: Double? = mLastLocation!!.longitude
            Toast.makeText(applicationContext,"Your Location in $lat2 - $log2",Toast.LENGTH_LONG).apply {
                setGravity(0,0,0)
                show()
            }
        }
    }

    override fun onConnected(p0: Bundle?) {
        mLocationRequest = LocationRequest()
        mLocationRequest!!.interval = 1000
        mLocationRequest!!.fastestInterval = 1000
        mLocationRequest!!.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this)
        }
        val loc = mLastLocation
        Log.e("location","${loc!!.latitude} - ${loc.longitude}")

        Toast.makeText(applicationContext,"onConnected",Toast.LENGTH_LONG).apply {
            setGravity(0,0,0)
            show()
        }
    }

    override fun onConnectionSuspended(p0: Int) {
        Toast.makeText(applicationContext,"onSuspend",Toast.LENGTH_LONG).apply {
            setGravity(0,0,0)
            show()
        }
    }

    override fun onLocationChanged(location: Location?) {
        mLastLocation = location
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker!!.remove()
        }

        Log.e("location"," Location Changed :${location!!.latitude} - ${location.longitude}")

        //Place current location marker
        val latLng = LatLng(location!!.latitude, location.longitude)
        val markerOptions = MarkerOptions()
        markerOptions.position(latLng)
        markerOptions.title("Your Position")
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        mCurrLocationMarker = mMap.addMarker(markerOptions)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11f))

        // PolyLine //
        mMap.addPolyline(
            PolylineOptions()
                .color(Color.rgb(111, 40, 160))
                .width(9f)
                .add(latLng)
                .add(LatLng(lat,lon))
                .add(LatLng(35.53333,84.5566)))

        Toast.makeText(applicationContext,"onLocationChanged",Toast.LENGTH_LONG).apply {
            setGravity(0,0,0)
            show()
        }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.e("Abd","$p0")

        Toast.makeText(applicationContext,"Failed To Get Your Location \n ${p0.errorMessage}",Toast.LENGTH_LONG).apply {
            setGravity(0,0,0)
            show()
        }
    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                        ActivityCompat.requestPermissions(this@MapsActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), MY_PERMISSIONS_REQUEST_LOCATION2)
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), MY_PERMISSIONS_REQUEST_LOCATION)
            }
        }else{
            ActivityCompat.requestPermissions(this@MapsActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), MY_PERMISSIONS_REQUEST_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient()
                        }
                        mMap.isMyLocationEnabled = true
                    }
                } else {
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show()
                }
                return
            }
            MY_PERMISSIONS_REQUEST_LOCATION2 ->{
//                AlertDialog.Builder(this@MapsActivity)
//                    .setTitle("Location Permission Needed")
//                    .setMessage("This app needs the Location permission, please accept to use location functionality")
//                    .setPositiveButton("OK") { dialogInterface, i ->
//                    }
//                    .create()
//                    .show()
                Toast.makeText(applicationContext,"Permition Denied, Give Me It ....",Toast.LENGTH_LONG).show()
            }
        }
    }

}
