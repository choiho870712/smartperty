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
import com.smartperty.smartperty.utils.GlobalVariables.Companion.toolbar
import kotlinx.android.synthetic.main.tenant_fragment_home_main.view.*

class TenantHomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.tenant_fragment_home_main, container, false)

        toolBarUtils.setVisibility(false)

        root.button_tenant_setup.setOnClickListener {
            root.findNavController().navigate(
                R.id.action_tenantHomeFragment_to_landlord_group)
        }

        root.button_home_housing_rules.setOnClickListener {
            GlobalVariables.estate = GlobalVariables.loginUser.estate
            root.findNavController().navigate(
                R.id.action_tenantHomeFragment_to_tenantHosingRulesFragment)
        }
        root.button_home_rent_and_utilities.setOnClickListener {
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
            val uri = Uri.parse("android-app://com.smartperty.smartperty/repairListFragment")
            root.findNavController().navigate(uri)
        }

        return root
    }
}