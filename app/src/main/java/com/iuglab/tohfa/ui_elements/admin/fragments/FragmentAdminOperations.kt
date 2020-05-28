package com.iuglab.tohfa.ui_elements.admin.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.iuglab.tohfa.R
import com.iuglab.tohfa.ui_elements.admin.activities.*
import kotlinx.android.synthetic.main.fragment_admin_operations.view.*

/**
 * A simple [Fragment] subclass.
 */
class FragmentAdminOperations : Fragment() {
    lateinit var  root: View
    lateinit var i : Intent

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       root = inflater.inflate(R.layout.fragment_admin_operations, container, false)
        return root
    }

    override fun onResume() {
        super.onResume()
        root.add_category_card.setOnClickListener {
            i = Intent(activity,ActivityAddCategory::class.java)
            go()
        }
        root.add_product_card.setOnClickListener {
            i = Intent(activity,ActivityAddProduct::class.java)
            go()
        }
        root.update_category_card.setOnClickListener {
            i = Intent(activity,ActivityShowCategoriesForAdmin::class.java)
            go()
        }
        root.update_product_card.setOnClickListener {
            i = Intent(activity,ActivityShowProductForAdmin::class.java)
            go()
        }
        root.delete_category_card.setOnClickListener {
            i = Intent(activity,ActivityShowCategoriesForAdmin::class.java)
            go()
        }
        root.delete_product_card.setOnClickListener {
            i = Intent(activity,ActivityShowProductForAdmin::class.java)
            go()
        }
    }
    private fun go(){
        startActivity(i)
    }

}
