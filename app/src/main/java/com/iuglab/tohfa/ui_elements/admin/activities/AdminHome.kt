package com.iuglab.tohfa.ui_elements.admin.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.iuglab.tohfa.R
import com.iuglab.tohfa.ui_elements.admin.fragments.FragmentAdminDashboard

class AdminHome : AppCompatActivity() {
    val TAG = "mzn"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home)
      swapFragment(FragmentAdminDashboard())
        //test firestore



    }

    override fun onBackPressed() {}

    private fun swapFragment(fragment : Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.continer, fragment).addToBackStack(null).commit()
    }
}
