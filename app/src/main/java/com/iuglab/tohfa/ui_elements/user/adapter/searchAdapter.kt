package com.iuglab.tohfa.ui_elements.user.adapter

import android.app.Activity
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

class searchAdapter (var activity: Activity, var products:ArrayList<Product>, val click: searchAdapter.onClickSearchProduct?) : RecyclerView.Adapter<searchAdapter.MyViewHolder>() ,
    Filterable {

    lateinit var favorite : UserDatabase
    lateinit var favorites : ArrayList<String>
    var isFavorite : Boolean ? = null
    var productsFull = ArrayList<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var root = LayoutInflater.from(activity).inflate(R.layout.categories_item_layout3,parent,false)
        favorite = UserDatabase(activity)
        favorites = favorite.getAllKeyProduct()
        isFavorite = false
        return MyViewHolder(root)
    }

    override fun getItemCount(): Int {
        return products.size
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Picasso.get().load(products[position].img).into(holder.img)
        holder.title.text = products[position].name
        holder.price.text = "$" + products[position].price.toString()
        for (f in favorites){
            if (f == products[position].id){
                holder.heart.setImageResource(R.drawable.b_favorite)
                isFavorite = true
            }else{
                holder.heart.setImageResource(R.drawable.ic_favorite_border)
                isFavorite = false
            }
        }
        holder.card.setOnClickListener {
            click!!.onClickSearchProduct(position)
        }

    }
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var img = itemView.categories_item_layout3_img
        var title = itemView.categories_item_layout3_title
        var price = itemView.categories_item_layout3_cost
        var heart = itemView.categories_item_layout3_btn_favorite
        var card = itemView.categories_item_layout3_card

    }

    interface onClickSearchProduct{
        fun onClickSearchProduct(position: Int)
    }

    override fun getFilter(): Filter {
        return filter
    }



    private var filter = object  : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            var filteredList  = ArrayList<Product>()
            if (constraint == null || constraint.length == 0){
                filteredList.addAll(productsFull)
            }else{
                var filterPattern : String = constraint.toString().toLowerCase().trim()
                for (item in productsFull){
                    if(item.name.toLowerCase().contains(filterPattern)){
                        filteredList.add(item)
                    }
                }
            }

            var results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            products.clear()
            products.addAll(results.values as List<Product>)
            notifyDataSetChanged()
        }
    }
}