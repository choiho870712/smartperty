package com.example.smartperty.utils

import android.content.Context
import android.graphics.Color
import android.view.View
import com.example.smartperty.R
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.*
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


/**
 *
 * @author Administrator
 *
 * @version 1.0
 *
 * @description class description
 *
 * @date 2018/1/2
 */
class BarChartManager(private val context: Context, private val mBarChart: BarChart) {
    private val leftAxis: YAxis
    private val rightAxis: YAxis
    private val xAxis: XAxis

    /**/
    private fun initLineChart() {
        mBarChart.setDrawValueAboveBar(false)
        mBarChart.description.isEnabled = false

        //背景颜色

        //mBarChart.setBackground(SupportApp.drawable(R.drawable.shape_out_in));

        //网格
        mBarChart.setDrawGridBackground(false)

        //背景阴影
        mBarChart.setDrawBarShadow(false)
        mBarChart.isHighlightFullBarEnabled = false

        //显示边界
        mBarChart.setDrawBorders(false)
        mBarChart.setPadding(1, 1, 1, 1)

        //设置动画效果
        mBarChart.animateY(1000, Easing.EasingOption.Linear)
        mBarChart.animateX(1000, Easing.EasingOption.Linear)
        mBarChart.textAlignment = View.TEXT_ALIGNMENT_CENTER
        mBarChart.onTouchListener = null

        //折线图例 标签 设置
        val legend = mBarChart.legend
        legend.form = Legend.LegendForm.CIRCLE
        legend.textSize = 11f
        legend.textColor = Color.WHITE

        //显示位置
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        legend.setDrawInside(false)
        legend.xEntrySpace = 32f
        legend.textSize = 12f

        //XY轴的设置

        //X轴设置显示位置在底部
        rightAxis.isEnabled = false
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.textSize = 10f
        xAxis.textColor = Color.WHITE
        xAxis.axisMinimum = 0f
        leftAxis.axisMinimum = 0f
        leftAxis.textSize = 12f
        leftAxis.textColor = Color.WHITE
        xAxis.isGranularityEnabled = true
        xAxis.granularity = 0.9f
        xAxis.setCenterAxisLabels(true)
        mBarChart.notifyDataSetChanged()
    }

    /**
     *
     * 展示柱状图(一条)
     *
     */
    fun showBarChart(
        xAxisValues: List<Float?>, yAxisValues: List<Float?>, label: String?,
        color: Int
    ) {
        initLineChart()
        val entries: ArrayList<BarEntry> = ArrayList()
        for (i in yAxisValues.indices) {
            entries.add(BarEntry(xAxisValues[i]!!, yAxisValues[i]!!))
        }

        // 每一个BarDataSet代表一类柱状图
        val barDataSet = BarDataSet(entries, label)
        barDataSet.color = color
        barDataSet.valueTextSize = 9f
        barDataSet.formLineWidth = 1f
        barDataSet.formSize = 15f
        barDataSet.axisDependency = YAxis.AxisDependency.LEFT
        val data = BarData(barDataSet)
        data.setDrawValues(false)
        data.barWidth = 0.5f
        data.setValueTextColor(context.resources.getColor(R.color.colorPrimary))

        //设置X轴的刻度数
        xAxis.setLabelCount(xAxisValues.size - 1, false)
        mBarChart.data = data
        mBarChart.invalidate()
    }

    /**
     *
     * 展示柱状图(多条)
     *
     */
    fun showBarChart(
        xAxisValues: List<Float?>, yAxisValues: List<List<Float?>>,
        labels: List<String?>, colours: List<Int?>
    ) {
        initLineChart()
        val data = BarData()
        for (i in yAxisValues.indices) {
            val entries: ArrayList<BarEntry> = ArrayList()
            for (j in yAxisValues[i].indices) {
                entries.add(BarEntry(xAxisValues[j]!!, yAxisValues[i][j]!!))
            }
            val barDataSet = BarDataSet(entries, labels[i])
            barDataSet.color = colours[i]!!
            barDataSet.valueTextColor = colours[i]!!
            barDataSet.valueTextSize = 10f
            barDataSet.axisDependency = YAxis.AxisDependency.LEFT
            barDataSet.setDrawValues(false)
            data.addDataSet(barDataSet)
        }
        val amount = yAxisValues.size
        val groupSpace = 0.5f //柱状图组之间的间距
        val barSpace = ((1 - 0.12) / amount / 20).toFloat() // x4 DataSet
        val barWidth = ((1 - 0.12) / amount / 10 * 4).toFloat() // x4 DataSet


        // (0.2 + 0.02) * 4 + 0.08 = 1.00 -> interval per "group"
        xAxis.setLabelCount(xAxisValues.size - 1, false)
        val values: MutableList<String> = mutableListOf()
        values.add("11-01")
        values.add("11-02")
        values.add("11-03")
        values.add("11-04")
        values.add("11-05")
        values.add("11-06")
        values.add("11-07")
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = IAxisValueFormatter{
                value, _-> SimpleDateFormat("MM:dd", Locale.ENGLISH).format(value.toLong()) }
        data.barWidth = barWidth
        data.groupBars(0f, groupSpace, barSpace)
        mBarChart.notifyDataSetChanged()
        mBarChart.data = data
        mBarChart.invalidate()
    }

