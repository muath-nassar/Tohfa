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
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.iuglab.tohfa.R
import com.iuglab.tohfa.appLogic.models.Product
import kotlinx.android.synthetic.main.fragment_admin_dashboard.*
import java.util.*
import kotlin.collections.ArrayList

class FragmentAdminDashboard : Fragment() {
    val mapOfBestPurchased = mutableMapOf<String, Double>()
    var totPurchased = 0
    var totBest5 = 0
    val db = Firebase.firestore
    lateinit var pieData: PieData

    var colors = intArrayOf(
        Color.rgb(217, 80, 138),
        Color.rgb(254, 149, 7),
        Color.rgb(254, 247, 120),
        Color.rgb(106, 167, 134),
        Color.rgb(53, 194, 209),
        Color.rgb(66, 50, 0)

    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_admin_dashboard, container, false)



        return root
    }

    override fun onResume() {
        super.onResume()
        db.collection("products").orderBy(Product.PURCHASE_TIMES, Query.Direction.DESCENDING)
            .limit(5).get().addOnSuccessListener { querySnapshot ->
                val mpieData = ArrayList<PieEntry>()
                for (document in querySnapshot) {
                    val entery = PieEntry(
                        document.getDouble(Product.PURCHASE_TIMES)!!.toFloat(),
                        document.getString(Product.NAME)

                    )
                    mpieData.add(entery)
                }
                val dataSet = PieDataSet(mpieData, "Top sales")
                dataSet.sliceSpace = 0f
                dataSet.selectionShift = 5f
                dataSet.setColors(*colors)
                pieData = PieData(dataSet)
                drawpieChart()
            }

        drawBarChart(intArrayOf(80, 100, 350, 108, 500))
    }

    private fun drawpieChart() {
        pieChart.setData(pieData)
        pieChart.dragDecelerationFrictionCoef = 0.1f

        pieChart.setEntryLabelTextSize(12f)
        pieChart.setDrawEntryLabels(true)

        pieChart.centerTextRadiusPercent = 0f
        pieChart.setUsePercentValues(true)

        pieChart.holeRadius = 0f
        pieChart.setHoleColor(Color.WHITE)

        pieChart.setTransparentCircleAlpha(30)
        pieChart.setTransparentCircleColor(Color.BLUE)
        pieChart.transparentCircleRadius = 0f

        pieChart.description.text = ""
        pieChart.description.textSize = 20f

        pieChart.animateY(1500, Easing.EaseOutCubic)
        pieChart.invalidate()
    }


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