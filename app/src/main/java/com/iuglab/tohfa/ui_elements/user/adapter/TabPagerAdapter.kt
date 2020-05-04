package com.h.alrekhawi.tabbediugexample.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.iuglab.tohfa.ui_elements.user.fragment.ProductFragment
import com.iuglab.tohfa.ui_elements.user.fragment.VarietiesFragment


private val TAB_TITLES = arrayOf(
    "Varieties",
    "Product"
)

class TabPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {

        var fragment: Fragment? =null

        when (position) {
            0 -> fragment = VarietiesFragment()
            1 -> fragment= ProductFragment()

        }

        return fragment!!
    }

    override fun getPageTitle(position: Int): CharSequence? {
//        return context.resources.getString(TAB_TITLES[position])
        return TAB_TITLES[position]
    }

    override fun getCount(): Int {
        return 2
    }
}