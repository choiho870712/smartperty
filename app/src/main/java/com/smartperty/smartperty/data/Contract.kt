package com.smartperty.smartperty.data

data class Contract(
    var estate: Estate? = null,
    var landlord: UserInfo? = null,
    var tenant: UserInfo? = null,
    var rentAmount: Int = 0,
    var rentPerMonthNumber: Int = 1,
    var rentEndDate: String = ""
) {
    fun isEstablished(): Boolean {
        return estate != null && landlord != null && tenant != null
    }
}