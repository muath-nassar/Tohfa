package com.iuglab.tohfa.ui_elements.admin.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.iuglab.tohfa.R
import com.iuglab.tohfa.ui_elements.admin.fragments.FragmentAdminDashboard
import com.iuglab.tohfa.ui_elements.admin.fragments.FragmentAdminOperations

class AdminHome : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home)
      swapFragment(FragmentAdminDashboard())






    }

    override fun onBackPressed() {}

    private fun swapFragment(fragment : Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.continer, fragment).addToBackStack(null).commit()
    }
}
