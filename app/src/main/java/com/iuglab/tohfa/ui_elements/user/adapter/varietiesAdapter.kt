package com.iuglab.tohfa.ui_elements.user.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iuglab.tohfa.R
import com.iuglab.tohfa.ui_elements.user.model.Varieties
import kotlinx.android.synthetic.main.categories_item_layout.view.*

class varietiesAdapter(var conetxt:Context , var varieties:ArrayList<Varieties>) : RecyclerView.Adapter<varietiesAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var root = LayoutInflater.from(conetxt).inflate(R.layout.categories_item_layout,null)
        return MyViewHolder(root)
    }

    override fun getItemCount(): Int {
       return varieties.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var img = itemView.categories_item_img
        var name = itemView.categories_item_txt


    }

}