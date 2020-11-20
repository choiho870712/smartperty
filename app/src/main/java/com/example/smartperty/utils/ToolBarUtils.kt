package com.example.smartperty.utils

import android.app.Activity
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.view.forEach
import com.example.smartperty.R
import com.example.smartperty.utils.GlobalVariables.Companion.activity
import com.example.smartperty.utils.GlobalVariables.Companion.toolbar

class ToolBarUtils() {

    fun removeAllButtonAndLogo() {
        activity.runOnUiThread {
            setLogoVisibility(false)
            toolbar.menu.forEach {
                it.isVisible = false
            }
        }
    }

    fun setVisibility(visible:Boolean) {
        activity.runOnUiThread {
            toolbar.visibility = if (visible) View.VISIBLE else View.GONE
        }
    }

    fun setTitle(title: String) {
        activity.runOnUiThread {
            toolbar.title = title
        }
    }

    fun setLogoVisibility(visible:Boolean) {
        val logoId = if (visible) R.drawable.ic_logo else R.color.colorPrimary
        activity.runOnUiThread {
            toolbar.setLogo(logoId)
        }
    }

    fun setSearchButtonVisibility(visible:Boolean) {
        val button = toolbar.menu.findItem(R.id.action_search)
        activity.runOnUiThread {
            button.isVisible = visible
        }
    }

    fun setSetupButtonVisibility(visible:Boolean) {
        val button = toolbar.menu.findItem(R.id.button_setup)
        activity.runOnUiThread {
            button.isVisible = visible
        }
    }

    fun setBackgroundColorVisibility(visible:Boolean) {
        val colorId = if (visible) R.color.colorPrimary else R.color.colorWhiteSmoke
        activity.runOnUiThread {
            toolbar.setBackgroundColor(activity.resources.getColor(colorId))
        }
    }
}