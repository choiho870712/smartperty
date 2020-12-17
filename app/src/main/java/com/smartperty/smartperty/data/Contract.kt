package com.smartperty.smartperty.data

data class Contract(
    var estate: Estate? = null,
    var landlord: User? = null,
    var tenant: User? = null,
    var rentAmount: Int = 0,
    var rentPerMonthNumber: Int = 1,
    var rentEndDate: String = "",
    var purchasePrice: Int = 0
) {
    fun isEstablished(): Boolean {
        return estate != null && landlord != null && tenant != null
    }
}