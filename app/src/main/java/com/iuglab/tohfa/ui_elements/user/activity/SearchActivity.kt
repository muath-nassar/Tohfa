package com.iuglab.tohfa.ui_elements.user.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.iuglab.tohfa.R
import com.iuglab.tohfa.appLogic.models.Product
import com.iuglab.tohfa.ui_elements.user.adapter.searchAdapter
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() , searchAdapter.onClickSearchProduct {
    lateinit var products : ArrayList<Product>
    lateinit var adapter : searchAdapter
    lateinit var db : FirebaseFirestore
    var TAG = "osm"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setSupportActionBar(search_toolbar)

        products = ArrayList<Product>()
        db = FirebaseFirestore.getInstance()

        db.collection("products")
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot){
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
                    adapter = searchAdapter(this,products,null)
                    search_recycler.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL,false)
                    search_recycler.adapter = adapter
                }
            }
            .addOnFailureListener {
                Log.e(TAG,"Fail get from firebase")
            }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu,menu)

        var searchItem   = menu!!.findItem(R.id.icon_search)
        var searchView  = searchItem.actionView as androidx.appcompat.widget.SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
        return true
    }



    override fun onClickSearchProduct(position: Int) {
        var product = products[position]
        var intent = Intent(this, DetailesActivity::class.java)
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
