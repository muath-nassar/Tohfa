package com.iuglab.tohfa.ui_elements.user.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.iuglab.tohfa.R
import com.iuglab.tohfa.ui_elements.user.databse.UserDatabase
import com.iuglab.tohfa.ui_elements.user.model.itemBascket
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detailes.*
import kotlin.math.log

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
    lateinit var db : FirebaseFirestore
    var contity :Double = 0.0
    lateinit var currentDB : UserDatabase
    lateinit var favorite : UserDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailes)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.detailes_map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        db = FirebaseFirestore.getInstance()
        currentDB = UserDatabase(this)
        favorite = UserDatabase(this)

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
            detailes_txt_price.text = price.toString()
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

        if (favorite.searchAboutProduct(id).size > 0){
            detailes_img_favorite.setImageResource(R.drawable.ic_favorite_black_24dp)
        }else{
            detailes_img_favorite.setImageResource(R.drawable.ic_favorite_border)
        }

        if(currentDB.searchAboutBasket(id).size>0){
            detailes_img_addBascket.setImageResource(R.drawable.ic_shopping2)
        }else{
            detailes_img_addBascket.setImageResource(R.drawable.ic_add_shopping)
        }

        detailes_img_favorite.setOnClickListener {
            if (favorite.searchAboutProduct(id).size > 0){
                var result = favorite.deleteProducts(id)
                if(result){
                    detailes_img_favorite.setImageResource(R.drawable.ic_favorite_border)
                    Snackbar.make(it,getString(R.string.remove_from_favorite), Snackbar.LENGTH_LONG).show()
                }
            }else{
                var result = favorite.insertProduct(id)
                if(result){
                    detailes_img_favorite.setImageResource(R.drawable.ic_favorite_black_24dp)
                    Snackbar.make(it,getString(R.string.added_favorite), Snackbar.LENGTH_LONG).show()
                }
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
            if(contity > 1){
                var newContity = contity - 1
                detailes_txt_contity.text = newContity.toString()
            }
        }

        detailes_txt_clicked.setOnClickListener{
           var intent = Intent(applicationContext,MapsActivity::class.java)
            intent.putExtra("lat",lat)
            intent.putExtra("lon",lon)
            intent.putExtra("title",name)
            startActivity(intent)
        }

        detailes_linear_puy.setOnClickListener {
            var price = detailes_txt_price.text.toString().toDouble()
             contity = detailes_txt_contity.text.toString().toDouble()
            var fullPrice = price * contity
            var name = detailes_txt_name.text.toString()

            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setIcon(R.drawable.ic_dollars_fill)
            alertDialog.setTitle(getString(R.string.confirm_pay))
            alertDialog.setMessage(getString(R.string.will_pay) + fullPrice.toString() +getString(R.string.about) +contity.toString()+getString(
                            R.string.items)+ name +getString(
                            R.string.ech_one) + price.toString())
            alertDialog.setCancelable(true)
            alertDialog.setPositiveButton(getString(R.string.compelte)){ dialogInterface, i ->
                db.collection("products").document(id)
                    .update(
                        "purchaseTimes", purchaseTimes + detailes_txt_contity.text.toString().toInt()
                    ).addOnSuccessListener {
                        val alertDialog2 = AlertDialog.Builder(this)
                        alertDialog2.setTitle(getString(R.string.paying_success))
                        alertDialog2.setIcon(R.drawable.ic_success)
                        alertDialog2.setPositiveButton(getString(R.string.finish)){dialogInterface, i ->
                            Log.e("osm","Finish")
                        }
                        alertDialog2.show()

                    }.addOnFailureListener {
                        val alertDialog2 = AlertDialog.Builder(this)
                        alertDialog2.setTitle(getString(R.string.fail))
                        alertDialog2.setIcon(R.drawable.ic_fail)
                        alertDialog2.setPositiveButton(getString(R.string.finish)){dialogInterface, i ->
                            Log.e("osm","Finish")
                        }
                        alertDialog2.show()
                    }
            }
            alertDialog.show()

        }

        detailes_linear_addBascket.setOnClickListener {
            var basket = itemBascket(id,img,name,price,detailes_txt_contity.text.toString().toInt(),purchaseTimes)

//            var baskets = currentDB.getAllBasketProducts()
//            for(b in baskets){
//                if(b.id == basket.id){
//                    Log.e("osm","YES")
//                }else{
//                    Log.e("osm","NO")
//                }
//            }
            if (currentDB.searchAboutBasket(basket.id).size > 0){
                val result = currentDB.deleteItemBascket(basket.id)
                if (result){
                    detailes_img_addBascket.setImageResource(R.drawable.ic_add_shopping)
                    Snackbar.make(it,getString(R.string.delete_from_basket), Snackbar.LENGTH_LONG).show()
                }else{
                    Log.e("osm","NOT delete")
                }
            }else{
                val result = currentDB.insertBasket(basket)
                if (result){
                    Log.e("osm","insert")
                    detailes_img_addBascket.setImageResource(R.drawable.ic_shopping2)
                    Snackbar.make(it,getString(R.string.add_to_basket), Snackbar.LENGTH_LONG).show()
                }else{
                    Log.e("osm","NOT insert")
                }
             }
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        mMap.uiSettings.isCompassEnabled = true
        val myPosition = LatLng(lat,lon)
        mMap.addMarker(MarkerOptions().position(myPosition).title(name))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myPosition,13f))
    }



}
