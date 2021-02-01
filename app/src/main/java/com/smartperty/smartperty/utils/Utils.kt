package com.smartperty.smartperty.utils

import android.graphics.Bitmap
import android.util.Log
import com.smartperty.smartperty.data.Contract
import com.smartperty.smartperty.data.Estate
import com.smartperty.smartperty.data.RepairOrder
import com.smartperty.smartperty.data.User

object Utils {
    var isModifyingUserList = false
    var isModifyingRepairList = false
    var isModifyingEstate = false
    var isModifyingContractList = false

    fun addUserToUserList(user: User) {
        Thread {
            val updateTarget = searchUserFromUserList(user.id)

//            while (isModifyingUserList)
//                Thread.sleep(500)

            isModifyingUserList = true

            if (updateTarget != null)
                updateTarget.update(user)
            else
                GlobalVariables.userList.add(user)

            isModifyingUserList = false

        }.start()
    }

    fun searchUserFromUserList(userId: String): User? {

        while (isModifyingUserList)
            Thread.sleep(500)

        isModifyingUserList = true

        val iterator = GlobalVariables.userList.iterator()
        while(iterator.hasNext()) {
            val user = iterator.next()
            if (user.compareId(userId)) {
                isModifyingUserList = false
                return user
            }
        }

        isModifyingUserList = false

        return null
    }

    fun getUser(userId: String): User? {
        if (userId == "nil")
            return null

        var user = searchUserFromUserList(userId)
        if (user == null) {
            user = GlobalVariables.api.getUserInformation(userId)
            val searchAgain = searchUserFromUserList(userId)
            if (searchAgain!= null) {
                return searchAgain
            }
            Log.d(">>>>>getUser", userId + ":" + user.name)
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
        Thread {
            val updateTarget = searchEstateFromEstateList(estate.objectId)

            isModifyingEstate = true

            if (updateTarget != null) {
                updateTarget.update(estate)
            }
            else
                GlobalVariables.estateList.add(estate)

            isModifyingEstate = false
        }.start()
    }

    fun searchEstateFromEstateList(objectId: String): Estate? {
        while (isModifyingEstate) {
            Thread.sleep(500)
        }

        isModifyingEstate = true

        val iterator = GlobalVariables.estateList.iterator()
        while(iterator.hasNext()) {
            val estate = iterator.next()
            if (estate.compareId(objectId)) {
                isModifyingEstate = false
                return estate
            }
        }

        isModifyingEstate = false

        return null
    }

    fun getEstate(objectId: String): Estate? {
        if (objectId == "nil") {
            return null
        }

        var estate = searchEstateFromEstateList(objectId)
        if (estate == null) {
            estate = GlobalVariables.api.getPropertyByObjectId(objectId)
            val searchAgain = searchEstateFromEstateList(objectId)
            if (searchAgain!= null) {
                return searchAgain
            }
            Log.d(">>>>>getEstate", objectId + ":" + estate!!.objectName)
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
                    if (searchEstateFromEstateList(estate.objectId) == null) {
                        Log.d(">>>>>getEstateGroupTag", estate.objectId + ":" + estate.objectName)
                        addEstateToEstateList(estate)
                    }
                }
            }.start()
        }
    }

    fun createEstate(estate:Estate) {
        Thread{
            GlobalVariables.estateFolder.list.add(estate)
            GlobalVariables.api.createProperty(estate)
            addEstateToEstateList(estate)
        }.start()
    }

    fun addRepairOrderToRepairList(repairOrder: RepairOrder) {
        Thread {
            val updateTarget = searchRepairOrderFromRepairList(repairOrder.event_id)

//            while (isModifyingRepairList)
//                Thread.sleep(500)

            isModifyingRepairList = true

            if (updateTarget != null) {
                updateTarget.update(repairOrder)
            }
            else
                GlobalVariables.repairList.add(repairOrder)

            isModifyingRepairList = false
        }.start()
    }

    fun searchRepairOrderFromRepairList(repairOrderId: String): RepairOrder? {
        while (isModifyingRepairList)
            Thread.sleep(500)

        isModifyingRepairList = true

        val iterator = GlobalVariables.repairList.iterator()
        while(iterator.hasNext()) {
            val repairOrder = iterator.next()
            if (repairOrder.compareId(repairOrderId)) {
                isModifyingRepairList = false
                return repairOrder
            }
        }

        GlobalVariables.repairList.forEach {
            if (it.compareId(repairOrderId))
                return it
        }

        isModifyingRepairList = false

        return null
    }

    fun getRepairOrder(repairOrderId: String): RepairOrder? {
        if (repairOrderId == "nil")
            return null

        var repairOrder = searchRepairOrderFromRepairList(repairOrderId)
        if (repairOrder == null) {
            repairOrder = GlobalVariables.api.getEventInformation(repairOrderId)
            val searchAgain = searchRepairOrderFromRepairList(repairOrderId)
            if (searchAgain!= null) {
                return searchAgain
            }
            Log.d(">>>>>getRepairOrder", repairOrderId + ":" + repairOrder!!.description)
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

            GlobalVariables.api.updateEventInformation(
                GlobalVariables.repairOrder.landlord!!.id,
                GlobalVariables.repairOrder.event_id,
                GlobalVariables.repairOrder.title,
                GlobalVariables.repairOrder.postList[0]
            )
        }.start()
    }

    fun createAccount(user: User, objectId: String) {
        val targetList =
            when (user.auth) {
                "tenant"-> {
                    GlobalVariables.loginUser.tenantList
                }
                "agent"->{
                    GlobalVariables.loginUser.agentList
                }
                "accountant"->{
                    GlobalVariables.loginUser.accountantList
                }
                "technician"->{
                    GlobalVariables.loginUser.technicianList
                }
                else-> {
                    GlobalVariables.loginUser.agentList
                }
            }

        targetList.add(user)

        Thread {
            GlobalVariables.api.createAccount(user,objectId)

            if (objectId != "nil") {
                val estate = getEstate(objectId)
                estate!!.tenant = user
            }
        }.start()
    }

    fun updateAccount(user: User) {
        Thread {
            GlobalVariables.api.updateUserInformation(user)
        }.start()
    }

    fun addContractToContractList(contract: Contract) {
        val updateTarget = searchContractFromContractList(contract.contractId)

        isModifyingContractList = true

        if (updateTarget != null) {
            updateTarget.update(contract)
        }
        else
            GlobalVariables.contractList.add(contract)

        isModifyingContractList = false
    }

    fun searchContractFromContractList(contractId: String): Contract? {
        while (isModifyingContractList)
            Thread.sleep(500)

        isModifyingContractList = true

        val iterator = GlobalVariables.contractList.iterator()
        while(iterator.hasNext()) {
            val contract = iterator.next()
            if (contract.compareId(contractId)) {
                isModifyingContractList = false
                return contract
            }
        }

        isModifyingContractList = false

        return null
    }

    fun getContract(landlordId: String, contractId: String): Contract? {
        if (contractId == "nil")
            return null

        var contract = searchContractFromContractList(contractId)
        if (contract == null) {
            contract = GlobalVariables.api.getContractByContractId(landlordId, contractId)
            val searchAgain = searchContractFromContractList(contractId)
            if (searchAgain!= null) {
                return searchAgain
            }
            Log.d(">>>>>getContract", contractId)
            addContractToContractList(contract)
        }

        return contract
    }

    fun createContract(contract:Contract) {
        Thread{
            contract.estate!!.contract = contract
            GlobalVariables.api.createContract(contract)
            addContractToContractList(contract)
        }.start()
    }

}