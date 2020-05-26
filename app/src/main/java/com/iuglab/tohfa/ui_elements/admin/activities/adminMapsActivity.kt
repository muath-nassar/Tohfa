package com.iuglab.tohfa.ui_elements.admin.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.iuglab.tohfa.R

class adminMapsActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var i: Intent

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_maps)
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
        val gaza = LatLng(31.501288, 34.433872)
        mMap.addMarker(MarkerOptions().position(gaza).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gaza, 14f))
        mMap.setOnMapClickListener { latLng ->
            val lat = latLng.latitude.toString()
            val lng = latLng.longitude.toString()
            AlertDialog.Builder(this).setTitle("Confirm selection")
                .setMessage("You have selected the point lat = $lat , lng = $lng\n please confirm selection.")
                .setPositiveButton("Accept", { _, _ ->
                    when (intent.getStringExtra("id")) {
                        "add" -> i = Intent(this, ActivityAddProduct::class.java)
                        "update" ->{
                            i = Intent(this, ActivityUpdateProduct::class.java)
                            val pID = intent.getStringExtra("p_id")
                            i.putExtra("p_id",pID)
                        }

                    }

                    i.putExtra("lat", lat)
                    i.putExtra("lng", lng)
                    val name = intent.getStringExtra("pName")
                    val price = intent.getStringExtra("pPrice")
                    val desc = intent.getStringExtra("pDescription")
                    val cat = intent.getStringExtra("pCategory")
                    val img = intent.getParcelableExtra<Uri>("pImage")
                    val imgFromFB = intent.getStringExtra("fbImage")

                    i.putExtra("pName", name)
                    i.putExtra("pPrice", price)
                    i.putExtra("pDescription", desc)
                    i.putExtra("pCategory", cat)
                    i.putExtra("pImage", img)
                    i.putExtra("id","map")
                    i.putExtra("fbImage",imgFromFB)
                    startActivity(i)
                    finish()
                }).setNegativeButton("Decline", null).show()
        }
    }
}
