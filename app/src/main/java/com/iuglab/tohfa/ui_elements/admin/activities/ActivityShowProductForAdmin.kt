package com.iuglab.tohfa.ui_elements.admin.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.iuglab.tohfa.R
import com.iuglab.tohfa.appLogic.adapters.ProductEditAdapter
import com.iuglab.tohfa.appLogic.models.Product
import com.iuglab.tohfa.appLogic.models.ProductTestMuath
import kotlinx.android.synthetic.main.activity_show_categories_for_admin.*
import kotlinx.android.synthetic.main.activity_show_product_for_admin.*
import kotlinx.android.synthetic.main.toolbar_admin.*

class ActivityShowProductForAdmin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_product_for_admin)
        toolbar.title = "Products"
        //tst code here start
        val p = ProductTestMuath("MacBook Air",850.00,"Have you ever saved an important file onto a flash drive or external hard drive on a Mac and then wereh")
       val products = mutableListOf<ProductTestMuath>()
        products.add(p);products.add(p);products.add(p);products.add(p);
        products.add(p);products.add(p);products.add(p);products.add(p);
        products.add(p);products.add(p);products.add(p);products.add(p);
        val adapter = ProductEditAdapter(this,products)
        rv_products_for_admin.layoutManager = LinearLayoutManager(this)
       rv_products_for_admin.adapter = adapter
        //test end

    }
}
