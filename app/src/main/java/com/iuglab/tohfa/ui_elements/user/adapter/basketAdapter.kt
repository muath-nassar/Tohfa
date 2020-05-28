package com.iuglab.tohfa.ui_elements.user.adapter

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.iuglab.tohfa.R
import com.iuglab.tohfa.appLogic.models.Product
import com.iuglab.tohfa.ui_elements.user.databse.UserDatabase
import com.iuglab.tohfa.ui_elements.user.model.itemBascket
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.basket_item_layout.view.*

class basketAdapter (var context: Activity, var baskets:ArrayList<itemBascket>, val click: basketAdapter.onClickBasketProduct?): RecyclerView.Adapter<basketAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var root =
            LayoutInflater.from(context).inflate(R.layout.basket_item_layout,parent,false)
        return MyViewHolder(root)
    }

    override fun getItemCount(): Int {
        return baskets.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Picasso.get().load(baskets[position].img).into(holder.img)
        holder.title.text = baskets[position].name
        holder.contity.text = baskets[position].contity.toString()
        holder.card.setOnClickListener {
            click!!.onClickBasketProduct(position)
        }
        holder.btnDelete.setOnClickListener {
            val alertDialog2 = AlertDialog.Builder(context)
            alertDialog2.setTitle("Are sure delte the product ?")
            alertDialog2.setIcon(R.drawable.a_delete)
            alertDialog2.setPositiveButton(context.getString(R.string.yes)){ dialogInterface, i ->
                var currentDB = UserDatabase(context)
                if (currentDB.deleteItemBascket(baskets[position].id)){
                    baskets.removeAt(position)
                    notifyItemRemoved(position)
                    notifyDataSetChanged()
                }
            }
            alertDialog2.setNegativeButton(context.getString(R.string.no)){ dialogInterface, i ->
                Log.e("osm","Not Delete")
            }
            alertDialog2.show()
        }

        holder.btnPlus.setOnClickListener {
            var contity = holder.contity.text.toString().toInt()
            var newContity = contity + 1
            holder.contity.text = newContity.toString()
        }

        holder.btnMinus.setOnClickListener {
            var contity = holder.contity.text.toString().toInt()
            if(contity > 1){
                var newContity = contity - 1
                holder.contity.text = newContity.toString()
            }
        }

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var img = itemView.basketActivity_img
        var title = itemView.basketActivity_title
        var contity = itemView.basketActivity_txt_contity
        var btnDelete = itemView.basketActivity_btn_delete
        var btnPlus = itemView.basketActivity_btn_plus
        var btnMinus = itemView.basketActivity_btn_minus
        var card = itemView.basketActivity_card
    }
    interface onClickBasketProduct{
        fun onClickBasketProduct(position: Int)
    }

}