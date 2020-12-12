package com.smartperty.smartperty.data

enum class ChartDataType{
    LINE_CHART, BAR_CHART, PIE_CHART, UNKNOWN
}

data class ChartData(
    var type: ChartDataType = ChartDataType.UNKNOWN,
    var dataList: MutableList<ChartDataPair> = mutableListOf()
) {
}

data class ChartDataPair(
    var tag:String = "",
    var value: Int = 0,
    var value2: Float = 0f
)