package com.smartperty.smartperty.landlord.notification.data

data class LandlordNotificationList(
    var list: MutableList<LandlordNotification> = mutableListOf()
) {
}