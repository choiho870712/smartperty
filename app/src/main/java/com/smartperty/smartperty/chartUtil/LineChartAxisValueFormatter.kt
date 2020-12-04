package com.smartperty.smartperty.chartUtil

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.DecimalFormat


class LineChartAxisValueFormatter : ValueFormatter() {
    override fun getFormattedValue(value: Float, axis: AxisBase): String {

        return axis.getFormattedLabel(value.toInt()) + "/26"
    }


}