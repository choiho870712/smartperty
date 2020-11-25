package com.smartperty.smartperty.utils

import android.app.Activity
import android.app.Application
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LiveData
import androidx.navigation.NavController

class GlobalVariables : Application() {
    companion object {
        lateinit var activity: Activity
        lateinit var toolbar: Toolbar
        lateinit var toolBarUtils: ToolBarUtils
        var currentNavController: LiveData<NavController>? = null
    }
}