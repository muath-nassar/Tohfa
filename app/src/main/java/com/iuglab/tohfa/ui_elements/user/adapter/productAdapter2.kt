package com.iuglab.tohfa.ui_elements.user.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iuglab.tohfa.R
import com.iuglab.tohfa.appLogic.models.Product

class productAdapter2(var context: Context , var products:ArrayList<Product>) : RecyclerView.Adapter<productAdapter2.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var root = LayoutInflater.from(context).inflate(R.layout.categories_item_layout3,parent,false)
        return MyViewHolder(root)
    }

    override fun getItemCount(): Int {
       return products.size
    }
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

    }
}