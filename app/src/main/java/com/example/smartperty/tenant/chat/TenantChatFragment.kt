package com.example.smartperty.tenant.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.smartperty.R
import com.example.smartperty.utils.BarChartManager
import com.github.mikephil.charting.charts.BarChart
import kotlinx.android.synthetic.main.tenant_fragment_chat_main.view.*
import kotlin.collections.ArrayList


class TenantChatFragment : Fragment() {

    private lateinit var root: View

    private lateinit var barChart: BarChart
    private lateinit var barChartManager : BarChartManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.tenant_fragment_chat_main, container, false)

        barChart = root.bar_chart_test
        initBarChart(barChart)

        return root
    }

    private fun initBarChart(chart: BarChart) {
        barChartManager = BarChartManager(requireContext(), chart)
        val xValues: MutableList<Float> = ArrayList()
        for (i in 0..6) {
            xValues.add(i.toFloat())
        }

        //设置y轴的数据()
        val yValues: MutableList<List<Float>> = ArrayList()
        for (i in 0..3) {
            val yValue: MutableList<Float> = ArrayList()
            for (j in 0..6) {
                yValue.add((Math.random() * 8000).toFloat())
            }
            yValues.add(yValue)
        }

        //颜色集合
        val colours: MutableList<Int> = ArrayList()
        colours.add(resources.getColor(R.color.colorPrimary))
        colours.add(resources.getColor(R.color.colorPrimaryDark))
        colours.add(resources.getColor(R.color.design_default_color_primary_dark))
        colours.add(resources.getColor(R.color.design_default_color_error))

        //线的名字集合
        val names: MutableList<String> = ArrayList()
        names.add("米")
        names.add("面")
        names.add("粮")
        names.add("油")


        //创建多条折线的图表
        barChartManager.showBarChart(xValues, yValues, names, colours)
    }

}
