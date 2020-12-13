package com.smartperty.smartperty.data

import android.graphics.Bitmap
import com.smartperty.smartperty.R
import com.smartperty.smartperty.utils.GlobalVariables

enum class RepairType {
    EQUIPMENT, CONTRACT, USAGE, UNKNOWN;

    companion object {
        private val VALUES = values();

        fun getByValue(value: Int) = VALUES.firstOrNull { it.ordinal == value }
    }
}

data class RepairOrder(
    var creator: User = GlobalVariables.user,
    var landlord: User = User(),
    var tenant: User = User(),
    var tenant_id: String = "",
    var plumber: User = User(),
    var plumber_id: String = "",
    var estate: Estate? = null,
    var type: RepairType = RepairType.UNKNOWN,
    var typeString: String = "",
    var title: String = "",
    var repairDateTime: String = "",
    var timestamp: Long = 0,
    var statusString: String = "",
    var event_id: String = "",
    var object_id: String = "",
    var initiate_id: String = "",
    var postList: MutableList<RepairOrderPost> = mutableListOf()
) {
    init {
        when (GlobalVariables.user.auth) {
            UserType.LANDLORD -> {
                landlord = GlobalVariables.user
            }
            UserType.TENANT -> {
                tenant = GlobalVariables.user
                landlord = GlobalVariables.landlord
                estate = GlobalVariables.estate
            }
            UserType.TECHNICIAN -> {
                plumber = GlobalVariables.user
            }
            else -> {

            }
        }
    }
}

data class RepairOrderPost(
    var sender: User = User(),
    var message: String = "message",
    var createDateTime: String = "createDateTime",
    var imageList: MutableList<Bitmap> = mutableListOf(),
    var imageStringList: MutableList<String> = mutableListOf()
) {

}