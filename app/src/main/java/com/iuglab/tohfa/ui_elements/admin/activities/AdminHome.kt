package com.iuglab.tohfa.ui_elements.admin.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.iuglab.tohfa.R
import com.iuglab.tohfa.ui_elements.admin.fragments.fragment_admin_dashboard

class AdminHome : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home)
        supportFragmentManager.beginTransaction().replace(R.id.continer, fragment_admin_dashboard()).addToBackStack("frag1").commit()






    }

    override fun onBackPressed() {}
}
