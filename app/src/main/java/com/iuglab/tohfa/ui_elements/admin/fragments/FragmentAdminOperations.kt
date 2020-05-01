package com.iuglab.tohfa.ui_elements.admin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.iuglab.tohfa.R

/**
 * A simple [Fragment] subclass.
 */
class FragmentAdminOperations : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_operations, container, false)
    }

}