    /**
     *
     * 展示柱状图(多条)
     *
     */
    fun showBarChart(
        xAxisValues: List<Float?>, yAxisValues: List<List<Float?>>,
        labels: List<String?>, colours: List<Int?>, values: List<String?>?
    ) {
        initLineChart()
        val data = BarData()
        for (i in yAxisValues.indices) {
            val entries: ArrayList<BarEntry> = ArrayList()
            for (j in yAxisValues[i].indices) {
                entries.add(BarEntry(xAxisValues[j]!!, yAxisValues[i][j]!!))
            }
            val barDataSet = BarDataSet(entries, labels[i])
            barDataSet.color = colours[i]!!
            barDataSet.valueTextColor = colours[i]!!
            barDataSet.valueTextSize = 10f
            barDataSet.axisDependency = YAxis.AxisDependency.LEFT
            barDataSet.setDrawValues(false)
            data.addDataSet(barDataSet)
        }
        val amount = yAxisValues.size
        val groupSpace = 0.5f //柱状图组之间的间距
        val barSpace = ((1 - 0.12) / amount / 20).toFloat() // x4 DataSet
        val barWidth = ((1 - 0.12) / amount / 10 * 4).toFloat() // x4 DataSet
        xAxis.setLabelCount(xAxisValues.size - 1, false)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = IAxisValueFormatter{
                value, _-> SimpleDateFormat("MM:dd", Locale.ENGLISH).format(value.toLong()) }
        data.barWidth = barWidth
        data.groupBars(0f, groupSpace, barSpace)
        mBarChart.notifyDataSetChanged()
        mBarChart.data = data
    }

    /**
     *
     * 设置Y轴值
     *
     */
    fun setYAxis(max: Float, min: Float, labelCount: Int) {
        if (max < min) {
            return
        }
        leftAxis.axisMaximum = max
        leftAxis.axisMinimum = min
        leftAxis.setLabelCount(labelCount, false)
        rightAxis.axisMaximum = max
        rightAxis.axisMinimum = min
        rightAxis.setLabelCount(labelCount, false)
        mBarChart.invalidate()
    }

    /**
     *
     * 设置X轴的值
     *
     */
    fun setXAxis(max: Float, min: Float, labelCount: Int) {
        xAxis.axisMaximum = max
        xAxis.axisMinimum = min
        xAxis.setLabelCount(labelCount, false)
        mBarChart.invalidate()
    }

    /**
     *
     * 设置高限制线
     *
     */
    fun setHightLimitLine(high: Float, name: String?, color: Int) {
        var name = name
        if (name == null) {
            name = "高限制线"
        }
        val hightLimit = LimitLine(high, name)
        hightLimit.lineWidth = 4f
        hightLimit.textSize = 10f
        hightLimit.lineColor = color
        hightLimit.textColor = color
        leftAxis.addLimitLine(hightLimit)
        mBarChart.invalidate()
    }

    /**
     *
     * 设置低限制线
     *
     */
    fun setLowLimitLine(low: Int, name: String?) {
        var name = name
        if (name == null) {
            name = "低限制线"
        }
        val hightLimit = LimitLine(low.toFloat(), name)
        hightLimit.lineWidth = 4f
        hightLimit.textSize = 10f
        leftAxis.addLimitLine(hightLimit)
        mBarChart.invalidate()
    }

    /**
     *
     * 设置描述信息
     *
     */
    fun setDescription(str: String?) {
        val description = Description()
        description.setText(str)
        mBarChart.description = description
        mBarChart.invalidate()
    }

    /**
     *
     * 展示柱状图(多条)
     *
     */
    fun showBarChartPlan(
        xAxisValues: List<Float?>, yAxisValues: List<List<Float?>>,
        labels: List<String?>, colours: List<Int?>, values: List<String?>?
    ) {
        initLineChart()
        val data = BarData()
        for (i in yAxisValues.indices) {
            val entries: ArrayList<BarEntry> = ArrayList()
            for (j in yAxisValues[i].indices) {
                entries.add(BarEntry(xAxisValues[j]!!, yAxisValues[i][j]!!))
            }
            val barDataSet = BarDataSet(entries, labels[i])
            barDataSet.color = colours[i]!!
            barDataSet.valueTextColor = colours[i]!!
            barDataSet.valueTextSize = 10f
            barDataSet.axisDependency = YAxis.AxisDependency.LEFT
            barDataSet.setDrawValues(false)
            data.addDataSet(barDataSet)
        }
        val amount = yAxisValues.size
        val groupSpace = 0.5f //柱状图组之间的间距
        val barSpace = ((1 - 0.12) / amount / 20).toFloat() // x4 DataSet
        val barWidth = ((1 - 0.12) / amount / 10 * 4).toFloat() // x4 DataSet
        xAxis.setLabelCount(xAxisValues.size - 1, false)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = IAxisValueFormatter{
                value, _-> SimpleDateFormat("MM:dd", Locale.ENGLISH).format(value.toLong()) }
        xAxis.xOffset = barSpace
        data.barWidth = barWidth
        data.groupBars(0f, groupSpace, barSpace)
        mBarChart.setDrawValueAboveBar(true)
        mBarChart.notifyDataSetChanged()
        mBarChart.data = data
    }

    init {
        leftAxis = mBarChart.axisLeft
        rightAxis = mBarChart.axisRight
        xAxis = mBarChart.xAxis
    }
}