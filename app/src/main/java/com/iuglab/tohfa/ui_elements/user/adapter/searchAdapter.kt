package com.iuglab.tohfa.ui_elements.user.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.iuglab.tohfa.R
import com.iuglab.tohfa.appLogic.models.Product
import com.iuglab.tohfa.ui_elements.user.activity.DetailesActivity
import com.iuglab.tohfa.ui_elements.user.databse.UserDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.categories_item_layout3.view.*
import java.util.*
import kotlin.collections.ArrayList

class searchAdapter (var context: Context, var products:ArrayList<Product>) : RecyclerView.Adapter<searchAdapter.MyViewHolder>() {

    lateinit var favorite : UserDatabase
    lateinit var favorites : ArrayList<String>
    var productsFull = ArrayList<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var root = LayoutInflater.from(context).inflate(R.layout.categories_item_layout3,parent,false)
        favorite = UserDatabase(context)
        favorites = favorite.getAllKeyProduct()
        return MyViewHolder(root)
    }

    override fun getItemCount(): Int {
        return products.size
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Picasso.get().load(products[position].img).into(holder.img)
        holder.title.text = products[position].name
        holder.price.text = "$" + products[position].price.toString()

        if (favorite.searchAboutProduct(products[position].id!!).size > 0){
            holder.heart.setImageResource(R.drawable.ic_favorite_black_24dp)
        }else{
            holder.heart.setImageResource(R.drawable.ic_favorite2)
        }

        holder.heart.setOnClickListener {
            if (favorite.searchAboutProduct(products[position].id!!).size > 0){
                var result = favorite.deleteProducts(products[position].id!!)
                if(result){
                    holder.heart.setImageResource(R.drawable.ic_favorite2)
                }
            }else{
                var result = favorite.insertProduct(products[position].id!!)
                if(result){
                    holder.heart.setImageResource(R.drawable.ic_favorite_black_24dp)
                }
            }
        }




        holder.card.setOnClickListener {
//            click!!.onClickSearchProduct(position)

            Log.e("search","$position")
            var product = products[position]
            var lat = product.location.latitude
            var lon = product.location.longitude

            var i = Intent(context.applicationContext, DetailesActivity::class.java)
            i.putExtra("id",product.id)
            i.putExtra("name",product.name)
            i.putExtra("category",product.category)
            i.putExtra("img",product.img)
            i.putExtra("price",product.price)
            i.putExtra("description",product.description)
            i.putExtra("purchaseTimes",product.purchaseTimes)
            i.putExtra("lat",lat)
            i.putExtra("lon",lon)
            context.startActivity(i)

        }


    }
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var img = itemView.categories_item_layout3_img
        var title = itemView.categories_item_layout3_title
        var price = itemView.categories_item_layout3_cost
        var heart = itemView.categories_item_layout3_btn_favorite
        var card = itemView.categories_item_layout3_card
    }

    fun filterList(filteredList : ArrayList<Product>){
        products = filteredList
        notifyDataSetChanged()
    }

}