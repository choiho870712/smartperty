package com.smartperty.smartperty.landlord.menu.`object`.data

data class LandlordObjectList(
    var title: String = "",
    var itemList: MutableList<LandlordObjectItem> = mutableListOf()
) {
}