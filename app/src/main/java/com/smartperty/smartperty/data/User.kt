package com.smartperty.smartperty.data

import android.graphics.Bitmap

data class User(
    var id: String = "",
    var permissions: String = "",

    var accountantList: MutableList<User> = mutableListOf(),
    var agentList: MutableList<User> = mutableListOf(),
    var technicianList: MutableList<User> = mutableListOf(),
    var tenantList: MutableList<User> = mutableListOf(),

    var contractList: MutableList<Contract> = mutableListOf(),
    var repairList: MutableList<RepairOrder> = mutableListOf(),
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
        id = updateInfo.id
        permissions = updateInfo.permissions
        accountantList = updateInfo.accountantList
        agentList = updateInfo.agentList
        contractList = updateInfo.contractList
        repairList = updateInfo.repairList
        technicianList = updateInfo.technicianList
        tenantList = updateInfo.tenantList
        estateDirectory = updateInfo.estateDirectory
        system_id = updateInfo.system_id
        name = updateInfo.name
        homePhone = updateInfo.homePhone
        cellPhone = updateInfo.cellPhone
        email = updateInfo.email
        address = updateInfo.address
        company = updateInfo.company
        auth = updateInfo.auth
        icon = updateInfo.icon
        iconString = updateInfo.iconString
        account = updateInfo.account
        password = updateInfo.password
        sex = updateInfo.sex
        annual_income = updateInfo.annual_income
        industry = updateInfo.industry
    }
}
