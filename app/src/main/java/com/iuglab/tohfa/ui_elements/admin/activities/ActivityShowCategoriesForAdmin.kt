package com.iuglab.tohfa.ui_elements.admin.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.iuglab.tohfa.R
import com.iuglab.tohfa.appLogic.adapters.CategoryEditAdapter
import com.iuglab.tohfa.appLogic.models.Category
import kotlinx.android.synthetic.main.activity_show_categories_for_admin.*
import kotlinx.android.synthetic.main.toolbar_admin.*
import java.util.*

class ActivityShowCategoriesForAdmin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_categories_for_admin)
        toolbar.title = "Categories"
     val cat1 = Category("Shoes",null)
        val data = mutableListOf<Category>()
        data.add(cat1);data.add(cat1);data.add(cat1);data.add(cat1);data.add(cat1);
        data.add(cat1);data.add(cat1);data.add(cat1);data.add(cat1)
        data.add(cat1);data.add(cat1);data.add(cat1);data.add(cat1)
        val adapter = CategoryEditAdapter(this,data)
        rv_categories_for_admin.layoutManager = GridLayoutManager(this,2)
        rv_categories_for_admin.adapter = adapter


    }
}
