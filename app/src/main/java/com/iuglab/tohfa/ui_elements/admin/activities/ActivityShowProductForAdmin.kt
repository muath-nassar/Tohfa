package com.iuglab.tohfa.ui_elements.admin.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.android.gms.maps.model.LatLng
import com.iuglab.tohfa.R
import com.iuglab.tohfa.appLogic.adapters.ProductEditAdapter
import com.iuglab.tohfa.appLogic.models.Product
import kotlinx.android.synthetic.main.activity_show_product_for_admin.*
import kotlinx.android.synthetic.main.toolbar_admin.*

class ActivityShowProductForAdmin : AppCompatActivity() {
    val TAG = "mzn"
    val db = Firebase.firestore
    val products = mutableListOf<Product>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_product_for_admin)
        toolbar.title = "Products"
        db.collection("products").get().addOnSuccessListener { querySnapshot ->
            for (document in querySnapshot) {
                val name = document.getString(Product.NAME)!!
                val category = document.getString(Product.CATEGORY)
                val price = document.getDouble(Product.PRICE)
                val id = document.id
                val description = document.getString(Product.DECRIPTION)
                val img = document.getString(Product.IMAGE)
                val rate = document.getDouble(Product.RATE)!!.toInt()
                val purchaseTimes = document.getDouble(Product.PURCHASE_TIMES)!!.toInt()
                val geo = document.getGeoPoint(Product.LOCATION)
                val location = LatLng(geo!!.latitude, geo.longitude)
                products.add(
                    Product(
                        name,
                        category!!,
                        id,
                        price!!,
                        description!!,
                        img,
                        rate,
                        purchaseTimes,
                        location
                    )
                )
            }

            val adapter = ProductEditAdapter(this, products)
            rv_products_for_admin.layoutManager = LinearLayoutManager(this)
            rv_products_for_admin.adapter = adapter
        }.addOnFailureListener { exception ->
            Log.e(TAG,exception.message)

        }

    }
}
