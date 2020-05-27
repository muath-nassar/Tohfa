package com.iuglab.tohfa.ui_elements.user.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.util.rangeTo
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.Query
import com.iuglab.tohfa.R
import com.iuglab.tohfa.appLogic.models.Location
import com.iuglab.tohfa.appLogic.models.Product
import com.iuglab.tohfa.ui_elements.user.activity.DetailesActivity
import com.iuglab.tohfa.ui_elements.user.adapter.productAdapter
import com.iuglab.tohfa.ui_elements.user.adapter.productAdapter2
import kotlinx.android.synthetic.main.fragment_product.view.*
import kotlin.math.log


/**
 * A simple [Fragment] subclass.
 */
class ProductFragment : Fragment() , productAdapter2.onClickProduct , productAdapter.onClickBestProduct {
    lateinit var root :View
    lateinit var arrayBestProduct : ArrayList<Product>
    lateinit var arrayProduct : ArrayList<Product>
    lateinit var bestAdapter : productAdapter
     lateinit var adapter : productAdapter2
    var db = FirebaseFirestore.getInstance()
    var TAG = "osm"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_product, container, false)
        arrayBestProduct = ArrayList()
        arrayProduct = ArrayList()

        db.collection("products")
            .limit(5).orderBy(Product.PURCHASE_TIMES,Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {
                    querySnapshot ->
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
                    arrayBestProduct.add(product)
                    bestAdapter = productAdapter(activity!!,arrayBestProduct,this)
                    root.frProduct_recyclerHorizontal.layoutManager = LinearLayoutManager(activity!!,RecyclerView.HORIZONTAL,false)
                    root.frProduct_recyclerHorizontal.adapter = bestAdapter
                }
            }
            .addOnFailureListener {
                Log.e(TAG,"Fail get from firebase")
            }


//        arrayProduct.add(Product())
//        arrayProduct.add(Product())
//        arrayProduct.add(Product())
//        arrayProduct.add(Product())
//        arrayProduct.add(Product())
//        arrayProduct.add(Product())
//        arrayProduct.add(Product())
//        arrayProduct.add(Product())
//        arrayProduct.add(Product())
//        arrayProduct.add(Product())
//        arrayProduct.add(Product())
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
                    arrayProduct.add(product)
                    adapter = productAdapter2(activity!!,arrayProduct,this)
                    root.frProduct_recyclerVertical.layoutManager = GridLayoutManager(activity!!,2)
                    root.frProduct_recyclerVertical.adapter = adapter

                }
            }
            .addOnFailureListener {
                Log.e(TAG,"Fail get from firebase")
            }


        return root
    }

    override fun onClickProduct(position: Int) {
        var product = arrayProduct[position]
        var intent = Intent(activity!!, DetailesActivity::class.java)
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

    override fun onClickBestProduct(position: Int) {
        var product = arrayBestProduct[position]
        var intent = Intent(activity!!, DetailesActivity::class.java)
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
