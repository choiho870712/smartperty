package com.smartperty.smartperty.landlord

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jakewharton.threetenabp.AndroidThreeTen
import com.opencsv.CSVReader
import com.smartperty.smartperty.R
import com.smartperty.smartperty.data.Region
import com.smartperty.smartperty.data.Road
import com.smartperty.smartperty.tools.setupWithNavController
import com.smartperty.smartperty.utils.DBHelper
import com.smartperty.smartperty.utils.GlobalVariables
import com.smartperty.smartperty.utils.ToolBarUtils
import kotlinx.android.synthetic.main.activity_tenant.*
import java.io.InputStreamReader


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

        val reader = CSVReader(
            InputStreamReader(
                getAssets().open("road.csv")
            )
        )
        // reader.readNext()會讀取你csv一列的內容並轉換成字串陣列，例如
        // column1, column2, "column
        // 3"
        // 就會被拆成3個字串。這邊就看你想怎麼使用

        reader.readNext()
        reader.readNext()
        var i = 0
        var nextLine = reader.readNext()
        while (nextLine != null) {
            var region =
                GlobalVariables.addressTree.regionList.find { it.region == nextLine[0] }
            if (region == null) {
                region = Region(region = nextLine[0])
                GlobalVariables.addressTree.regionList.add(region)
            }

            var street = region.roadList.find { it.road == nextLine[1].substring(3) }
            if (street == null) {
                street = Road(road = nextLine[1].substring(3))
                region.roadList.add(street)
            }

            street.streetList.add(nextLine[2])

            nextLine = reader.readNext()
            i++
        }
    }
}