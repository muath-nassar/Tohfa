package com.iuglab.tohfa.ui_elements.user.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.RecyclerView
import com.iuglab.tohfa.R
import com.iuglab.tohfa.appLogic.models.Product
import com.iuglab.tohfa.ui_elements.user.databse.UserDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.categories_item2_layout.view.*
import kotlinx.android.synthetic.main.categories_item_layout2.view.*
import kotlin.coroutines.coroutineContext

class productAdapter (var context: Activity,var products:ArrayList<Product>, val click: productAdapter.onClickBestProduct?): RecyclerView.Adapter<productAdapter.MyViewHolder>(){
    lateinit var favorite : UserDatabase
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var root =LayoutInflater.from(context).inflate(R.layout.categories_item_layout2,parent,false)
        favorite = UserDatabase(context)
        return MyViewHolder(root)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Picasso.get().load(products[position].img).into(holder.img)
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
            click!!.onClickBestProduct(position)
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var img = itemView.categories_item2_img
        var heart = itemView.categories_item2_btn_favorite
        var card = itemView.categories_item2_card
    }
    interface onClickBestProduct{
        fun onClickBestProduct(position: Int)
    }

}