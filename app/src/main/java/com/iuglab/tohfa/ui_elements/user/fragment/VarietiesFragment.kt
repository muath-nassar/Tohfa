package com.iuglab.tohfa.ui_elements.user.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager

import com.iuglab.tohfa.R
import com.iuglab.tohfa.ui_elements.user.adapter.varietiesAdapter
import com.iuglab.tohfa.ui_elements.user.model.Varieties
import kotlinx.android.synthetic.main.fragment_varieties.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [VarietiesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class VarietiesFragment : Fragment() {
    lateinit var root :View
    lateinit var arrayVarieties : ArrayList<Varieties>
    lateinit var adapter : varietiesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         root = inflater.inflate(R.layout.fragment_varieties, container, false)


        arrayVarieties = ArrayList()
        arrayVarieties.add(Varieties(R.drawable.b_applogo,"S"))
        arrayVarieties.add(Varieties(R.drawable.b_applogo,"S"))
        arrayVarieties.add(Varieties(R.drawable.b_applogo,"S"))
        arrayVarieties.add(Varieties(R.drawable.b_applogo,"S"))
        arrayVarieties.add(Varieties(R.drawable.b_applogo,"S"))
        arrayVarieties.add(Varieties(R.drawable.b_applogo,"S"))
        arrayVarieties.add(Varieties(R.drawable.b_applogo,"S"))
        arrayVarieties.add(Varieties(R.drawable.b_applogo,"S"))
        arrayVarieties.add(Varieties(R.drawable.b_applogo,"S"))
        arrayVarieties.add(Varieties(R.drawable.b_applogo,"S"))
        arrayVarieties.add(Varieties(R.drawable.b_applogo,"S"))
        arrayVarieties.add(Varieties(R.drawable.b_applogo,"S"))
        arrayVarieties.add(Varieties(R.drawable.b_applogo,"S"))

        adapter = varietiesAdapter(activity!!,arrayVarieties)
        root.Fr_varieties_recycler.layoutManager = GridLayoutManager(activity!!,3)
        root.Fr_varieties_recycler.adapter = adapter


         return root
    }


}
