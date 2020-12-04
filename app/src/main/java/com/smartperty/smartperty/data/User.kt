package com.smartperty.smartperty.data

import android.graphics.Bitmap

enum class UserType {
    LANDLORD, TENANT, PLUMBER, UNKNOWN
}

data class User(
    var id: String = "",
    var account: String = "",
    var password: String = "",
    var userInfo: UserInfo = UserInfo()
) {
}

data class UserInfo(
    var name: String = "",
    var homePhone: String = "",
    var cellPhone: String = "",
    var email: String = "",
    var address: String = "",
    var company: String = "",
    var type: UserType = UserType.UNKNOWN,
    var image: Bitmap? = null
) {

}