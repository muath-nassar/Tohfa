package com.iuglab.tohfa.ui_elements.admin.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.Legend.LegendForm
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
import com.iuglab.tohfa.R
import kotlinx.android.synthetic.main.fragment_admin_dashboard.*
import java.util.*

class FragmentAdminDashboard : Fragment() {

    var colors = intArrayOf(
        Color.rgb(217, 80, 138),
        Color.rgb(254, 149, 7),
        Color.rgb(254, 247, 120),
        Color.rgb(106, 167, 134),
        Color.rgb(53, 194, 209),
        Color.rgb(66, 50, 0)

    )
    lateinit var pieData: PieData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_admin_dashboard, container, false)

        val dataSet = PieDataSet(datavalues(), "Dashboard")
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 5f
        dataSet.setColors(*colors)
        pieData = PieData(dataSet)

        return root
    }

    override fun onResume() {
        super.onResume()

        drawpieChart()
        drawBarChart(intArrayOf(80,100,350,108,500))
    }

    private fun drawpieChart() {
        pieChart.setData(pieData)
        pieChart.dragDecelerationFrictionCoef = 0.1f

        pieChart.setEntryLabelTextSize(12f)
        pieChart.setDrawEntryLabels(false)

        pieChart.centerTextRadiusPercent = 80f
        pieChart.setUsePercentValues(true)

        pieChart.holeRadius = 30f
        pieChart.setHoleColor(Color.WHITE)

        pieChart.setTransparentCircleAlpha(30)
        pieChart.setTransparentCircleColor(Color.BLUE)
        pieChart.transparentCircleRadius = 0f


        pieChart.description.text = "The most requested categories"
        pieChart.description.textSize = 20f

        pieChart.animateY(1500, Easing.EaseOutCubic)
        pieChart.invalidate()
    }

    private fun datavalues(): ArrayList<PieEntry>? {
        val data = ArrayList<PieEntry>()
        data.add(PieEntry(20f, "First"))
        data.add(PieEntry(20f, "Second"))
        data.add(PieEntry(20f, "Third"))
        data.add(PieEntry(20f, "Fourth"))
        data.add(PieEntry(20f, "Fifth"))

        return data
    }// for pieChart

    private fun drawBarChart(numbers: IntArray) {

        val chart = horizontalBarChart
        chart.setDrawBarShadow(false)
        chart.setDrawValueAboveBar(true)
        chart.setMaxVisibleValueCount(numbers.size)
        chart.setPinchZoom(true)
        chart.setDrawGridBackground(true)
        val barEntries = mutableListOf<BarEntry>()
        var x = 1f
        for (number in numbers) {
            barEntries.add(BarEntry(x, number.toFloat()))
            x++
        }
        val barDataSet = BarDataSet(barEntries, "Top Rated Products")
        barDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)
        val data = BarData(barDataSet)
        data.barWidth = 0.5f

        //
       /* val l = chart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)
        l.form = LegendForm.SQUARE
        l.formSize = 9f
        l.textSize = 11f
        l.xEntrySpace = 4f*/
        //


        chart.data = data
    }


}
