package com.iuglab.tohfa.appLogic.adapters

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.iuglab.tohfa.R
import com.iuglab.tohfa.appLogic.models.Product
import com.iuglab.tohfa.ui_elements.admin.activities.ActivityShowProductForAdmin
import com.iuglab.tohfa.ui_elements.admin.activities.ActivityUpdateCategory
import com.iuglab.tohfa.ui_elements.admin.activities.ActivityUpdateProduct
import kotlinx.android.synthetic.main.product_item_admin.view.*


class ProductEditAdapter(var context: Context, var data: MutableList<Product>) :
    RecyclerView.Adapter<ProductEditAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //val tvNumber = itemView.tvNumber  ****** for demonestration
        val productName = itemView.productName
        val productDescription = itemView.productDescription
        val productPrice = itemView.productPrice
        val prproductImage = itemView.productImage
        val btnUpdate = itemView.btnUpdateProduct
        val btnDelete = itemView.btnDeleteProduct


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
        val img = data[position].img
        Glide.with(context).load(img).into(holder.prproductImage)
        //----------------------------------------------------
        holder.btnDelete.setOnClickListener {
            AlertDialog.Builder(context).setTitle("Confirm delete process").setMessage("Are you sure")
                .setPositiveButton("Yes",{_,_ ->
                    Firebase.firestore.collection("products").document(data[position].id!!).delete().
                    addOnSuccessListener {
                        Toast.makeText(context, "Product deleted successfully", Toast.LENGTH_LONG).show()
                        context.startActivity(Intent(context, ActivityShowProductForAdmin::class.java))
                    }.addOnFailureListener {

                    }

                }).setNegativeButton("No",{_,_ ->

                }).show()
        }
        holder.btnUpdate.setOnClickListener {
            val i = Intent(context, ActivityUpdateProduct::class.java)
            i.putExtra("p_id",data[position].id)
            i.putExtra("id","first open")
            context.startActivity(i)
        }


    }
}
