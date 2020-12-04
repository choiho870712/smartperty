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

enum class RepairStatus {
    CREATED, CHOOSING_TENANT, CHOOSING_PLUMBER,
    SENDING_TO_LANDLORD, SENDING_TO_PLUMBER,
    SENT_TO_LANDLORD, SENT_TO_PLUMBER, RUNNING, DONE;

    companion object {
        fun getStringByStatus(status: RepairStatus) =
            GlobalVariables.activity.resources
                .getStringArray(R.array.repair_status_list)[status.ordinal]
    }
}

data class RepairOrder(
    var creator: UserInfo = GlobalVariables.user.userInfo,
    var landlord: UserInfo = UserInfo(),
    var tenant: UserInfo = UserInfo(),
    var plumber: UserInfo = UserInfo(),
    var estate: Estate? = null,
    var type: RepairType = RepairType.UNKNOWN,
    var title: String = "",
    var repairDateTime: String = "",
    var status: RepairStatus = RepairStatus.CREATED,
    var postList: MutableList<RepairOrderPost> = mutableListOf()
) {
    init {
        when (GlobalVariables.user.userInfo.type) {
            UserType.LANDLORD -> {
                landlord = GlobalVariables.user.userInfo
            }
            UserType.TENANT -> {
                tenant = GlobalVariables.user.userInfo
                landlord = GlobalVariables.landlord
                estate = GlobalVariables.estate
            }
            UserType.PLUMBER -> {
                plumber = GlobalVariables.user.userInfo
            }
            else -> {

            }
        }
    }

    fun nextStatus() {
        when(status) {
            RepairStatus.CREATED -> {
                if (creator.type == UserType.TENANT)
                    status = RepairStatus.SENDING_TO_LANDLORD
                else if (creator.type == UserType.LANDLORD)
                    status = RepairStatus.CHOOSING_TENANT
            }
            RepairStatus.SENDING_TO_LANDLORD -> {
                status = RepairStatus.SENT_TO_LANDLORD
            }
            RepairStatus.SENT_TO_LANDLORD -> {
                status = RepairStatus.CHOOSING_PLUMBER
            }
            RepairStatus.CHOOSING_TENANT -> {
                if (estate != null)
                    status = RepairStatus.CHOOSING_PLUMBER
            }
            RepairStatus.CHOOSING_PLUMBER -> {
                if (plumber.name.isNotEmpty())
                    status = RepairStatus.SENDING_TO_PLUMBER
            }
            RepairStatus.SENDING_TO_PLUMBER -> {
                status = RepairStatus.SENT_TO_PLUMBER
            }
            RepairStatus.SENT_TO_PLUMBER -> {
                status = RepairStatus.RUNNING
            }
            RepairStatus.RUNNING -> {
                status = RepairStatus.DONE
            }
            else -> {

            }
        }
    }
}

data class RepairOrderPost(
    var sender: UserInfo = UserInfo(),
    var message: String = "message",
    var createDateTime: String = "createDateTime",
    var imageList: MutableList<Bitmap> = mutableListOf()
) {

}