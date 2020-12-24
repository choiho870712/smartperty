package com.smartperty.smartperty.data

import android.graphics.Bitmap
import com.smartperty.smartperty.utils.GlobalVariables

data class RepairOrder(
    var event_id: String = "",

    var creator: User? = null,
    var landlord: User? = null,
    var estate: Estate? = null,

    var timestamp: Long = 0,
    var status: String = "",
    var type: String = "",
    var description: String = "",
    var date: String = "",

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
}

data class RepairOrderPost(
    var sender: User = User(),
    var message: String = "message",
    var createDateTime: String = "createDateTime",
    var imageList: MutableList<Bitmap> = mutableListOf()
) {

}