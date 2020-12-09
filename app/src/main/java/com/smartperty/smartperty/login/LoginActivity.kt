package com.smartperty.smartperty.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.smartperty.smartperty.R
import com.smartperty.smartperty.utils.DBHelper
import com.smartperty.smartperty.utils.GlobalVariables
import com.smartperty.smartperty.utils.ToolBarUtils
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.toolbar

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        GlobalVariables.activity = this
        GlobalVariables.dbHelper = DBHelper(this)
        GlobalVariables.toolbar = toolbar
        GlobalVariables.toolBarUtils = ToolBarUtils()
        GlobalVariables.toolBarUtils.setVisibility(false)

        val navController = login_fragment.findNavController()
        toolbar.setupWithNavController(navController, AppBarConfiguration(navController.graph))

    }
}
