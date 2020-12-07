package com.smartperty.smartperty.data

import android.graphics.Bitmap

enum class UserType {
    LANDLORD, TENANT, TECHNICIAN, ACCOUNTANT, AGENT, UNKNOWN;

    companion object {
        fun getByString(str: String): UserType {
            return when (str) {
                "landlord" -> {
                    UserType.LANDLORD
                }
                "technician" -> {
                    UserType.TECHNICIAN
                }
                "accountant" -> {
                    UserType.ACCOUNTANT
                }
                "agent" -> {
                    UserType.AGENT
                }
                "tenant" -> {
                    UserType.TENANT
                }
                else -> {
                    UserType.UNKNOWN
                }
            }
        }
    }
}

data class User(
    var id: String = "",
    var account: String = "",
    var password: String = "",
    var permissions: String = "",
    var userInfo: UserInfo = UserInfo(),
    var accountantIdList: MutableList<String> = mutableListOf(),
    var agentIdList: MutableList<String> = mutableListOf(),
    var contractIdList: MutableList<String> = mutableListOf(),
    var eventIdList: MutableList<String> = mutableListOf(),
    var technicianIdList: MutableList<String> = mutableListOf(),
    var tenantIdList: MutableList<String> = mutableListOf(),
    var estateDirectory: MutableList<EstateList> = mutableListOf(),
    var system_id: String = ""
) {
}

data class UserInfo(
    var name: String = "",
    var homePhone: String = "",
    var cellPhone: String = "",
    var email: String = "",
    var address: String = "",
    var company: String = "",
    var auth: UserType = UserType.UNKNOWN,
    var icon: Bitmap? = null,
    var iconString: String = ""
) {

}