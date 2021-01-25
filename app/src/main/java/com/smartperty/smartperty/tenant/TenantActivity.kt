/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.smartperty.smartperty.tenant

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.smartperty.smartperty.R
import com.smartperty.smartperty.tools.setupWithNavController
import com.smartperty.smartperty.utils.GlobalVariables
import com.smartperty.smartperty.utils.ToolBarUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jakewharton.threetenabp.AndroidThreeTen
import com.smartperty.smartperty.data.*
import com.smartperty.smartperty.utils.DBHelper
import kotlinx.android.synthetic.main.activity_tenant.*

/**
 * An activity that inflates a layout that has a [BottomNavigationView].
 */
class TenantActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tenant)

        setupUtils()

        if (savedInstanceState == null) {
            setupBottomNavigationBar()
            setupToolBar()
        } // Else, need to wait for onRestoreInstanceState
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        setupBottomNavigationBar()
        setupToolBar()
    }

    /**
     * Called on first creation and when restoring state.
     */
    private fun setupBottomNavigationBar() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)

        val navGraphIds = listOf(
            R.navigation.landlord_notification,
            R.navigation.tenant_home,
            R.navigation.landlord_chat
        )

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )

        // Whenever the selected controller changes, setup the action bar.
        controller.observe(this, Observer { navController ->
            toolbar.setupWithNavController(navController, AppBarConfiguration(navController.graph))
        })
        GlobalVariables.currentNavController = controller

        bottomNavigationView.selectedItemId = R.id.tenant_home
    }

    override fun onSupportNavigateUp(): Boolean {
        return GlobalVariables.currentNavController?.value?.navigateUp() ?: false
    }

    private fun setupToolBar() {
        toolbar.inflateMenu(R.menu.toolbar)
    }

    private fun setupUtils() {
        GlobalVariables.activity = this
        GlobalVariables.toolbar = toolbar
        GlobalVariables.toolBarUtils = ToolBarUtils()
        GlobalVariables.dbHelper = DBHelper(this)

        AndroidThreeTen.init(GlobalVariables.activity)
    }
}
