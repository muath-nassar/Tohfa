package com.iuglab.tohfa.ui_elements.user.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.h.alrekhawi.tabbediugexample.adapter.TabPagerAdapter
import com.iuglab.tohfa.R
import kotlinx.android.synthetic.main.activity_categories.*

class CategoriesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)


        setSupportActionBar(categories_toolbar)
       val tabPagerAdapter = TabPagerAdapter(this,supportFragmentManager)
        categories_view_pager.adapter = tabPagerAdapter
        categories_tabs.setupWithViewPager(categories_view_pager)

    }


}
