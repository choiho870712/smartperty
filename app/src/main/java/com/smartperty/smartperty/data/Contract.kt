package com.smartperty.smartperty.data

import android.graphics.Bitmap

data class RentRecord(
    var timeStamp: Long = 0,
    var isPay: Boolean = false
)

data class Contract(
    var contractId: String = "nil",
    var estate: Estate? = null,
    var landlord: User? = null,
    var tenant: User? = null,
    var rent: Int = 0,
    var payment_method: String = "monthly",
    var startDate: Long = 0,
    var endDate: Long = 0,
    var timeLeft: Int = 0,
    var currency: String = "nil",
    var deposit: Int = 0,
    var pdfUrl: MutableList<String> = mutableListOf(),
    var jpgUrl: MutableList<String> = mutableListOf(),

    var rentRecordList: MutableList<RentRecord> = mutableListOf(),

    var textString: String = "",
    var pdfString: String = "",
    var jpgBitmapList: MutableList<Bitmap> = mutableListOf()
) {

    fun getNextDate(): Long {
        rentRecordList.forEach {
            if (!it.isPay) {
                return it.timeStamp
            }
        }

        return 0
    }

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
        endDate = updateInfo.endDate
        timeLeft = updateInfo.timeLeft
        currency = updateInfo.currency
        deposit = updateInfo.deposit
        pdfUrl = updateInfo.pdfUrl
        jpgUrl = updateInfo.jpgUrl
        rentRecordList = updateInfo.rentRecordList
        textString = updateInfo.textString
        pdfString = updateInfo.pdfString
        jpgBitmapList = updateInfo.jpgBitmapList
    }
}

