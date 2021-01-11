package com.smartperty.smartperty.data

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import com.smartperty.smartperty.R
import com.smartperty.smartperty.utils.GlobalVariables

enum class NotificationType{
    CONTRACT, RENT, AGENT, EVENT, SYSTEM, UNKNOWN
}

data class Notification(
    var message: String = "nil",
    var type: NotificationType = NotificationType.UNKNOWN,
    var date: String = "nil"
) {
    @SuppressLint("ResourceType")
    fun getDrawable(): Drawable  {
        return when(type) {
            NotificationType.CONTRACT -> {
                GlobalVariables.activity.resources.getDrawable(R.drawable.fade_red)
            }
            NotificationType.RENT -> {
                GlobalVariables.activity.resources.getDrawable(R.drawable.fade_yellow)
            }
            NotificationType.AGENT -> {
                GlobalVariables.activity.resources.getDrawable(R.drawable.fade_blue)
            }
            NotificationType.EVENT -> {
                GlobalVariables.activity.resources.getDrawable(R.drawable.fade_black)
            }
            NotificationType.SYSTEM -> {
                GlobalVariables.activity.resources.getDrawable(R.drawable.fade_green)
            }
            else -> {
                GlobalVariables.activity.resources.getDrawable(R.drawable.fade_red)
            }
        }
    }
}