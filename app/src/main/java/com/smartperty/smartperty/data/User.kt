package com.smartperty.smartperty.data

import android.graphics.Bitmap

data class User(
    var id: String = "",
    var permissions: String = "",
    var accountantIdList: MutableList<String> = mutableListOf(),
    var agentIdList: MutableList<String> = mutableListOf(),
    var contractIdList: MutableList<String> = mutableListOf(),
    var eventIdList: MutableList<String> = mutableListOf(),
    var technicianIdList: MutableList<String> = mutableListOf(),
    var tenantIdList: MutableList<String> = mutableListOf(),
    var estateDirectory: MutableList<EstateList> = mutableListOf(),
    var system_id: String = "",
    var name: String = "",
    var homePhone: String = "",
    var cellPhone: String = "",
    var email: String = "",
    var address: String = "",
    var company: String = "",
    var auth: String = "",
    var icon: Bitmap? = null,
    var iconString: String = "",
    var account: String = "",
    var password: String = "",
    var sex: String = "",
    var annual_income: String = "",
    var industry: String = ""
) {
    fun compareId(userId: String): Boolean {
        return userId == this.id
    }

    fun update(updateInfo:User) {
//        id = updateInfo.id
//        permissions = updateInfo.permissions
//        var accountantIdList: MutableList<String> = mutableListOf(),
//        var agentIdList: MutableList<String> = mutableListOf(),
//        var contractIdList: MutableList<String> = mutableListOf(),
//        var eventIdList: MutableList<String> = mutableListOf(),
//        var technicianIdList: MutableList<String> = mutableListOf(),
//        var tenantIdList: MutableList<String> = mutableListOf(),
//        var estateDirectory: MutableList<EstateList> = mutableListOf(),
//        var system_id: String = "",
//        var name: String = "",
//        var homePhone: String = "",
//        var cellPhone: String = "",
//        var email: String = "",
//        var address: String = "",
//        var company: String = "",
//        var auth: String = "",
//        var icon: Bitmap? = null,
//        var iconString: String = "",
//        var account: String = "",
//        var password: String = "",
//        var sex: String = "",
//        var annual_income: String = "",
//        var industry: String = ""
    }
}
