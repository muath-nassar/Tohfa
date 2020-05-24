package com.iuglab.tohfa.appLogic.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iuglab.tohfa.R
import com.iuglab.tohfa.appLogic.models.Category
import kotlinx.android.synthetic.main.category_item_for_admin.view.*


class CategoryEditAdapter(var context: Context, var data: MutableList<Category>) :
    RecyclerView.Adapter<CategoryEditAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //val tvNumber = itemView.tvNumber  ****** for demonestration
        val categoryImage = itemView.categoryImage
        val categoryTitle = itemView.categoryTitle

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(context).inflate(R.layout.category_item_for_admin, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return this.data.size
    }


    override fun onBindViewHolder(holder: CategoryEditAdapter.MyViewHolder, position: Int) {
        holder.categoryTitle.text = data[position].name
        Glide.with(context).load(data[position].img).into(holder.categoryImage)

    }
}
