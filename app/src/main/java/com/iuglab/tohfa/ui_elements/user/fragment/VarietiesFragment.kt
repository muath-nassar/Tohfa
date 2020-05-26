package com.iuglab.tohfa.ui_elements.user.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.iuglab.tohfa.R
import com.iuglab.tohfa.ui_elements.user.activity.varietiesActivity
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
class VarietiesFragment : Fragment() , varietiesAdapter.onClickItem {
    lateinit var root :View
    var arrayVarieties = ArrayList<Varieties>()
    lateinit var adapter : varietiesAdapter
    var db = FirebaseFirestore.getInstance()
    val TAG = "osm"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         root = inflater.inflate(R.layout.fragment_varieties, container, false)

        db.collection("categories")
            .get()
            .addOnSuccessListener {querySnapshot ->
                for (document in querySnapshot){
                    val name = document.get("name").toString()
                    val img = document.get("image").toString()

                    arrayVarieties.add(Varieties(img,name))
                }
                adapter = varietiesAdapter(activity!!,arrayVarieties,this)
                root.Fr_varieties_recycler.layoutManager = GridLayoutManager(activity!!,3)
                root.Fr_varieties_recycler.adapter = adapter
            }
            .addOnFailureListener {
                Log.e(TAG,"FAil get item from firebase")
            }





         return root
    }

    override fun onClick(position: Int) {
        Log.e(TAG,"clicked")
        var category = arrayVarieties[position].title
        var intent = Intent(activity,varietiesActivity::class.java)
        intent.putExtra("category",category)
        startActivity(intent)
    }


}
