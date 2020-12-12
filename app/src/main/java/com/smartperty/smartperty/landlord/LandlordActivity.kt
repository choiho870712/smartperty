package com.smartperty.smartperty.landlord

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
import com.smartperty.smartperty.utils.DBHelper
import kotlinx.android.synthetic.main.activity_tenant.*

/**
 * An activity that inflates a layout that has a [BottomNavigationView].
 */
class LandlordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landlord)

        setupUtils()

        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        } // Else, need to wait for onRestoreInstanceState
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        setupBottomNavigationBar()
    }

    /**
     * Called on first creation and when restoring state.
     */
    private fun setupBottomNavigationBar() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)

        val navGraphIds = listOf(
            R.navigation.landlord_menu,
            R.navigation.landlord_notification,
            R.navigation.landlord_home,
            R.navigation.landlord_chat,
            R.navigation.landlord_group
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

        toolbar.inflateMenu(R.menu.toolbar)
        bottomNavigationView.selectedItemId = R.id.landlord_home
    }

    override fun onSupportNavigateUp(): Boolean {
        return GlobalVariables.currentNavController?.value?.navigateUp() ?: false
    }

    private fun setupUtils() {
        GlobalVariables.activity = this
        GlobalVariables.toolbar = toolbar
        GlobalVariables.toolBarUtils = ToolBarUtils()
        GlobalVariables.dbHelper = DBHelper(this)

        AndroidThreeTen.init(GlobalVariables.activity)
    }
}