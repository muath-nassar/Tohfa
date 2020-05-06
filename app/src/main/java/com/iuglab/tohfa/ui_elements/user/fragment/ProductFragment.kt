package com.iuglab.tohfa.ui_elements.user.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.iuglab.tohfa.R
import com.iuglab.tohfa.appLogic.models.Product
import com.iuglab.tohfa.ui_elements.user.adapter.productAdapter
import com.iuglab.tohfa.ui_elements.user.adapter.productAdapter2
import kotlinx.android.synthetic.main.fragment_product.view.*

/**
 * A simple [Fragment] subclass.
 */
class ProductFragment : Fragment() {
    lateinit var root :View
    lateinit var arrayBestProduct : ArrayList<Product>
    lateinit var arrayProduct : ArrayList<Product>
    lateinit var bestAdapter : productAdapter
    lateinit var adapter : productAdapter2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_product, container, false)
        arrayBestProduct = ArrayList()
        arrayProduct = ArrayList()
        arrayBestProduct.add(Product())
        arrayBestProduct.add(Product())
        arrayBestProduct.add(Product())
        arrayBestProduct.add(Product())
        arrayBestProduct.add(Product())
        arrayBestProduct.add(Product())
        arrayBestProduct.add(Product())
        bestAdapter = productAdapter(activity!!,arrayBestProduct)
        root.frProduct_recyclerHorizontal.layoutManager = LinearLayoutManager(activity!!,RecyclerView.HORIZONTAL,false)
        root.frProduct_recyclerHorizontal.adapter = bestAdapter

        arrayProduct.add(Product())
        arrayProduct.add(Product())
        arrayProduct.add(Product())
        arrayProduct.add(Product())
        arrayProduct.add(Product())
        arrayProduct.add(Product())
        arrayProduct.add(Product())
        arrayProduct.add(Product())
        arrayProduct.add(Product())
        arrayProduct.add(Product())
        arrayProduct.add(Product())
        adapter = productAdapter2(activity!!,arrayProduct)
        root.frProduct_recyclerVertical.layoutManager = GridLayoutManager(activity!!,2)
        root.frProduct_recyclerVertical.adapter = adapter

        
        return root
    }

}
