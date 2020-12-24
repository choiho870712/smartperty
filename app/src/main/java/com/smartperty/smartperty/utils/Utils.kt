package com.smartperty.smartperty.utils

import android.graphics.Bitmap
import com.smartperty.smartperty.data.Estate
import com.smartperty.smartperty.data.RepairOrder
import com.smartperty.smartperty.data.User

object Utils {
    fun addUserToUserList(user: User) {
        val updateTarget = searchUserFromUserList(user.id)
        if (updateTarget != null) {
            GlobalVariables.userList.remove(updateTarget)
            GlobalVariables.userList.add(user)
        }
        else
            GlobalVariables.userList.add(user)
    }

    fun searchUserFromUserList(userId: String): User? {
        GlobalVariables.userList.forEach {
            if (it.compareId(userId))
                return it
        }

        return null
    }

    fun getUser(userId: String): User? {
        if (userId == "nil")
            return null

        var user = searchUserFromUserList(userId)
        if (user == null) {
            user = GlobalVariables.api.getUserInformation(userId)
            addUserToUserList(user)
        }

        return user
    }

    fun getImage(imageUrl: String): Bitmap? {
        if (imageUrl.length < 10)
            return null

        return GlobalVariables.imageHelper.convertUrlToImage(imageUrl)
    }

    fun addEstateToEstateList(estate: Estate) {
        val updateTarget = searchEstateFromEstateList(estate.objectId)
        if (updateTarget != null) {
            GlobalVariables.estateList.remove(updateTarget)
            GlobalVariables.estateList.add(estate)
        }
        else
            GlobalVariables.estateList.add(estate)
    }

    fun searchEstateFromEstateList(objectId: String): Estate? {
        GlobalVariables.estateList.forEach {
            if (it.compareId(objectId))
                return it
        }

        return null
    }

    fun getEstate(objectId: String): Estate? {
        if (objectId == "nil")
            return null

        var estate = searchEstateFromEstateList(objectId)
        if (estate == null) {
            estate = GlobalVariables.api.getPropertyByObjectId(objectId)
            addEstateToEstateList(estate!!)
        }

        return estate
    }

    fun getEstateDirectoryByGroupTag() {
        GlobalVariables.estateDirectory = GlobalVariables.loginUser.estateDirectory
        GlobalVariables.estateDirectory.forEach {
            Thread {
                it.list = GlobalVariables.api.getPropertyByGroupTag(
                    it.title, GlobalVariables.loginUser.id)

                it.list.forEachIndexed { index, estate ->
                    addEstateToEstateList(estate)
                }
            }.start()
        }
    }

    fun createEstate(estate:Estate) {
        Thread{
            GlobalVariables.api.createProperty(estate)
            addEstateToEstateList(estate)
        }.start()
    }

    fun addRepairOrderToRepairList(repairOrder: RepairOrder) {
        val updateTarget = searchRepairOrderFromRepairList(repairOrder.event_id)
        if (updateTarget != null) {
            GlobalVariables.repairList.remove(updateTarget)
            GlobalVariables.repairList.add(repairOrder)
        }
        else
            GlobalVariables.repairList.add(repairOrder)
    }

    fun searchRepairOrderFromRepairList(repairOrderId: String): RepairOrder? {
        GlobalVariables.repairList.forEach {
            if (it.compareId(repairOrderId))
                return it
        }

        return null
    }

    fun getRepairOrder(repairOrderId: String): RepairOrder? {
        if (repairOrderId == "nil")
            return null

        var repairOrder = searchRepairOrderFromRepairList(repairOrderId)
        if (repairOrder == null) {
            repairOrder = GlobalVariables.api.getEventInformation(repairOrderId)
            addRepairOrderToRepairList(repairOrder)
        }

        return repairOrder
    }

    fun getRepairListByLandlordId(landlordId: String): MutableList<RepairOrder> {
        val repairList = GlobalVariables.api.landlordGetEventInformation(landlordId)

        repairList.forEach {
            addRepairOrderToRepairList(it)
        }

        return repairList
    }

    fun createRepairOrder(repairOrder: RepairOrder) {
        Thread {
            GlobalVariables.api.createEvent(repairOrder)
            addRepairOrderToRepairList(repairOrder)
            if (repairOrder.estate != null)
                repairOrder.estate!!.repairList.add(repairOrder)
        }.start()
    }
}