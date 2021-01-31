package com.smartperty.smartperty.data

import android.graphics.Bitmap
import com.smartperty.smartperty.utils.GlobalVariables

data class RepairOrder(
    var event_id: String = "nil",

    var creator: User? = null,
    var landlord: User? = null,
    var estate: Estate? = null,

    var timestamp: Long = 0,
    var status: String = "nil",
    var type: String = "nil",
    var description: String = "nil",
    var date: String = "nil",
    var cost: Int = 0,
    var title: String = "nil",

    var participant: MutableList<User> = mutableListOf(),
    var postList: MutableList<RepairOrderPost> = mutableListOf()
) {
    fun compareId(id:String): Boolean {
        return id == event_id
    }

    fun setTechnician(user: User) {
        if (getTechnician() == null) {
            participant.add(user)
        }
    }

    fun getTechnician(): User? {
        return participant.find {
            it.auth == "technician"
        }
    }

    fun setTenant(user: User) {
        if (getTenant() == null) {
            participant.add(user)
        }
    }

    fun getTenant(): User? {
        return participant.find {
            it.auth == "tenant"
        }
    }

    fun update(updateInfo: RepairOrder) {
        event_id = updateInfo.event_id

        creator = updateInfo.creator
        landlord = updateInfo.landlord
        estate = updateInfo.estate

        timestamp = updateInfo.timestamp
        status = updateInfo.status
        type = updateInfo.type
        description = updateInfo.description
        date = updateInfo.date

        title = updateInfo.title

        participant = updateInfo.participant
        postList = updateInfo.postList
    }
}

data class RepairOrderPost(
    var sender: User? = null,
    var message: String = "message",
    var createDateTime: String = "createDateTime",
    var imageList: MutableList<Bitmap> = mutableListOf()
) {

}