package com.iuglab.tohfa.ui_elements.user.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.RecyclerView
import com.iuglab.tohfa.R
import com.iuglab.tohfa.appLogic.models.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.categories_item_layout2.view.*
import kotlin.coroutines.coroutineContext

class productAdapter (var context: Context,var products:ArrayList<Product>, val click: productAdapter.onClickBestProduct?): RecyclerView.Adapter<productAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var root =LayoutInflater.from(context).inflate(R.layout.categories_item_layout2,parent,false)
        return MyViewHolder(root)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Picasso.get().load(products[position].img).into(holder.img)
        holder.card.setOnClickListener {
            click!!.onClickBestProduct(position)
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var img = itemView.categories_item2_img
        var card = itemView.categories_item2_card
    }
    interface onClickBestProduct{
        fun onClickBestProduct(position: Int)
    }

}