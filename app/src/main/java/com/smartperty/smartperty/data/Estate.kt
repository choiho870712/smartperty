package com.smartperty.smartperty.data

import android.graphics.Bitmap
import com.smartperty.smartperty.utils.GlobalVariables

data class Equipment(
    var name: String = "nil",
    var count: Int = 0,
    var image: Bitmap? = null
)

data class Room(
    var name: String = "",
    var equipmentList: MutableList<Equipment> = mutableListOf()
)

data class Estate (
    var objectId: String = "nil",

    var landlord: User? = null,
    var tenant: User? = null,
    var contract: Contract? = null,

    var groupName: String = "",
    var objectName: String = "",
    var description: String = "",
    var region: String = "",
    var street: String = "",
    var road: String = "",
    var fullAddress: String = "",
    var floor: String = "",
    var area: Double = 0.0,
    var rent: Int = 0,
    var parkingSpace: String = "",
    var type: String = "",
    var purchasePrice: Long = 0,
    var rules: String = "",

    var roomList: MutableList<Room> = mutableListOf(),
    var attractionList: MutableList<String> = mutableListOf(),
    var imageList: MutableList<Bitmap> = mutableListOf(),
    var repairList: MutableList<RepairOrder> = mutableListOf(),
    var contractHistoryList: MutableList<Contract> = mutableListOf()
) {
    fun compareId(id: String): Boolean {
        return id == this.objectId
    }

    fun isRented(): Boolean {
        return contract != null
    }

    fun getAddress(): String {
        return this.region + this.road + this.street + this.fullAddress
    }

    fun update(updateInfo: Estate) {
        objectId = updateInfo.objectId

        landlord = updateInfo.landlord
        tenant = updateInfo.tenant
        contract = updateInfo.contract

        groupName = updateInfo.groupName
        objectName = updateInfo.objectName
        description = updateInfo.description
        region = updateInfo.region
        street = updateInfo.street
        road = updateInfo.road
        fullAddress = updateInfo.fullAddress
        floor = updateInfo.floor
        area = updateInfo.area
        rent = updateInfo.rent
        parkingSpace = updateInfo.parkingSpace
        type = updateInfo.type
        purchasePrice = updateInfo.purchasePrice
        rules = updateInfo.rules

        roomList = updateInfo.roomList
        attractionList = updateInfo.attractionList
        imageList = updateInfo.imageList
        repairList = updateInfo.repairList
        contractHistoryList = updateInfo.contractHistoryList
    }

    override fun toString(): String {
        return ""
    }
}

data class EstateList(
    var title: String = "",
    var image: Bitmap? = null,
    var imageUrl: String = "",
    var list: MutableList<Estate> = mutableListOf()
) {
    fun calculateSquareFt(): Double {
        var totalSquareFt = 0.0
        list.forEach {
            totalSquareFt += it.area
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