package com.iuglab.tohfa.ui_elements.user.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.iuglab.tohfa.R
import com.iuglab.tohfa.ui_elements.Settings
import com.iuglab.tohfa.ui_elements.user.adapter.productAdapter2
import com.iuglab.tohfa.ui_elements.user.fragment.ProductFragment
import com.iuglab.tohfa.ui_elements.user.fragment.VarietiesFragment
import kotlinx.android.synthetic.main.activity_categories.*


class CategoriesActivity : AppCompatActivity(){

       var productFragment : ProductFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        setSupportActionBar(categories_toolbar)
//       val tabPagerAdapter = TabPagerAdapter(this,supportFragmentManager)
//        categories_view_pager.adapter = tabPagerAdapter
        createViewPager(categories_view_pager)
        categories_tabs.setupWithViewPager(categories_view_pager)
        createTabIcons()

        productFragment = ProductFragment()

        categories_btn_basket.setOnClickListener {
            var intent = Intent(this,basketActivity::class.java)
            startActivity(intent)
        }

    }

    private fun createTabIcons() {
        val tabOne = LayoutInflater.from(this).inflate(R.layout.custom_tab, null) as TextView
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_variters, 0, 0)
        categories_tabs.getTabAt(0)!!.setCustomView(tabOne)

        val tabTwo = LayoutInflater.from(this).inflate(R.layout.custom_tab, null) as TextView
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.categoerires_menu2,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

            R.id.categories_icon_search -> {

                var intent = Intent(applicationContext,SearchActivity::class.java)
                startActivity(intent)
            }

            R.id.categories_icon_favorite ->{
                var intent = Intent(applicationContext, favoriteActivity::class.java)
                startActivity(intent)
            }
            R.id.categories_icon_setting ->{
                var intent = Intent(applicationContext, Settings::class.java)
                startActivity(intent)
            }

        }
        return super.onOptionsItemSelected(item)
    }

}
