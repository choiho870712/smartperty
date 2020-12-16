package com.smartperty.smartperty.landlord.home

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.smartperty.smartperty.R
import com.smartperty.smartperty.chartUtil.MyMarkerView
import com.smartperty.smartperty.landlord.home.data.LandlordExpiringContract
import com.smartperty.smartperty.landlord.home.data.LandlordExpiringRent
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.activity_landlord.*
import kotlinx.android.synthetic.main.landlord_fragment_home_main.view.*
import kotlin.math.roundToInt

class LandlordHomeFragment : Fragment() {

    companion object {
        lateinit var lineChart: LineChart
    }

    private lateinit var root:View
    private lateinit var chart: LineChart

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.landlord_fragment_home_main, container, false)

        GlobalVariables.toolBarUtils.setVisibility(false)
        root.text_home_greet_name.text = GlobalVariables.user.name

        root.scroll_view.requestFocus()

        val expiringRentList = mutableListOf(
            LandlordExpiringRent(
                title = "大樓A",
                tenantName = "Jason",
                expireMonth = "3個月",
                rentAmount = "$30000"
            ),
            LandlordExpiringRent(
                title = "大樓B",
                tenantName = "Aiden",
                expireMonth = "20個月",
                rentAmount = "$200000"
            ),
            LandlordExpiringRent(
                title = "大樓C",
                tenantName = "Hugo",
                expireMonth = "4個月",
                rentAmount = "$40000"
            )
        )

        root.recycler_rent_list.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = LandlordExpiringRentAdapter(requireActivity(), root, expiringRentList)
        }
        root.recycler_rent_list.isFocusable = false

        val expiringContractList = mutableListOf(
            LandlordExpiringContract(
                title = "大樓C",
                address = "幸福大樓3樓15室",
                expireMonth = "2個月後到期"
            ),
            LandlordExpiringContract(
                title = "大樓D",
                address = "大仁大樓3樓17室",
                expireMonth = "2個月後到期"
            )
        )

        root.recycler_contract_list.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = LandlordExpiringContractAdapter(requireActivity(), root, expiringContractList)
        }
        root.recycler_contract_list.isFocusable = false


        createLineChart()

        root.card_landlord_home_notification.setOnClickListener {
            GlobalVariables.activity.bottom_nav.selectedItemId = R.id.landlord_notification
        }

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
            lineChart.setOnChartValueSelectedListener(
                LineChartActivity()
            )
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
        }
        var xAxis: XAxis
        run {
            // // X-Axis Style // //
            xAxis = lineChart.getXAxis()

            // vertical grid lines
            xAxis.setDrawGridLines(false)

            xAxis.position = XAxis.XAxisPosition.BOTTOM

            xAxis.axisMinimum = 0f
            xAxis.axisMaximum = 3f
            xAxis.labelCount = 4
            xAxis.granularity = 1.0f

            xAxis.valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return value.toInt().toString() + "/16"
                }
            }
        }
        var leftAxis: YAxis
        run {
            // // Y-Axis Style // //
            leftAxis = lineChart.getAxisLeft()

            // axis range
            leftAxis.axisMaximum = 20000f
            leftAxis.axisMinimum = 0f
            leftAxis.granularity = 4000f
        }
        var rightAxis: YAxis
        run {
            // // Y-Axis Style // //
            rightAxis = lineChart.axisRight

            // axis range
            rightAxis.axisMaximum = 20000f
            rightAxis.axisMinimum = 0f
            rightAxis.granularity = 4000f
        }

        // add data
        setLineChartData(4, 20000)

        // draw points over time
        lineChart.animateXY(1000, 1000)

        // get the legend (only possible after setting data)
        val l: Legend = lineChart.getLegend()

        // draw legend entries as lines
        l.form = Legend.LegendForm.LINE
    }

    private fun setLineChartData(count: Int, range: Int) {
        val values: ArrayList<Entry> = ArrayList()
        for (i in 0 until count) {
            val `val` = (Math.random() * range).roundToInt().toFloat()
            values.add(Entry(i.toFloat(), `val`))
        }
        val values2: ArrayList<Entry> = ArrayList()
        for (i in 0 until count) {
            val `val` = (Math.random() * range).roundToInt().toFloat()
            values2.add(Entry(i.toFloat(), `val`))
        }
        val set1: LineDataSet
        val set2: LineDataSet
        if (lineChart.getData() != null &&
            lineChart.getData().getDataSetCount() > 1
        ) {
            set1 = lineChart.getData().getDataSetByIndex(0) as LineDataSet
            set1.setValues(values)
            set1.notifyDataSetChanged()
            set2 = lineChart.getData().getDataSetByIndex(1) as LineDataSet
            set2.setValues(values2)
            set2.notifyDataSetChanged()
            lineChart.getData().notifyDataChanged()
            lineChart.notifyDataSetChanged()
        } else {
            // create a dataset and give it a type
            set1 = LineDataSet(values, "收入")
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

            // create a dataset and give it a type
            set2 = LineDataSet(values2, "支出")
            set2.setDrawIcons(false)

            // black lines and points
            set2.setColor(Color.RED)
            set2.setCircleColor(Color.BLUE)

            // line thickness and point size
            set2.setLineWidth(3f)
            set2.setCircleRadius(6f)

            // draw points as solid circles
            set2.setDrawCircleHole(true)
            set2.circleHoleRadius = 3f

            // text size of values
            set2.setValueTextSize(10f)

            val dataSets: ArrayList<ILineDataSet> = ArrayList()
            dataSets.add(set1) // add the data sets
            dataSets.add(set2) // add the data sets

            // create a data object with the data sets
            val data = LineData(dataSets)

            // set data
            lineChart.setData(data)
        }
    }
}