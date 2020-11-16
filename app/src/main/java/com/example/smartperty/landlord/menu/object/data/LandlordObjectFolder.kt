package com.example.smartperty.landlord.menu.`object`.data

data class LandlordObjectFolder(
    var title: String = "",
    var folderList: MutableList<LandlordObjectList> = mutableListOf()
) {
}