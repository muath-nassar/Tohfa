package com.iuglab.tohfa.ui_elements.user.activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.iuglab.tohfa.R
import com.iuglab.tohfa.appLogic.models.Product
import com.iuglab.tohfa.ui_elements.user.adapter.basketAdapter
import com.iuglab.tohfa.ui_elements.user.databse.UserDatabase
import com.iuglab.tohfa.ui_elements.user.model.itemBascket
import kotlinx.android.synthetic.main.activity_basket.*

class basketActivity : AppCompatActivity() , basketAdapter.onClickBasketProduct {
    lateinit var baskets : ArrayList<itemBascket>
    lateinit var adapter : basketAdapter
    lateinit var currentDB : UserDatabase
    lateinit var db : FirebaseFirestore
   lateinit var progressDialog : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basket)

        basket_progress.visibility = View.VISIBLE

        baskets = ArrayList()
        currentDB = UserDatabase(this)
        db = FirebaseFirestore.getInstance()
        progressDialog = ProgressDialog(this)

        baskets.addAll(currentDB.getAllBasketProducts())
        if (baskets.size == 0 ){
            basketActivity_txt_empty.visibility = View.VISIBLE
            basketActivity_btn_pay.visibility = View.GONE
            basket_progress.visibility = View.GONE
        }
        adapter = basketAdapter(this,baskets,this)
        basket_progress.visibility = View.GONE
        basketActivity_recycler.layoutManager = GridLayoutManager(this,1)
        basketActivity_recycler.adapter = adapter

        basketActivity_btn_pay.setOnClickListener {
            buyAsyncTask().execute()
        }

    }



    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }
    override fun onClickBasketProduct(position: Int) {
        toDetailesAsyncTask().execute(baskets[position].id)
    }

    inner class toDetailesAsyncTask : AsyncTask<String,String,String>(){

        override fun onPreExecute() {
            super.onPreExecute()
            progressDialog.setMessage(getString(R.string.loading))
            progressDialog.setCancelable(false)
            progressDialog.show()
        }

        override fun doInBackground(vararg params: String?): String {
            db.collection("products")
                .get()
                .addOnSuccessListener {querySnapshot ->
                    for (document in querySnapshot){

                        if (document.id == params[0]){
                            val id = document.id
                            val category = document.get(Product.CATEGORY).toString()
                            val desc = document.get(Product.DECRIPTION).toString()
                            val img = document.get(Product.IMAGE).toString()
                            val geoPiont = document.get(Product.LOCATION) as GeoPoint
                            val location = LatLng(geoPiont.latitude,geoPiont.longitude)
                            val name = document.get(Product.NAME).toString()
                            val price = document.getDouble(Product.PRICE)
                            val purchaseTime = document.getLong(Product.PURCHASE_TIMES)
                            val rate = document.getLong(Product.RATE)

                            val intent = Intent(applicationContext, DetailesActivity::class.java)
                            intent.putExtra("id",id)
                            intent.putExtra("name",name)
                            intent.putExtra("category",category)
                            intent.putExtra("img",img)
                            intent.putExtra("price",price)
                            intent.putExtra("description",desc)
                            intent.putExtra("purchaseTimes",purchaseTime)
                            var lat = location.latitude
                            var lon = location.longitude
                            intent.putExtra("lat",lat)
                            intent.putExtra("lon",lon)
                            startActivity(intent)
                        }
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(baseContext,getString(R.string.fail),Toast.LENGTH_LONG).show()
                }
            return "Task Completed"
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if(progressDialog. isShowing ) progressDialog.dismiss()
        }

    }

    inner class buyAsyncTask : AsyncTask<String,String,String>(){

        override fun onPreExecute() {
            super.onPreExecute()
            progressDialog.setMessage(getString(R.string.loading))
            progressDialog.setCancelable(false)
            progressDialog.show()
        }

        override fun doInBackground(vararg params: String?): String {
            var result = "Buying is successful"
            for(b in baskets){
                db.collection("products").document(b.id)
                    .update(
                        "purchaseTimes", b.purchaseTimes + b.contity
                    ).addOnSuccessListener {
                        result = getString(R.string.buying_success)
                    }.addOnFailureListener {
                        result = getString(R.string.buying_fail)
                    }
            }

            return result
        }



        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if(progressDialog. isShowing ) progressDialog.dismiss()

                currentDB.deleteBasckets()
                adapter.notifyDataSetChanged()
                val alertDialog = AlertDialog.Builder(this@basketActivity)
                alertDialog.setTitle(result)
                alertDialog.setPositiveButton(R.string.finish) { dialogInterface, i ->
                    Log.e("osm", "Finish")
                }
                alertDialog.create().show()


        }
    }
}
