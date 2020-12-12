package com.smartperty.smartperty.data

data class Finance(
    var income: MutableList<FinanceItem> = mutableListOf(),
    var outcome: MutableList<FinanceItem> = mutableListOf()
) {
}

data class FinanceItem(
    var name: String,
    var value: Int
)