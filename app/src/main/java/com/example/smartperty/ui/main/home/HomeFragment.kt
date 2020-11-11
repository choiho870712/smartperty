package com.example.smartperty.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.smartperty.R
import kotlinx.android.synthetic.main.fragment_home_main.view.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home_main, container, false)

        root.button_home_housing_rules.setOnClickListener {
            root.findNavController().navigate(
                R.id.action_homeFragment_to_hosingRulesFragment)
        }
        root.button_home_rent_and_utilities.setOnClickListener {
            root.findNavController().navigate(
                R.id.action_homeFragment_to_rentAndUtilitiesFragment)
        }
        root.button_home_equipment_manual.setOnClickListener {
            root.findNavController().navigate(
                R.id.action_homeFragment_to_equipmentManualFragment)
        }
        root.button_home_contract.setOnClickListener {
            root.findNavController().navigate(
                R.id.action_homeFragment_to_contractFragment)
        }
        root.button_home_attractions_nearby.setOnClickListener {
            root.findNavController().navigate(
                R.id.action_homeFragment_to_attractionsNearbyFragment)
        }
        root.button_home_repair.setOnClickListener {
            root.findNavController().navigate(
                R.id.action_homeFragment_to_repairFragment)
        }

        return root
    }
}