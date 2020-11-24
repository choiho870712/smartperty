package com.example.smartperty.landlord.menu


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController

import com.example.smartperty.R
import com.example.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.landlord_fragment_menu_main.view.*

/**
 * A simple [Fragment] subclass.
 */
class LandlordMenuFragment : Fragment() {

    private lateinit var root:View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.landlord_fragment_menu_main, container, false)

        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()

        root.card_object_folder.setOnClickListener {
            root.findNavController().navigate(
                R.id.action_landlordMenuFragment_to_landlordObjectFolderFragment)
        }

        root.card_contract.setOnClickListener {
            root.findNavController().navigate(
                R.id.action_landlordMenuFragment_to_landlordContractFragment)
        }

        root.card_repair.setOnClickListener {
            root.findNavController().navigate(
                R.id.action_landlordMenuFragment_to_landlordRepairFragment)
        }

        root.card_personnel.setOnClickListener {
            root.findNavController().navigate(
                R.id.action_landlordMenuFragment_to_landlordPersonnelListFragment)
        }

        root.card_data_analysis.setOnClickListener {
            root.findNavController().navigate(
                R.id.action_landlordMenuFragment_to_landlordDataAnalysisFragment)
        }


        return root
    }


}
