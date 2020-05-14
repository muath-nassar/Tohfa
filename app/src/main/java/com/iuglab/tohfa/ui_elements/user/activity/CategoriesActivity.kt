package com.iuglab.tohfa.ui_elements.user.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.h.alrekhawi.tabbediugexample.adapter.TabPagerAdapter
import com.iuglab.tohfa.R
import com.iuglab.tohfa.ui_elements.user.fragment.ProductFragment
import com.iuglab.tohfa.ui_elements.user.fragment.VarietiesFragment
import kotlinx.android.synthetic.main.activity_categories.*


class CategoriesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)
        Toast.makeText(applicationContext,"\n  CategoriesActivity    \n", Toast.LENGTH_LONG).show()


        setSupportActionBar(categories_toolbar)

//       val tabPagerAdapter = TabPagerAdapter(this,supportFragmentManager)
//        categories_view_pager.adapter = tabPagerAdapter
        createViewPager(categories_view_pager)
        categories_tabs.setupWithViewPager(categories_view_pager)
        createTabIcons()
    }

    private fun createTabIcons() {
        val tabOne = LayoutInflater.from(this).inflate(R.layout.custom_tab, null) as TextView
        tabOne.text = "Varieties"
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_variters, 0, 0)
        categories_tabs.getTabAt(0)!!.setCustomView(tabOne)

        val tabTwo = LayoutInflater.from(this).inflate(R.layout.custom_tab, null) as TextView
        tabTwo.text = "Products"
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_product, 0, 0)
        categories_tabs.getTabAt(1)!!.setCustomView(tabTwo)

    }

    private fun createViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFrag(VarietiesFragment(), "Varieties")
        adapter.addFrag(ProductFragment(), "Product")

        viewPager.adapter = adapter
    }

    internal class ViewPagerAdapter(manager: FragmentManager?) :
        FragmentPagerAdapter(manager!!) {
        private val mFragmentList: MutableList<Fragment> = ArrayList()
        private val mFragmentTitleList: MutableList<String> = ArrayList()
        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFrag(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitleList[position]
        }
    }


    override fun onBackPressed() {

    }

}
