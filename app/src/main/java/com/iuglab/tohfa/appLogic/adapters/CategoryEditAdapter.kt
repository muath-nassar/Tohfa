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
import com.iuglab.tohfa.appLogic.models.Category
import com.iuglab.tohfa.ui_elements.admin.activities.ActivityShowCategoriesForAdmin
import com.iuglab.tohfa.ui_elements.admin.activities.ActivityUpdateCategory
import kotlinx.android.synthetic.main.category_item_for_admin.view.*


class CategoryEditAdapter(var context: Context, var data: MutableList<Category>) :
    RecyclerView.Adapter<CategoryEditAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //val tvNumber = itemView.tvNumber  ****** for demonestration
        val categoryImage = itemView.categoryImage
        val categoryTitle = itemView.categoryTitle
        val btnDeleteCategory = itemView.deleteCategory
        val btnUpdateCategory = itemView.updateCategory
        var id = ""

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
        holder.id = data[position].id!!
        holder.categoryTitle.text = data[position].name
        Glide.with(context).load(data[position].img).into(holder.categoryImage)
        holder.btnUpdateCategory.setOnClickListener {
            val i = Intent(context,ActivityUpdateCategory::class.java)
            i.putExtra("p_id",holder.id)
            context.startActivity(i)

        }
        holder.btnDeleteCategory.setOnClickListener {
            AlertDialog.Builder(context).setTitle("Confirm delete process").setMessage("Are you sure")
                .setPositiveButton("Yes",{_,_ ->
                    Firebase.firestore.collection("categories").document(holder.id).delete().
                    addOnSuccessListener {
                        Toast.makeText(context, "Category deleted successfully",Toast.LENGTH_LONG).show()
                        context.startActivity(Intent(context, ActivityShowCategoriesForAdmin::class.java))
                    }.addOnFailureListener {

                    }

                }).setNegativeButton("No",{_,_ ->

                }).show()

        }


    }
}
