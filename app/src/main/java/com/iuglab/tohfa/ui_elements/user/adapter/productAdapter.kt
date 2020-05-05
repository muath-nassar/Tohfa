package com.iuglab.tohfa.ui_elements.user.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.RecyclerView
import com.iuglab.tohfa.R
import com.iuglab.tohfa.appLogic.models.Product
import kotlin.coroutines.coroutineContext

class productAdapter (var context: Context,var products:ArrayList<Product>): RecyclerView.Adapter<productAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }


}