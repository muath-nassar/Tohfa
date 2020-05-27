package com.iuglab.tohfa.appLogic.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.iuglab.tohfa.ui_elements.admin.fragments.FragmentAdminDashboard
import com.iuglab.tohfa.ui_elements.admin.fragments.FragmentAdminOperations

class AdminHomeSectionPagerAdapter(var context: Context,fm: FragmentManager): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    val tabs = arrayOf("Tab1,Tab2")
    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position){
            0-> fragment = FragmentAdminDashboard()
            1-> fragment = FragmentAdminOperations()

        }
        return fragment!!
    }

    override fun getCount(): Int {
        return 2
    }

}