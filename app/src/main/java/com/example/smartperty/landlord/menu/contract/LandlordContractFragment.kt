package com.example.smartperty.landlord.menu.contract

import android.graphics.RectF
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartperty.R
import com.example.smartperty.chartUtil.DayAxisValueFormatter
import com.example.smartperty.chartUtil.MyAxisValueFormatter
import com.example.smartperty.chartUtil.XYMarkerView
import com.example.smartperty.landlord.menu.`object`.data.LandlordObjectItem
import com.example.smartperty.landlord.menu.`object`.data.LandlordObjectList
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.model.GradientColor
import com.github.mikephil.charting.utils.MPPointF
import kotlinx.android.synthetic.main.landlord_fragment_contract.view.*


class LandlordContractFragment : Fragment(), OnChartValueSelectedListener {

    private lateinit var root:View

    private lateinit var chart: BarChart

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.landlord_fragment_contract, container, false)

        val folderList = LandlordObjectList(
            title = "list1",
            itemList = mutableListOf(
                LandlordObjectItem(title = "item1")
            )
        )

        root.recycler_contract_list.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = LandlordContractAdapter(requireActivity(), root, folderList.itemList)
        }

        createBarChart()

        return root
    }

    private fun createBarChart() {
        chart = root.chart1
        chart.setOnChartValueSelectedListener(this)

        chart.setDrawBarShadow(false)
        chart.setDrawValueAboveBar(true)

        chart.description.isEnabled = false

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        chart.setMaxVisibleValueCount(60)

        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false)

        chart.setDrawGridBackground(false)

        val xAxisFormatter = DayAxisValueFormatter(chart)
        val xAxis = chart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.typeface = Typeface.DEFAULT
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f
        xAxis.labelCount = 7
        xAxis.valueFormatter = xAxisFormatter

        val custom = MyAxisValueFormatter()
        val leftAxis = chart.axisLeft
        leftAxis.typeface = Typeface.DEFAULT
        leftAxis.setLabelCount(8, false)
        leftAxis.valueFormatter = custom
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        leftAxis.spaceTop = 15f
        leftAxis.axisMinimum = 0f

        val rightAxis = chart.axisRight
        rightAxis.setDrawGridLines(false)
        rightAxis.typeface = Typeface.DEFAULT
        rightAxis.setLabelCount(8, false)
        rightAxis.spaceTop = 15f
        rightAxis.axisMinimum = 0f

        val legend = chart.legend
        legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        legend.setDrawInside(false)
        legend.form = Legend.LegendForm.SQUARE
        legend.formSize = 9f
        legend.textSize = 11f
        legend.xEntrySpace = 11f

        val markerView = XYMarkerView(requireContext(), xAxisFormatter)
        markerView.chartView = chart
        chart.marker = markerView

        setData(12, 50)
        chart.invalidate()
    }

    private fun setData(count: Int, range: Int) {
        val start = 1f

        val values: ArrayList<BarEntry> = ArrayList()

        for (i in start.toInt() until start.toInt() + count) {
            val `val` = (Math.random() * (range + 1)).toFloat()
            if (Math.random() * 100 < 25) {
                values.add(BarEntry(i.toFloat(), `val`, resources.getDrawable(R.drawable.star)))
            } else {
                values.add(BarEntry(i.toFloat(), `val`))
            }
        }

        val set1: BarDataSet

        if (chart.data != null &&
            chart.data.dataSetCount > 0
        ) {
            set1 = chart.data.getDataSetByIndex(0) as BarDataSet
            set1.values = values
            chart.data.notifyDataChanged()
            chart.notifyDataSetChanged()
        } else {
            set1 = BarDataSet(values, "到期個數/日")
            set1.setDrawIcons(false)
            val startColor1 = ContextCompat.getColor(
                requireContext(),
                android.R.color.holo_orange_light
            )
            val startColor2 = ContextCompat.getColor(
                requireContext(),
                android.R.color.holo_blue_light
            )
            val startColor3 = ContextCompat.getColor(
                requireContext(),
                android.R.color.holo_orange_light
            )
            val startColor4 = ContextCompat.getColor(
                requireContext(),
                android.R.color.holo_green_light
            )
            val startColor5 = ContextCompat.getColor(
                requireContext(),
                android.R.color.holo_red_light
            )
            val endColor1 = ContextCompat.getColor(requireContext(), android.R.color.holo_blue_dark)
            val endColor2 = ContextCompat.getColor(requireContext(), android.R.color.holo_purple)
            val endColor3 = ContextCompat.getColor(
                requireContext(),
                android.R.color.holo_green_dark
            )
            val endColor4 = ContextCompat.getColor(requireContext(), android.R.color.holo_red_dark)
            val endColor5 = ContextCompat.getColor(
                requireContext(),
                android.R.color.holo_orange_dark
            )
            val gradientFills: MutableList<GradientColor> = ArrayList()
            gradientFills.add(GradientColor(startColor1, endColor1))
            gradientFills.add(GradientColor(startColor2, endColor2))
            gradientFills.add(GradientColor(startColor3, endColor3))
            gradientFills.add(GradientColor(startColor4, endColor4))
            gradientFills.add(GradientColor(startColor5, endColor5))
            set1.gradientColors = gradientFills

            val dataSets: ArrayList<IBarDataSet> = ArrayList()
            dataSets.add(set1)
            val data = BarData(dataSets)
            data.setValueTextSize(10f)
            data.setValueTypeface(Typeface.DEFAULT)
            data.barWidth = 0.9f
            chart.data = data
        }
    }


    private val onValueSelectedRectF: RectF = RectF()

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        if (e == null) return

        val bounds: RectF = onValueSelectedRectF
        chart.getBarBounds(e as BarEntry?, bounds)
        val position: MPPointF = chart.getPosition(e, YAxis.AxisDependency.LEFT)

        Log.i("bounds", bounds.toString())
        Log.i("position", position.toString())

        Log.i(
            "x-index",
            "low: " + chart.lowestVisibleX + ", high: "
                    + chart.highestVisibleX
        )

        MPPointF.recycleInstance(position)
    }

    override fun onNothingSelected() {
        
    }
}
