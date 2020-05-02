package com.iuglab.tohfa.ui_elements.admin.activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.iuglab.tohfa.R
import com.iuglab.tohfa.ui_elements.admin.fragments.fragment_admin_dashboard
import kotlinx.android.synthetic.main.activity_admin_home.*

class AdminHome : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home)

        supportFragmentManager.beginTransaction().replace(R.id.continer, fragment_admin_dashboard()).commit()

    }
}
