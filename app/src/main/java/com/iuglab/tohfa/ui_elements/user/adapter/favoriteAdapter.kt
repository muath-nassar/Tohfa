package com.iuglab.tohfa.ui_elements.user.adapter

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.iuglab.tohfa.R
import com.iuglab.tohfa.appLogic.models.Product
import com.iuglab.tohfa.ui_elements.user.databse.UserDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.favorite_item_layout.view.*

class favoriteAdapter (var context: Activity, var products:ArrayList<Product>, val click: favoriteAdapter.onClickFavoriteProduct?): RecyclerView.Adapter<favoriteAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var root =
            LayoutInflater.from(context).inflate(R.layout.favorite_item_layout,parent,false)
        return MyViewHolder(root)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Picasso.get().load(products[position].img).into(holder.img)
        holder.title.text = products[position].name
        holder.price.text = products[position].price.toString()

        holder.card.setOnClickListener {
            click!!.onClickFavoriteProduct(position)
        }

        holder.btnDelete.setOnClickListener {
            val alertDialog2 = AlertDialog.Builder(context)
            alertDialog2.setTitle(context.getString(R.string.sure_delete))
            alertDialog2.setIcon(R.drawable.a_delete)
            alertDialog2.setPositiveButton(context.getString(R.string.yes)){ dialogInterface, i ->
                var currentDB = UserDatabase(context)
                if (currentDB.deleteProducts(products[position].id!!)){
                    products.removeAt(position)
                    notifyItemRemoved(position)
                    notifyDataSetChanged()
                }
            }
            alertDialog2.setNegativeButton(context.getString(R.string.no)){ dialogInterface, i ->
                Log.e("osm","Not Delete")
            }
            alertDialog2.show()
        }


    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var img = itemView.favorite_item_img
        var title = itemView.favorite_item_txt_cost_title
        var price = itemView.favorite_item_txt_cost
        var btnDelete = itemView.favorite_item_img_delete
        var card = itemView.favoriteActivity_card
    }
    interface onClickFavoriteProduct{
        fun onClickFavoriteProduct(position: Int)
    }

}