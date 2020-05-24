package com.iuglab.tohfa.ui_elements.admin.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.iuglab.tohfa.R
import com.iuglab.tohfa.appLogic.adapters.CategoryEditAdapter
import com.iuglab.tohfa.appLogic.models.Category
import kotlinx.android.synthetic.main.activity_show_categories_for_admin.*
import kotlinx.android.synthetic.main.toolbar_admin.*
import java.util.*

class ActivityShowCategoriesForAdmin : AppCompatActivity() {
    val TAG = "mzn"
    val db = Firebase.firestore
    val categories = mutableListOf<Category>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_categories_for_admin)
        toolbar.title = "Categories"
        db.collection("categories").get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
                    categories.add(
                        Category(
                            document.getString(Category.NAME)!!,
                            document.getString(Category.IMAGE)
                        )
                    )
                }
                val adapter = CategoryEditAdapter(this, categories)
                rv_categories_for_admin.layoutManager = GridLayoutManager(this, 2)
                rv_categories_for_admin.adapter = adapter
            }.addOnFailureListener { exception ->
                Log.e(TAG,exception.message)
            }




    }
}
