package com.example.smartperty.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.doOnAttach
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager.widget.ViewPager
import com.example.smartperty.R
import com.example.smartperty.tools.CommonViewPagerAdapter
import com.example.smartperty.ui.main.chat.ChatContainerFragment
import com.example.smartperty.ui.main.chat.ChatFragment
import com.example.smartperty.ui.main.home.HomeContainerFragment
import com.example.smartperty.ui.main.home.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.nav_view
import kotlinx.android.synthetic.main.app_bar_main.toolbar
import kotlinx.android.synthetic.main.content_chat.*
import kotlinx.android.synthetic.main.content_home.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private val viewPagerAdapter =  CommonViewPagerAdapter(supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initActionBarDrawer()
        initFragments()

        toolbar.inflateMenu(R.menu.toolbar)
        bottom_navigation.menu.getItem(1).isChecked = true

        nav_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.drawer_menu -> {
                    Toast.makeText(applicationContext,
                        R.string.title_menu, Toast.LENGTH_SHORT).show()
                }
                R.id.drawer_home -> {
                    Toast.makeText(applicationContext,
                        R.string.title_home, Toast.LENGTH_SHORT).show()
                }
                R.id.drawer_chat -> {
                    Toast.makeText(applicationContext,
                        R.string.title_chat, Toast.LENGTH_SHORT).show()
                }
            }
            drawer_layout.closeDrawer(GravityCompat.START)

            true
        }

        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                bottom_navigation.menu.getItem(position+1).isChecked = true
                val navController = viewPagerAdapter.getItem(position).findNavController()
                toolbar.setupWithNavController(navController, AppBarConfiguration(navController.graph))
            }
        })

        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_menu -> {
                    drawer_layout.openDrawer(GravityCompat.START)
                    return@setOnNavigationItemSelectedListener false
                }
                R.id.navigation_home -> {
                    view_pager.currentItem = 0
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_chat -> {
                    view_pager.currentItem = 1
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }

    private fun initActionBarDrawer() {
        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun initFragments() {
        viewPagerAdapter.addFragment(HomeContainerFragment())
        viewPagerAdapter.addFragment(ChatContainerFragment())

        view_pager.offscreenPageLimit = 1
        view_pager.adapter = viewPagerAdapter
    }
}
