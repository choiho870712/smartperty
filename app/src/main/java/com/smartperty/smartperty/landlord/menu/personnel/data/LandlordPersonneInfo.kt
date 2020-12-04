package com.smartperty.smartperty.landlord.menu.personnel.data

import android.graphics.Bitmap

data class LandlordPersonneInfo(
    var name: String = "",
    var company: String = "",
    var phone: String = "",
    var image: Bitmap? = null
) {
}