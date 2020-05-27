package com.iuglab.tohfa.ui_elements.admin.activities

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.iuglab.tohfa.R
import com.iuglab.tohfa.appLogic.adapters.AdminHomeSectionPagerAdapter
import com.iuglab.tohfa.ui_elements.admin.fragments.FragmentAdminDashboard
import kotlinx.android.synthetic.main.activity_admin_home.*
import kotlinx.android.synthetic.main.custom_tab.*

class AdminHome : AppCompatActivity() {
    val TAG = "mzn"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home)
        //setSupportActionBar(toolbar)

        val sectionsPagerAdapter = AdminHomeSectionPagerAdapter(this, supportFragmentManager)
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)

        tabs.getTabAt(0)!!.setIcon(R.drawable.a_dashboard)
        tabs.getTabAt(0)!!.setText("Dashboard")
        tabs.getTabAt(1)!!.setIcon(R.drawable.ic_operations)
        tabs.getTabAt(1)!!.setText("Operations")

        //test firestore



    }

    override fun onBackPressed() {}


}
