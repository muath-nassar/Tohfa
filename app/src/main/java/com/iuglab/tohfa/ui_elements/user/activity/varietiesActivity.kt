package com.iuglab.tohfa.ui_elements.user.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.ktx.Firebase
import com.iuglab.tohfa.R
import com.iuglab.tohfa.appLogic.models.Product
import com.iuglab.tohfa.ui_elements.user.adapter.productAdapter2
import com.iuglab.tohfa.ui_elements.user.adapter.varietiesAdapter2
import kotlinx.android.synthetic.main.activity_varieties.*

class varietiesActivity : AppCompatActivity() , varietiesAdapter2.onClickItem {
    lateinit var products : ArrayList<Product>
    lateinit var adapter : varietiesAdapter2
    lateinit var db : FirebaseFirestore
    var TAG = "osm"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_varieties)


        db = FirebaseFirestore.getInstance()
        products = ArrayList()
        var category = intent.getStringExtra("category")

        db.collection("products").whereEqualTo("category",category)
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot){
                    varieties_progress.visibility = View.GONE
                    var id = document.id
                    var category = document.get(Product.CATEGORY).toString()
                    var desc = document.get(Product.DECRIPTION).toString()
                    var img = document.get(Product.IMAGE).toString()
                    var geoPiont = document.get(Product.LOCATION) as GeoPoint
                    var location = LatLng(geoPiont.latitude,geoPiont.longitude)
                    var name = document.get(Product.NAME).toString()
                    var price = document.getDouble(Product.PRICE)
                    var purchaseTime = document.getLong(Product.PURCHASE_TIMES)
                    var rate = document.getLong(Product.RATE)

                    var product = Product(name,category,id,price as Double,desc,img,rate!!.toInt(),purchaseTime!!.toInt(),location as LatLng)
                    products.add(product)
                    adapter = varietiesAdapter2(this,products,this)
                    varieties_recycler.layoutManager = GridLayoutManager(this,1)
                    varieties_recycler.adapter = adapter

                }
            }
            .addOnFailureListener {
                Log.e(TAG,"Fail get from firebase")
            }

        if (products.size == 0 ){
            varieties_txt_empty.visibility = View.VISIBLE
            varieties_progress.visibility = View.GONE
        }

    }

    override fun onClick(position: Int) {
       var product = products[position]
        var intent = Intent(this,DetailesActivity::class.java)
        intent.putExtra("id",product.id)
        intent.putExtra("name",product.name)
        intent.putExtra("category",product.category)
        intent.putExtra("img",product.img)
        intent.putExtra("price",product.price)
        intent.putExtra("description",product.description)
        intent.putExtra("purchaseTimes",product.purchaseTimes)
        var lat = product.location.latitude
        var lon = product.location.longitude
        intent.putExtra("lat",lat)
        intent.putExtra("lon",lon)
        startActivity(intent)
    }
}
