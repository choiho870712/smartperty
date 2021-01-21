package com.smartperty.smartperty.data

import android.graphics.Bitmap

data class User(
    var id: String = "nil",
    //var permissions: String = "nil",

    var accountantList: MutableList<User> = mutableListOf(),
    var agentList: MutableList<User> = mutableListOf(),
    var technicianList: MutableList<User> = mutableListOf(),
    var tenantList: MutableList<User> = mutableListOf(),

    var contractList: MutableList<Contract> = mutableListOf(),
    var repairList: MutableList<RepairOrder> = mutableListOf(),
    var estateDirectory: MutableList<EstateList> = mutableListOf(),
    var system_id: String = "nil",
    var name: String = "nil",
    var homePhone: String = "nil",
    var cellPhone: String = "nil",
    var email: String = "nil",
    var address: String = "nil",
    var company: String = "nil",
    var auth: String = "nil",
    var icon: Bitmap? = null,
    var iconString: String = "nil",
    var account: String = "nil",
    var password: String = "nil",
    var sex: String = "nil",
    var annual_income: String = "nil",
    var industry: String = "nil",
    var profession: String = "nil",

    var permission: Permission = Permission()
) {
    fun compareId(userId: String): Boolean {
        return userId == this.id
    }

    fun update(updateInfo:User) {
        id = updateInfo.id
        //permissions = updateInfo.permissions
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
        profession = updateInfo.profession
        permission = updateInfo.permission
    }
}

data class Permission(
    var property: String = "A",
    var contract: String = "A",
    var data: String = "A",
    var payment: String = "A",
    var event: String = "A",
    var staff: String = "A"
)