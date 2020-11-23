package com.example.smartperty.landlord.notification.data

data class LandlordNotificationList(
    var list: MutableList<LandlordNotification> = mutableListOf()
) {
}