package com.iuglab.tohfa.ui_elements.user.adapter

import android.app.Activity
import android.text.method.MetaKeyKeyListener
import android.text.method.TextKeyListener.clear
import android.text.method.TextKeyListener.resetLockedMeta
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.iuglab.tohfa.R
import com.iuglab.tohfa.appLogic.models.Product
import com.iuglab.tohfa.ui_elements.user.databse.UserDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.categories_item_layout3.view.*


class productAdapter2(var activity: Activity , var products:ArrayList<Product>, val click: productAdapter2.onClickProduct?) : RecyclerView.Adapter<productAdapter2.MyViewHolder>() {

    lateinit var favorite : UserDatabase

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var root = LayoutInflater.from(activity).inflate(R.layout.categories_item_layout3,parent,false)
        favorite = UserDatabase(activity)
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
            click!!.onClickProduct(position)
        }


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