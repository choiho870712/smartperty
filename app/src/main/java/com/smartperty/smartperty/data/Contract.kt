package com.smartperty.smartperty.data

data class Contract(
    var estate: Estate? = null,
    var landlord: User? = null,
    var tenant: User? = null,
    var rent: Int = 0,
    var payment_method: String = "",
    var startDate: Long = 0,
    var nextDate: Long = 0,
    var endDate: Long = 0,
    var timeLeft: Int = 0,
    var currency: String = "",
    var deposit: Int = 0
)