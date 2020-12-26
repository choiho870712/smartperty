package com.smartperty.smartperty.data

data class Contract(
    var contractId: String = "nil",
    var estate: Estate? = null,
    var landlord: User? = null,
    var tenant: User? = null,
    var rent: Int = 0,
    var payment_method: String = "monthly",
    var startDate: Long = 0,
    var nextDate: Long = 0,
    var endDate: Long = 0,
    var timeLeft: Int = 0,
    var currency: String = "",
    var deposit: Int = 0,
    var pdfUrl: MutableList<String> = mutableListOf(),
    var jpgUrl: MutableList<String> = mutableListOf()
) {
    fun compareId(id: String): Boolean {
        return id == this.contractId
    }

    fun update(updateInfo: Contract) {
        contractId = updateInfo.contractId
        estate = updateInfo.estate
        landlord = updateInfo.landlord
        tenant = updateInfo.tenant
        rent = updateInfo.rent
        payment_method = updateInfo.payment_method
        startDate = updateInfo.startDate
        nextDate = updateInfo.nextDate
        endDate = updateInfo.endDate
        timeLeft = updateInfo.timeLeft
        currency = updateInfo.currency
        deposit = updateInfo.deposit
        pdfUrl = updateInfo.pdfUrl
        jpgUrl = updateInfo.jpgUrl
    }
}

