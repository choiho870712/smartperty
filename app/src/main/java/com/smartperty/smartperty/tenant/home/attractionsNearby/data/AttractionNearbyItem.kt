package com.smartperty.smartperty.tenant.home.attractionsNearby.data

data class AttractionNearbyItem(
    var name: String = "",
    var region: String = "",
    var road: String = "",
    var street: String = "",
    var address: String = "",
    var description: String = ""
) {
    fun getFullAddress() : String {
        return region + road + street + address
    }
}