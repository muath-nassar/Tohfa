package com.iuglab.tohfa.ui_elements.user.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.iuglab.tohfa.R
import com.iuglab.tohfa.appLogic.models.Product
import com.iuglab.tohfa.ui_elements.user.adapter.favoriteAdapter
import com.iuglab.tohfa.ui_elements.user.databse.UserDatabase
import kotlinx.android.synthetic.main.activity_favorite.*

class favoriteActivity : AppCompatActivity() ,favoriteAdapter.onClickFavoriteProduct {

    lateinit var products : ArrayList<Product>
    lateinit var keys : ArrayList<String>
    lateinit var adapter : favoriteAdapter
    lateinit var currentDB : UserDatabase
    lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        products = ArrayList<Product>()
        keys = ArrayList<String>()
        currentDB = UserDatabase(this)
        db = FirebaseFirestore.getInstance()
        keys.addAll(currentDB.getAllKeyProduct())

        for (k in keys){

            db.collection("products")
                .get()
                .addOnSuccessListener { querySnapshot ->
                    for (document in querySnapshot) {

                        if (document.id == k) {
                            val id = document.id
                            val category = document.get(Product.CATEGORY).toString()
                            val desc = document.get(Product.DECRIPTION).toString()
                            val img = document.get(Product.IMAGE).toString()
                            val geoPiont = document.get(Product.LOCATION) as GeoPoint
                            val location = LatLng(geoPiont.latitude, geoPiont.longitude)
                            val name = document.get(Product.NAME).toString()
                            val price = document.getDouble(Product.PRICE)
                            val purchaseTime = document.getLong(Product.PURCHASE_TIMES)
                            val rate = document.getLong(Product.RATE)

                            var product = Product(name,category,id,price as Double,desc,img,rate!!.toInt(),purchaseTime!!.toInt(),location as LatLng)
                            products.add(product)

                            adapter = favoriteAdapter(this,products,this)
                            favorite_rtecycler.layoutManager = GridLayoutManager(this,1)
                            favorite_rtecycler.adapter = adapter
                        }

                    }
                }

        }



    }

    override fun onClickFavoriteProduct(position: Int) {
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
