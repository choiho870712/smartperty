package com.smartperty.smartperty.landlord.menu.dataAnalysis

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.LargeValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import com.smartperty.smartperty.R
import com.smartperty.smartperty.chartUtil.MyMarkerView
import com.smartperty.smartperty.data.ChartData
import com.smartperty.smartperty.data.ChartDataPair
import com.smartperty.smartperty.data.Estate
import com.smartperty.smartperty.data.EstateList
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.fragment_data_analysis_page.view.*

class DataAnalysisPageFragment(
    val title: String,
    val myBarChartDataSet: ChartData,
    private val myPieChartDataSet: ChartData
) : Fragment() {

    companion object {
        private lateinit var root:View
        lateinit var pieChart: PieChart
        lateinit var barChart: BarChart
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_data_analysis_page, container, false)

        root.textView_data_analysis_type.text = title
        root.textView_data_analysis_type2.text = title

        createBarChart()
        createPieChart()

        return root
    }

    class PieChartActivity(private val myPieChartDataSet:ChartData) : OnChartValueSelectedListener {

        override fun onValueSelected(e: Entry?, h: Highlight) {
            if (e == null) return
            Log.i(
                "VAL SELECTED",
                "Value: " + e.y.toString() + ", index: " + h.x
                    .toString() + ", DataSet index: " + h.dataSetIndex
            )

            if (GlobalVariables.loginUser.permission.property != "N") {
                GlobalVariables.estateFolder = myPieChartDataSet.dataList[h.x.toInt()].estateList
                root.findNavController().navigate(
                    R.id.action_landlordDataAnalysisFragment_to_estateListFragment
                )
            }
        }

        override fun onNothingSelected() {
            Log.i("PieChart", "nothing selected")
        }
    }

    private fun createPieChart() {
        pieChart = root.pieChart
        pieChart.setUsePercentValues(false)
        pieChart.description.isEnabled = false
        pieChart.setExtraOffsets(5f, 10f, 5f, 5f)
        pieChart.dragDecelerationFrictionCoef = 0.95f
        pieChart.setCenterTextTypeface(Typeface.DEFAULT)
        pieChart.centerText = generateCenterSpannableText()
        pieChart.isDrawHoleEnabled = false
        pieChart.setHoleColor(Color.WHITE)
        pieChart.setTransparentCircleColor(Color.WHITE)
        pieChart.setTransparentCircleAlpha(110)
        pieChart.holeRadius = 58f
        pieChart.transparentCircleRadius = 61f
        pieChart.setDrawCenterText(false)
        pieChart.rotationAngle = 0f
        // enable rotation of the chart by touch
        pieChart.isRotationEnabled = true
        pieChart.isHighlightPerTapEnabled = true

        // chart.setUnit(" €");
        // chart.setDrawUnitsInChart(true);

        // add a selection listener
        pieChart.setOnChartValueSelectedListener(PieChartActivity(myPieChartDataSet))
        pieChart.animateY(1400, Easing.EaseInOutQuad)
        // chart.spin(2000, 0, 360);
        val l = pieChart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.xEntrySpace = 7f
        l.yEntrySpace = 0f
        l.yOffset = 0f

        // entry label styling
        pieChart.setEntryLabelColor(Color.WHITE)
        pieChart.setEntryLabelTypeface(Typeface.DEFAULT)
        pieChart.setEntryLabelTextSize(12f)

        setPieChartData()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setPieChartData() {
        val entries: ArrayList<PieEntry> = ArrayList()

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        myPieChartDataSet.dataList.forEach {
            entries.add(
                PieEntry(
                    it.value.toFloat(),
                    when (it.tag) {
                        "Dwelling" -> {
                            "住宅"
                        }
                        "Suite" -> {
                            "套房"
                        }
                        "Storefront" -> {
                            "店面"
                        }
                        "Office" -> {
                            "辦公"
                        }
                        "DwellingOffice" -> {
                            "住辦"
                        }
                        "Factory" -> {
                            "廠房"
                        }
                        "ParkingSpace" -> {
                            "車位"
                        }
                        "LandPlace" -> {
                            "土地"
                        }
                        "Other" -> {
                            "其他"
                        }
                        else -> {
                            it.tag
                        }
                    }
                )
            )
        }
        val dataSet = PieDataSet(entries, "")
        dataSet.setDrawIcons(false)
        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f

        // add a lot of colors
        val colors: ArrayList<Int> = ArrayList()
        for (c: Int in ColorTemplate.VORDIPLOM_COLORS) colors.add(c)
        for (c: Int in ColorTemplate.JOYFUL_COLORS) colors.add(c)
        for (c: Int in ColorTemplate.COLORFUL_COLORS) colors.add(c)
        for (c: Int in ColorTemplate.LIBERTY_COLORS) colors.add(c)
        for (c: Int in ColorTemplate.PASTEL_COLORS) colors.add(c)
        colors.add(ColorTemplate.getHoloBlue())
        dataSet.colors = colors
        //dataSet.setSelectionShift(0f);
        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.WHITE)
        data.setValueTypeface(Typeface.DEFAULT)

        data.setValueFormatter(
            object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return value.toInt().toString()
                }
            }
        )

        pieChart.data = data

        // undo all highlights
        pieChart.highlightValues(null)
        pieChart.invalidate()
    }

    private fun generateCenterSpannableText(): SpannableString {
        val s = SpannableString("MPAndroidChart\ndeveloped by Philipp Jahoda")
        s.setSpan(RelativeSizeSpan(1.7f), 0, 14, 0)
        s.setSpan(StyleSpan(Typeface.NORMAL), 14, s.length - 15, 0)
        s.setSpan(ForegroundColorSpan(Color.GRAY), 14, s.length - 15, 0)
        s.setSpan(RelativeSizeSpan(.8f), 14, s.length - 15, 0)
        s.setSpan(StyleSpan(Typeface.ITALIC), s.length - 14, s.length, 0)
        s.setSpan(ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length - 14, s.length, 0)
        return s
    }

    class BarChartActivity : OnChartValueSelectedListener {

        override fun onValueSelected(e: Entry, h: Highlight) {
            Log.i("Activity", "Selected: " + e.toString() + ", dataSet: " + h.dataSetIndex)
        }

        override fun onNothingSelected() {
            Log.i("Activity", "Nothing selected.")
        }
    }

    private fun createBarChart() {
        barChart = root.barChart
        barChart.setOnChartValueSelectedListener(BarChartActivity())
        barChart.description.isEnabled = false

//        chart.setDrawBorders(true);

        // scaling can now only be done on x- and y-axis separately
        barChart.setPinchZoom(false)
        barChart.setDrawBarShadow(false)
        barChart.setDrawGridBackground(false)

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        val mv = MyMarkerView(requireContext(), R.layout.custom_marker_view)
        mv.chartView = barChart // For bounds control
        barChart.marker = mv // Set the marker to the chart

        val l: Legend = barChart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(true)
        l.typeface = Typeface.DEFAULT
        l.yOffset = 0f
        l.xOffset = 10f
        l.yEntrySpace = 0f
        l.textSize = 8f
        val xAxis: XAxis = barChart.xAxis
        xAxis.typeface = Typeface.DEFAULT
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f
        xAxis.setCenterAxisLabels(true)
        xAxis.axisMinimum = 0f
        xAxis.axisMaximum = myBarChartDataSet.dataList.size.toFloat()
        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return if (myBarChartDataSet.dataList.size > value.toInt() && value.toInt() >= 0) {
                    when (myBarChartDataSet.dataList[value.toInt()].tag ) {
                        "Dwelling" -> {
                            "住宅"
                        }
                        "Suite" -> {
                            "套房"
                        }
                        "Storefront" -> {
                            "店面"
                        }
                        "Office" -> {
                            "辦公"
                        }
                        "DwellingOffice" -> {
                            "住辦"
                        }
                        "Factory" -> {
                            "廠房"
                        }
                        "ParkingSpace" -> {
                            "車位"
                        }
                        "LandPlace" -> {
                            "土地"
                        }
                        "Other" -> {
                            "其他"
                        }
                        else -> {
                            myBarChartDataSet.dataList[value.toInt()].tag
                        }
                    }

                }
                else
                    value.toInt().toString()
            }
        }

        val leftAxis: YAxis = barChart.axisLeft
        leftAxis.typeface = Typeface.DEFAULT
        leftAxis.setDrawGridLines(false)
        leftAxis.spaceTop = 35f
        leftAxis.axisMinimum = 0f // this replaces setStartAtZero(true)
        leftAxis.granularity = 10000f
        leftAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return if (value >= 10000) {
                    (value/10000).toInt().toString() + "萬台幣"
                } else
                    ""
            }
        }
        val rightAxis: YAxis = barChart.axisRight
        rightAxis.typeface = Typeface.DEFAULT
        rightAxis.setDrawGridLines(false)
        rightAxis.axisMinimum = 0f // this replaces setStartAtZero(true)
        rightAxis.granularity = 2f
        rightAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return if (value > 0) {
                    value.toInt().toString() + "%"
                } else
                    ""
            }
        }

        setBarChartData()
    }

    private fun setBarChartData() {

        val barSpace = 0.03f
        val barWidth = 0.2f
        val groupSpace = 1f - (barSpace+barWidth)*2

        val values1: ArrayList<BarEntry> = ArrayList()
        val values2: ArrayList<BarEntry> = ArrayList()
        myBarChartDataSet.dataList.forEachIndexed { index, chartDataPair ->
            values1.add(BarEntry(index.toFloat(), chartDataPair.value.toFloat()))
            values2.add(BarEntry(index.toFloat(), chartDataPair.value2.toFloat()))
        }
        val set1: BarDataSet
        val set2: BarDataSet
        if (barChart.data != null && barChart.data.dataSetCount > 0) {
            set1 = barChart.data.getDataSetByIndex(0) as BarDataSet
            set2 = barChart.data.getDataSetByIndex(1) as BarDataSet
            set1.values = values1
            set2.values = values2
            barChart.data.notifyDataChanged()
            barChart.notifyDataSetChanged()
        } else {
            set1 = BarDataSet(values1, "租金/坪")
            set1.color = Color.rgb(255, 0, 0)
            set2 = BarDataSet(values2, "投資報酬率")
            set2.color = Color.rgb(0, 0, 255)
            set2.axisDependency = barChart.axisRight.axisDependency
            var maxNumber = 0.0
            myBarChartDataSet.dataList.forEach {
                if (it.value2 > maxNumber)
                    maxNumber = it.value2
            }
            barChart.axisRight.axisMaximum = (maxNumber + 10).toFloat()
            val data = BarData(set1, set2)
            data.setValueFormatter(
                object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        return if (value >= 0 && value < 1) {
                            value.toString() + "%"
                        } else if (value >= 1000)
                            (value/1000).toInt().toString() + ",000台幣"
                        else
                            value.toInt().toString()
                    }
                }
            )
            data.setValueTypeface(Typeface.DEFAULT)
            barChart.data = data
        }

        barChart.barData.barWidth = barWidth

        barChart.groupBars(0f, groupSpace, barSpace)
        barChart.invalidate()
    }
}