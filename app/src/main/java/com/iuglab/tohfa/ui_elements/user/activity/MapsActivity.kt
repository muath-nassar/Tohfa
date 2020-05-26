package com.iuglab.tohfa.ui_elements.user.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.iuglab.tohfa.R

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    var lat : Double = 0.0
    var lon : Double = 0.0
    lateinit var title : String
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        lat = intent.getDoubleExtra("lat",0.0)
        lon = intent.getDoubleExtra("lon",0.0)
        title = intent.getStringExtra("title")

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val center = LatLng(lat,lon)
        mMap.addMarker(MarkerOptions().position(center).title(title))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(center,14f))
    }
}
