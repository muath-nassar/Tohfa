package com.iuglab.tohfa.ui_elements.admin.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.iuglab.tohfa.R
import kotlinx.android.synthetic.main.activity_add_product.*
import kotlinx.android.synthetic.main.toolbar_admin.*

class ActivityAddProduct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)
        //ready screen start
        toolbar.title = "Add product"
        val items = listOf("Material", "Design", "Components", "Android")
        val adapter = ArrayAdapter(this, R.layout.list_item_material, items)
        (spCategory.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        //ready screen end

    }
}
