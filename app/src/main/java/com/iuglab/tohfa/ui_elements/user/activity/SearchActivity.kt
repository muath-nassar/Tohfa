package com.iuglab.tohfa.ui_elements.user.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.iuglab.tohfa.R
import com.iuglab.tohfa.appLogic.models.Product
import com.iuglab.tohfa.ui_elements.user.adapter.searchAdapter
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {
    lateinit var products : ArrayList<Product>
    lateinit var adapter : searchAdapter
    lateinit var db : FirebaseFirestore
    var TAG = "osm"
    var id : String? = null
    var category : String? = null
    var desc : String? = null
    var img : String? = null
    var location : String? = null
    var name : String? = null
    var price : Double? = null
    var purchaseTime : Long? = null
    var rate : Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        products = ArrayList()
        db = FirebaseFirestore.getInstance()
        adapter = searchAdapter(this,products)
        search_recycler.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL,false)
        search_recycler.adapter = adapter

        etSearch.addTextChangedListener (object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                filter(s.toString())
                Log.e(TAG,"Text Changed Lenght (${s!!.toString().length})")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        } )

    }

    fun filter(text : String){
                db.collection("products")
                    .get()
                    .addOnSuccessListener { querySnapshot ->
                        for (document in querySnapshot){
                            id = document.id
                            category = document.get(Product.CATEGORY).toString()
                            desc = document.get(Product.DECRIPTION).toString()
                            img = document.get(Product.IMAGE).toString()
                            var geo: GeoPoint?
                            geo = document.get(Product.LOCATION) as GeoPoint
                            val location = LatLng(geo.latitude,geo.longitude)
                            name = document.get(Product.NAME).toString()
                            price = document.getDouble(Product.PRICE)
                            purchaseTime = document.getLong(Product.PURCHASE_TIMES)
                            rate = document.getLong(Product.RATE)

                            var name = name!!
                            var category = category!!
                            var desc = desc!!

                            var product = Product(name,category,id,price as Double,desc,img,rate!!.toInt(),purchaseTime!!.toInt(),location)
                            if (name.toLowerCase().contains(text.toLowerCase()) && name.length >= 2) {
                                products.clear()
                                products.add(product)
                                adapter.notifyDataSetChanged()
                            }else{
                                products.clear()
                            }
                        }
                    }
                    .addOnFailureListener {
                        Log.e(TAG,"Fail get from firebase")
                    }

//        adapter.filterList(products)
    }
//
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.search_menu,menu)
//
//        var searchItem   = menu!!.findItem(R.id.icon_search)
//        var searchView  = searchItem.actionView as androidx.appcompat.widget.SearchView
//
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
//            androidx.appcompat.widget.SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String): Boolean {
//                adapter.filter.filter(newText)
//                return false
//            }
//        })
//        return true
//    }
}
