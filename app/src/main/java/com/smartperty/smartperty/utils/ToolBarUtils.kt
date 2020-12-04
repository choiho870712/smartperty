package com.smartperty.smartperty.utils

import android.view.View
import androidx.core.view.forEach
import com.smartperty.smartperty.R
import com.smartperty.smartperty.utils.GlobalVariables.Companion.activity
import com.smartperty.smartperty.utils.GlobalVariables.Companion.toolbar

class ToolBarUtils() {

    fun removeAllButtonAndLogo() {
        activity.runOnUiThread {
            setVisibility(true)
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

    fun setEditButtonVisibility(visible: Boolean) {
        val button = toolbar.menu.findItem(R.id.button_edit)
        activity.runOnUiThread {
            button.isVisible = visible
        }
    }

    fun setAddButtonVisibility(visible: Boolean) {
        val button = toolbar.menu.findItem(R.id.button_add)
        activity.runOnUiThread {
            button.isVisible = visible
        }
    }

    fun setSubmitButtonVisibility(visible: Boolean) {
        val button = toolbar.menu.findItem(R.id.button_submit)
        activity.runOnUiThread {
            button.isVisible = visible
        }
    }
}