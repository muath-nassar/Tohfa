package com.iuglab.tohfa.ui_elements.user.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iuglab.tohfa.R
import com.iuglab.tohfa.appLogic.models.Product
import com.iuglab.tohfa.ui_elements.user.model.Varieties
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.categories_item2_layout.view.*

class varietiesAdapter2(var conetxt: Context, var varieties:ArrayList<Product>, val click: onClickItem?) : RecyclerView.Adapter<varietiesAdapter2.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var root = LayoutInflater.from(conetxt).inflate(R.layout.categories_item2_layout,parent,false)
        return MyViewHolder(root)
    }

    override fun getItemCount(): Int {
        return varieties.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Picasso.get().load(varieties[position].img).into(holder.img)
        holder.name.text = varieties[position].name
        holder.price.text = "$"+varieties[position].price.toString()
        holder.card.setOnClickListener {
            click!!.onClick(position)
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var img = itemView.categories_item2_layout3_img
        var name = itemView.categories_item2_layout3_title
        var price = itemView.categories_item2_layout3_cost
        var card = itemView.varieties_item2_layout_cardView

    }
    interface onClickItem{
        fun onClick(position: Int)
    }

}