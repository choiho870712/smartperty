package com.smartperty.smartperty.landlord.menu.contract

import android.annotation.SuppressLint
import android.app.Activity
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
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import com.smartperty.smartperty.R
import com.smartperty.smartperty.chartUtil.MyMarkerView
import com.smartperty.smartperty.data.EstateList
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.landlord_fragment_contract.view.*


class LandlordContractFragment : Fragment() {

    companion object {
        private lateinit var root:View
        lateinit var lineChart: LineChart
        lateinit var pieChart1: PieChart
        lateinit var pieChart2: PieChart
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.landlord_fragment_contract, container, false)

        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()

        GlobalVariables.prepareContractChartDataSet()

        createLineChart()
        createPieChart1()
        createPieChart2()

        return root
    }

    class LineChartActivity : Activity(), OnChartValueSelectedListener {
        override fun onValueSelected(e: Entry?, h: Highlight?) {
            Log.i("Entry selected", e.toString())
            Log.i(
                "LOW HIGH",
                "low: " + lineChart.getLowestVisibleX()
                    .toString() + ", high: " + lineChart.getHighestVisibleX()
            )
            Log.i(
                "MIN MAX",
                "xMin: " + lineChart.getXChartMin()
                    .toString() + ", xMax: " + lineChart.getXChartMax()
                    .toString() + ", yMin: " + lineChart.getYChartMin()
                    .toString() + ", yMax: " + lineChart.getYChartMax()
            )
        }

        override fun onNothingSelected() {

        }
    }

    private fun createLineChart() {
        run {
            // // Chart Style // //
            lineChart = root.chart1

            // background color
            lineChart.setBackgroundColor(Color.WHITE)

            // disable description text
            lineChart.getDescription().setEnabled(false)

            // enable touch gestures
            lineChart.setTouchEnabled(true)

            // set listeners
            lineChart.setOnChartValueSelectedListener(LineChartActivity())
            lineChart.setDrawGridBackground(false)
            lineChart.setDrawBorders(false)

            // create marker to display box when values are selected
            val mv = MyMarkerView(requireContext(), R.layout.custom_marker_view)

            // Set the marker to the chart
            mv.setChartView(lineChart)
            lineChart.setMarker(mv)

            // enable scaling and dragging
            lineChart.setDragEnabled(true)
            lineChart.setScaleEnabled(true)
            // chart.setScaleXEnabled(true);
            // chart.setScaleYEnabled(true);

            // force pinch zoom along both axis
            lineChart.setPinchZoom(true)

            lineChart
        }
        var xAxis: XAxis
        run {
            // // X-Axis Style // //
            xAxis = lineChart.getXAxis()

            // vertical grid lines
            xAxis.setDrawGridLines(false)

            xAxis.position = XAxis.XAxisPosition.BOTTOM

            xAxis.granularity = 1.0f

            xAxis.valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    val month = (value-1)%12 +1
                    return month.toInt().toString() + "/1"
                }
            }
        }
        var leftAxis: YAxis
        run {
            // // Y-Axis Style // //
            leftAxis = lineChart.getAxisLeft()

            leftAxis.granularity = 1.0f
        }
        var rightAxis: YAxis
        run {
            // // Y-Axis Style // //
            rightAxis = lineChart.axisRight

            rightAxis.granularity = 1.0f
        }

        // add data
        setLineChartData()

        // draw points over time
        lineChart.animateXY(1000, 1000)

        // get the legend (only possible after setting data)
        val l: Legend = lineChart.getLegend()

        // draw legend entries as lines
        l.form = Legend.LegendForm.LINE
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setLineChartData() {
        val values: ArrayList<Entry> = ArrayList()
        GlobalVariables.contractExpireLineChartDataSet.dataList.forEach {
            val month = it.tag.toInt()
            values.add(Entry((month).toFloat(), it.value.toFloat()))
        }
        val set1: LineDataSet
        if (lineChart.getData() != null &&
            lineChart.getData().getDataSetCount() > 0
        ) {
            set1 = lineChart.getData().getDataSetByIndex(0) as LineDataSet
            set1.setValues(values)
            set1.notifyDataSetChanged()
            lineChart.getData().notifyDataChanged()
            lineChart.notifyDataSetChanged()
        } else {
            // create a dataset and give it a type
            set1 = LineDataSet(values, "到期日")
            set1.setDrawIcons(false)

            // black lines and points
            set1.setColor(Color.BLUE)
            set1.setCircleColor(Color.BLUE)

            // line thickness and point size
            set1.setLineWidth(3f)
            set1.setCircleRadius(6f)

            // draw points as solid circles
            set1.setDrawCircleHole(true)
            set1.circleHoleRadius = 3f

            // text size of values
            set1.setValueTextSize(10f)

            set1.valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return value.toInt().toString()
                }
            }

            val dataSets: ArrayList<ILineDataSet> = ArrayList()
            dataSets.add(set1) // add the data sets

            // create a data object with the data sets
            val data = LineData(dataSets)

            // set data
            lineChart.setData(data)
        }
    }

    class PieChartActivity1() : OnChartValueSelectedListener {

        override fun onValueSelected(e: Entry?, h: Highlight) {
            if (e == null) return
            Log.i(
                "VAL SELECTED",
                "Value: " + e.y.toString() + ", index: " + h.x
                    .toString() + ", DataSet index: " + h.dataSetIndex
            )

            if (GlobalVariables.loginUser.permission.property != "N") {
                GlobalVariables.estateFolder = EstateList(
                    list = GlobalVariables.getContractExpireIn3MonthByAreaList(
                        GlobalVariables.contractExpireIn3MonthBySquareFtPieChartDataSet.dataList[h.x.toInt()].rangeMin,
                        GlobalVariables.contractExpireIn3MonthBySquareFtPieChartDataSet.dataList[h.x.toInt()].rangeMax
                    )
                )
                root.findNavController().navigate(
                    R.id.action_landlordContractFragment_to_estateListFragment
                )
            }
        }

        override fun onNothingSelected() {
            Log.i("PieChart", "nothing selected")
        }
    }

    class PieChartActivity2() : OnChartValueSelectedListener {

        override fun onValueSelected(e: Entry?, h: Highlight) {
            if (e == null) return
            Log.i(
                "VAL SELECTED",
                "Value: " + e.y.toString() + ", index: " + h.x
                    .toString() + ", DataSet index: " + h.dataSetIndex
            )

            if (GlobalVariables.loginUser.permission.property != "N") {
                GlobalVariables.estateFolder = EstateList(
                    list = GlobalVariables.getContractExpireIn3MonthByTypeList(
                        GlobalVariables.contractExpireIn3MonthByTypePieChartDataSet.dataList[h.x.toInt()].tag
                    )
                )
                root.findNavController().navigate(
                    R.id.action_landlordContractFragment_to_estateListFragment
                )
            }
        }

        override fun onNothingSelected() {
            Log.i("PieChart", "nothing selected")
        }
    }
    private fun createPieChart1() {
        pieChart1 = root.pieChart1
        pieChart1.setUsePercentValues(false)
        pieChart1.description.isEnabled = false
        pieChart1.setExtraOffsets(5f, 10f, 5f, 5f)
        pieChart1.dragDecelerationFrictionCoef = 0.95f
        pieChart1.setCenterTextTypeface(Typeface.DEFAULT)
        pieChart1.centerText = generateCenterSpannableText()
        pieChart1.isDrawHoleEnabled = false
        pieChart1.setHoleColor(Color.WHITE)
        pieChart1.setTransparentCircleColor(Color.WHITE)
        pieChart1.setTransparentCircleAlpha(110)
        pieChart1.holeRadius = 58f
        pieChart1.transparentCircleRadius = 61f
        pieChart1.setDrawCenterText(false)
        pieChart1.rotationAngle = 0f
        // enable rotation of the chart by touch
        pieChart1.isRotationEnabled = true
        pieChart1.isHighlightPerTapEnabled = true

        // chart.setUnit(" €");
        // chart.setDrawUnitsInChart(true);

        // add a selection listener
        pieChart1.setOnChartValueSelectedListener(PieChartActivity1())
        pieChart1.animateY(1400, Easing.EaseInOutQuad)
        // chart.spin(2000, 0, 360);
        val l = pieChart1.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.xEntrySpace = 7f
        l.yEntrySpace = 0f
        l.yOffset = 0f

        // entry label styling
        pieChart1.setEntryLabelColor(Color.WHITE)
        pieChart1.setEntryLabelTypeface(Typeface.DEFAULT)
        pieChart1.setEntryLabelTextSize(12f)

        setPieChart1Data()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setPieChart1Data() {
        val entries: ArrayList<PieEntry> = ArrayList()

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        GlobalVariables.contractExpireIn3MonthBySquareFtPieChartDataSet.dataList.forEach {
            entries.add(
                PieEntry(
                    it.value.toFloat(),
                    it.tag
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

        pieChart1.data = data

        // undo all highlights
        pieChart1.highlightValues(null)
        pieChart1.invalidate()
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

    private fun createPieChart2() {
        pieChart2 = root.pieChart2
        pieChart2.setUsePercentValues(false)
        pieChart2.description.isEnabled = false
        pieChart2.setExtraOffsets(5f, 10f, 5f, 5f)
        pieChart2.dragDecelerationFrictionCoef = 0.95f
        pieChart2.setCenterTextTypeface(Typeface.DEFAULT)
        pieChart2.centerText = generateCenterSpannableText()
        pieChart2.isDrawHoleEnabled = false
        pieChart2.setHoleColor(Color.WHITE)
        pieChart2.setTransparentCircleColor(Color.WHITE)
        pieChart2.setTransparentCircleAlpha(110)
        pieChart2.holeRadius = 58f
        pieChart2.transparentCircleRadius = 61f
        pieChart2.setDrawCenterText(false)
        pieChart2.rotationAngle = 0f
        // enable rotation of the chart by touch
        pieChart2.isRotationEnabled = true
        pieChart2.isHighlightPerTapEnabled = true

        // chart.setUnit(" €");
        // chart.setDrawUnitsInChart(true);

        // add a selection listener
        pieChart2.setOnChartValueSelectedListener(PieChartActivity2())
        pieChart2.animateY(1400, Easing.EaseInOutQuad)
        // chart.spin(2000, 0, 360);
        val l = pieChart2.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.xEntrySpace = 7f
        l.yEntrySpace = 0f
        l.yOffset = 0f

        // entry label styling
        pieChart2.setEntryLabelColor(Color.WHITE)
        pieChart2.setEntryLabelTypeface(Typeface.DEFAULT)
        pieChart2.setEntryLabelTextSize(12f)

        setPieChart2Data()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setPieChart2Data() {
        val entries: ArrayList<PieEntry> = ArrayList()

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.

        GlobalVariables.contractExpireIn3MonthByTypePieChartDataSet.dataList.forEach {
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

        pieChart2.data = data

        // undo all highlights
        pieChart2.highlightValues(null)
        pieChart2.invalidate()
    }
}
