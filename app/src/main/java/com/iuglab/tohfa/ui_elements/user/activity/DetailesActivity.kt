package com.iuglab.tohfa.ui_elements.user.activity

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.iuglab.tohfa.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detailes.*
import kotlinx.android.synthetic.main.fragment_admin_operations.*

class DetailesActivity : AppCompatActivity() , OnMapReadyCallback {


lateinit var id :String
    lateinit var name : String
    lateinit var category : String
    lateinit var img :String
    var price : Double = 0.0
    lateinit var description : String
    var purchaseTimes : Int = 0
    var lat :Double = 0.0
    var lon :Double = 0.0
    var moreVisiblity = false
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailes)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.detailes_map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        id = intent.getStringExtra("id")
        name =intent.getStringExtra("name")
        category =intent.getStringExtra("category")
        img = intent.getStringExtra("img")
        price = intent.getDoubleExtra("price",-1.0)
        description =intent.getStringExtra("description")
        purchaseTimes =intent.getIntExtra("purchaseTimes",-1)
        lat =intent.getDoubleExtra("lat",-1.0)
        lon =intent.getDoubleExtra("lon",-1.0)

        if ((id.isNotEmpty() && name.isNotEmpty()) && category.isNotEmpty()
            && price!= null && description.isNotEmpty() && purchaseTimes!=null
                && lat!=null && lon!=null ){

            Picasso.get().load(img).into(detailes_img)
            detailes_txt_name.text = name
            detailes_txt_price.text = "$"+price.toString()
            if (description.length>100){
               var desc = description.substring(0,100)
                detailes_txt_detailes.text = desc + " ..."
                detailes_txt_more.text = "see more .."
                detailes_img_more.setImageResource(R.drawable.b_seemore)
                moreVisiblity = false
            }else{
                detailes_txt_detailes.text = description
//                detailes_txt_more.text = "see less .."
//                detailes_img_more.setImageResource(R.drawable.b_see_less)
                detailes_linear_more.visibility = View.GONE
                moreVisiblity = true
            }

        }

        detailes_txt_more.setOnClickListener {
            if (!moreVisiblity){
                detailes_txt_detailes.text = description
                detailes_txt_more.text = "see less .."
                detailes_img_more.setImageResource(R.drawable.b_see_less)
                moreVisiblity = true

            }else{
                var desc = description.substring(0,100)
                detailes_txt_detailes.text = desc + " ..."
                detailes_txt_more.text = "see more .."
                detailes_img_more.setImageResource(R.drawable.b_seemore)
                moreVisiblity = false
            }
        }

        detailes_img_contity_plus.setOnClickListener {
            var contity = detailes_txt_contity.text.toString().toInt()
            var newContity = contity + 1
            detailes_txt_contity.text = newContity.toString()
        }
        detailes_img_contity_minus.setOnClickListener {
            var contity = detailes_txt_contity.text.toString().toInt()
            if(contity > 0){
                var newContity = contity - 1
                detailes_txt_contity.text = newContity.toString()
            }
        }


    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
        mMap.uiSettings.isCompassEnabled = true
        val myPosition = LatLng(lat,lon)
        mMap.addMarker(MarkerOptions().position(myPosition).title(name))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myPosition,10f))
    }

}
