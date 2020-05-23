package com.iuglab.tohfa.appLogic.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iuglab.tohfa.R
import com.iuglab.tohfa.appLogic.models.Product
import com.iuglab.tohfa.appLogic.models.ProductTestMuath
import kotlinx.android.synthetic.main.product_item_admin.view.*


class ProductEditAdapter(var context: Context, var data: MutableList<ProductTestMuath>) :
    RecyclerView.Adapter<ProductEditAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //val tvNumber = itemView.tvNumber  ****** for demonestration
        val productName = itemView.productName
        val productDescription = itemView.productDescription
        val productPrice = itemView.productPrice
        val prproductImage = itemView.productImage

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(context).inflate(R.layout.product_item_admin, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return this.data.size
    }


    override fun onBindViewHolder(holder: ProductEditAdapter.MyViewHolder, position: Int) {
        //fill holder with data
        holder.productName.text = data[position].name
        holder.productDescription.text = data[position].description
        holder.productPrice.text = "$"+data[position].price.toString()
        val testImage = "https://www.macobserver.com/wp-content/uploads/2017/05/Windows-10-Mac-1200x631.jpg?x21588"
        Glide.with(context).load(testImage).into(holder.prproductImage)
    }
}
