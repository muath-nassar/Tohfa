package com.iuglab.tohfa.ui_elements.user.adapter

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iuglab.tohfa.R
import com.iuglab.tohfa.appLogic.models.Product
import com.iuglab.tohfa.ui_elements.user.databse.UserDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.categories_item_layout3.view.*
import kotlin.properties.Delegates

class productAdapter2(var activity: Activity , var products:ArrayList<Product>, val click: productAdapter2.onClickProduct?) : RecyclerView.Adapter<productAdapter2.MyViewHolder>(){

    lateinit var favorite : UserDatabase
    lateinit var favorites : ArrayList<String>
    var isFavorite : Boolean ? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var root = LayoutInflater.from(activity).inflate(R.layout.categories_item_layout3,parent,false)
        favorite = UserDatabase(activity)
        favorites = favorite.getAllKeyProduct()
        isFavorite = false
        return MyViewHolder(root)
    }

    override fun getItemCount(): Int {
       return products.size
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Picasso.get().load(products[position].img).into(holder.img)
        holder.title.text = products[position].name
        holder.price.text = "$" + products[position].price.toString()
        for (f in favorites){
            if (f == products[position].id){
                holder.heart.setImageResource(R.drawable.b_favorite)
                isFavorite = true
            }else{
                holder.heart.setImageResource(R.drawable.ic_favorite_border)
                isFavorite = false
            }
        }
        holder.card.setOnClickListener {
            click!!.onClickProduct(position)
        }

//        holder.heart.setOnClickListener {
//            if (!isFavorite!!){
//                val result = favorite.insertProduct(products[position].id.toString())
//                if (result){
//                    holder.heart.setImageResource(R.drawable.b_favorite)
//                    isFavorite = true
//                }
//            }else{
//                val result = favorite.deleteProducts(products[position].id.toString())
//                if (result){
//                    holder.heart.setImageResource(R.drawable.ic_favorite_border)
//                    isFavorite = false
//                }else{
//                    Log.e("osm","not delete")
//                }
//            }

//            Log.e("osm","CLICKED")
//            favorites = favorite.getAllKeyProduct()
//            for (f in favorites){
//                Log.e("osm","FOR")
//                if (f == products[position].id){
//                    Log.e("osm","EXIST")
//                    val result = favorite.deleteProducts(products[position].id.toString())
//                    if (result) {
//                        Log.e("osm","DELETE")
//                        holder.heart.setImageResource(R.drawable.ic_favorite_border)
//                        isFavorite = true
//                    }
//                }else{
//                    Log.e("osm","NO FOR")
//                    val result = favorite.insertProduct(products[position].id.toString())
//                    if (result) {
//                        Log.e("osm","INSERT")
//                        holder.heart.setImageResource(R.drawable.b_favorite)
//                        isFavorite = false
//                    }
//
//                }
//            }
     //   }
    }
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var img = itemView.categories_item_layout3_img
        var title = itemView.categories_item_layout3_title
        var price = itemView.categories_item_layout3_cost
        var heart = itemView.categories_item_layout3_btn_favorite
        var card = itemView.categories_item_layout3_card

    }

    interface onClickProduct{
        fun onClickProduct(position: Int)
    }
}