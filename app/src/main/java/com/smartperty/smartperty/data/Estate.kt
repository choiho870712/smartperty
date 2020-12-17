package com.smartperty.smartperty.data

import android.graphics.Bitmap
import com.smartperty.smartperty.utils.GlobalVariables

data class Equipment(
    var name: String = "",
    var count: Int = 0,
    var image: Bitmap? = null,
    var imageUrl: String = ""
)

data class Room(
    var name: String = "",
    var equipmentList: MutableList<Equipment> = mutableListOf()
)

data class Estate (
    var title: String = "",
    var address: String = "",
    var floor: Int = 0,
    var squareFt: Double = 0.0,
    var parkingSpace: String = "",
    var content: String = "",
    var contract: Contract = Contract(),
    var imageList: MutableList<Bitmap> = mutableListOf(),
    var imageUrlList: MutableList<String> = mutableListOf(),
    var repairList: MutableList<RepairOrder> = mutableListOf(),
    var contractId: String = "",
    var contractHistoryIdList: MutableList<String> = mutableListOf(),
    var objectId: String = "",
    var repairListId: MutableList<String> = mutableListOf(),
    var road: String = "",
    var street: String = "",
    var district: String = "",
    var type: String = "",
    var groupName: String = "",
    var rules: String = "",
    var systemId: String = "",
    var attractionList: MutableList<String> = mutableListOf(),
    var roomList: MutableList<Room> = mutableListOf()
) {
    init {
        repairList.forEach {
            it.estate = this
        }
    }

    fun isRented(): Boolean {
        return contract.isEstablished()
    }

    override fun toString(): String {
        return ""
    }
}

data class EstateList(
    var title: String = "title",
    var image: Bitmap? = null,
    var imageUrl: String = "",
    var list: MutableList<Estate> = mutableListOf()
) {
    init {
        list.forEach {
            it.contract.estate = it
            it.contract.landlord = GlobalVariables.landlord
        }
    }


    fun calculateSquareFt(): Double {
        var totalSquareFt = 0.0
        list.forEach {
            totalSquareFt += it.squareFt
        }
        return totalSquareFt
    }

    fun calculateRentRate(): Int {
        var rentCount = 0
        list.forEach {
            if (it.isRented())
                rentCount += 1
        }

        return if (rentCount == 0) 0
            else rentCount*100/list.size
    }

    fun searchEstateByObjectId(objectId: String): Estate? {
        list.forEach {
            if (it.objectId == objectId)
                return it
        }
        return null
    }
}