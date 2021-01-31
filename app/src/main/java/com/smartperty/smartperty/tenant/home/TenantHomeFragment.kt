package com.smartperty.smartperty.tenant.home

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.smartperty.smartperty.R
import com.smartperty.smartperty.utils.GlobalVariables
import com.smartperty.smartperty.utils.GlobalVariables.Companion.toolBarUtils
import kotlinx.android.synthetic.main.tenant_fragment_home_main.view.*
import kotlinx.android.synthetic.main.tenant_fragment_home_main.view.image_home_weather
import kotlinx.android.synthetic.main.tenant_fragment_home_main.view.text_home_greet_name
import kotlinx.android.synthetic.main.tenant_fragment_home_main.view.text_home_greet_sex
import kotlinx.android.synthetic.main.tenant_fragment_home_main.view.text_home_message
import kotlinx.android.synthetic.main.tenant_fragment_home_main.view.text_home_notify
import kotlinx.android.synthetic.main.tenant_fragment_home_main.view.text_home_temperature

class TenantHomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.tenant_fragment_home_main, container, false)

        toolBarUtils.setVisibility(false)

        root.text_home_greet_name.text = GlobalVariables.loginUser.name
        root.text_home_greet_sex.text =
            when(GlobalVariables.loginUser.sex) {
                "男" -> "君"
                "女" -> "醬"
                else -> ""
            }

        root.text_home_message.text = GlobalVariables.welcomeMessage

        var notifyMessage = ""
        GlobalVariables.notificationList.forEachIndexed { index, notification ->
            if (index < 3) {
                if (index > 0) notifyMessage += "\n"
                notifyMessage += notification.message
            }
        }

        root.text_home_notify.text = notifyMessage

        root.text_home_temperature.text = GlobalVariables.weather.avgT.toString()

        root.image_home_weather.setImageBitmap(
            GlobalVariables.weather.getWeatherBitmap()
        )


        root.button_tenant_setup.setOnClickListener {
            root.findNavController().navigate(
                R.id.action_tenantHomeFragment_to_landlord_group)
        }

        root.button_home_housing_rules.visibility = View.INVISIBLE
        root.button_home_rent_and_utilities.visibility = View.INVISIBLE
        root.button_home_equipment_manual.visibility = View.INVISIBLE
        root.button_home_contract.visibility = View.INVISIBLE
        root.button_home_attractions_nearby.visibility = View.INVISIBLE
        root.button_home_repair.visibility = View.INVISIBLE

        if (GlobalVariables.loginUser.auth == "tenant") {
            root.button_home_housing_rules.visibility = View.VISIBLE
            root.button_home_rent_and_utilities.visibility = View.VISIBLE
            root.button_home_equipment_manual.visibility = View.VISIBLE
            root.button_home_contract.visibility = View.VISIBLE
            root.button_home_attractions_nearby.visibility = View.VISIBLE
            root.button_home_repair.visibility = View.VISIBLE
        }
        else if (GlobalVariables.loginUser.auth == "technician") {
            root.button_home_repair.visibility = View.VISIBLE
        }

        root.button_home_housing_rules.setOnClickListener {
            GlobalVariables.estate = GlobalVariables.loginUser.estate
            root.findNavController().navigate(
                R.id.action_tenantHomeFragment_to_tenantHosingRulesFragment)
        }
        root.button_home_rent_and_utilities.setOnClickListener {
            GlobalVariables.estate = GlobalVariables.loginUser.estate
            root.findNavController().navigate(
                R.id.action_tenantHomeFragment_to_tenantRentAndUtilitiesFragment)
        }
        root.button_home_equipment_manual.setOnClickListener {
            GlobalVariables.estate = GlobalVariables.loginUser.estate
            root.findNavController().navigate(
                R.id.action_tenantHomeFragment_to_tenantEquipmentManualFragment)
        }
        root.button_home_contract.setOnClickListener {
            GlobalVariables.estate = GlobalVariables.loginUser.estate
            root.findNavController().navigate(
                R.id.action_tenantHomeFragment_to_tenantContractFragment)
        }
        root.button_home_attractions_nearby.setOnClickListener {
            GlobalVariables.estate = GlobalVariables.loginUser.estate
            root.findNavController().navigate(
                R.id.action_tenantHomeFragment_to_tenantAttractionsNearbyFragment)
        }
        root.button_home_repair.setOnClickListener {
            GlobalVariables.estate = GlobalVariables.loginUser.estate
            val uri = Uri.parse("android-app://com.smartperty.smartperty/repairListFragment")
            root.findNavController().navigate(uri)
        }

        return root
    }
}