package com.iuglab.tohfa.ui_elements.admin.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.iuglab.tohfa.R
import com.iuglab.tohfa.appLogic.extra_helpers.AdminFirebaseHelper
import com.iuglab.tohfa.ui_elements.admin.fragments.FragmentAdminDashboard
import com.iuglab.tohfa.ui_elements.admin.fragments.FragmentAdminOperations

class AdminHome : AppCompatActivity() {
    val TAG = "mzn"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home)
      swapFragment(FragmentAdminDashboard())
        //test firestore
        val adminHelper = AdminFirebaseHelper(this)
        val categories = adminHelper.getAllCategories()
        for (category in categories){
            Log.e(TAG,category.name)
        }
        Log.e(TAG,categories.size.toString())
    }

    override fun onBackPressed() {}

    private fun swapFragment(fragment : Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.continer, fragment).addToBackStack(null).commit()
    }
}
