package com.iuglab.tohfa.ui_elements.admin.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.iuglab.tohfa.R
import kotlinx.android.synthetic.main.fragment_admin_dashboard.*
import java.util.*

class fragment_admin_dashboard : Fragment() {

    var colors = intArrayOf(
        Color.RED,
        Color.GRAY,
        Color.GREEN,
        Color.YELLOW,
        Color.CYAN,
        Color.MAGENTA,
        Color.rgb(122,33,44)
    )
    lateinit var pd :PieData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_admin_dashboard, container, false)

        val pds = PieDataSet(datavalues(), "Dashboard")
        pds.setColors(*colors)
        pd = PieData(pds)

        return root
    }

    override fun onResume() {
        super.onResume()

        pieChart.setData(pd)

        pieChart.setCenterTextSize(18f)
        pieChart.setDrawEntryLabels(true)
        pieChart.centerText = "455"
        pieChart.centerTextRadiusPercent = 80f
        pieChart.setUsePercentValues(true)
//        pieChart.setHoleColor(Color.rgb(111,11,50))
        pieChart.holeRadius = 30f
        pieChart.setTransparentCircleAlpha(20)
        pieChart.setTransparentCircleColor(Color.BLUE)
        pieChart.transparentCircleRadius = 40f
        pieChart.description.text = "Sales"
//        pieChart.maxAngle = 100f

        pieChart.invalidate()
    }


    private fun datavalues(): ArrayList<PieEntry>? {
        val data = ArrayList<PieEntry>()
        data.add(PieEntry(15.78f, "sta"))
        data.add(PieEntry(12.22f, "dgg"))
        data.add(PieEntry(4.14f, "hgg"))
        data.add(PieEntry(34.55f, "ddg"))
        data.add(PieEntry(6.64f, "ggs"))
        data.add(PieEntry(5.12f, "ddd"))
        data.add(PieEntry(10f, "ggg"))
        return data
    }

}
