package com.smartperty.smartperty.landlord.menu


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController

import com.smartperty.smartperty.R
import com.smartperty.smartperty.utils.GlobalVariables
import com.smartperty.smartperty.utils.Utils
import kotlinx.android.synthetic.main.fragment_personnel_add.view.*
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
            if (GlobalVariables.loginUser.permission.property == "N") {
                // setup dialog builder
                val builder = android.app.AlertDialog.Builder(requireActivity())
                builder.setTitle("您沒有權限查看")

                builder.setPositiveButton("是") { _, _ ->

                }

                // create dialog and show it
                requireActivity().runOnUiThread{
                    val dialog = builder.create()
                    dialog.show()
                }
            }
            else {
                root.findNavController().navigate(
                    R.id.action_landlordMenuFragment_to_landlordObjectFolderFragment)
            }
        }

        root.card_contract.setOnClickListener {
            if (GlobalVariables.loginUser.permission.contract == "N") {
                // setup dialog builder
                val builder = android.app.AlertDialog.Builder(requireActivity())
                builder.setTitle("您沒有權限查看")

                builder.setPositiveButton("是") { _, _ ->

                }

                // create dialog and show it
                requireActivity().runOnUiThread{
                    val dialog = builder.create()
                    dialog.show()
                }
            }
            else {
                root.findNavController().navigate(
                    R.id.action_landlordMenuFragment_to_landlordContractFragment)
            }
        }

        root.card_repair.setOnClickListener {
            if (GlobalVariables.loginUser.permission.event == "N") {
                // setup dialog builder
                val builder = android.app.AlertDialog.Builder(requireActivity())
                builder.setTitle("您沒有權限查看")

                builder.setPositiveButton("是") { _, _ ->

                }

                // create dialog and show it
                requireActivity().runOnUiThread{
                    val dialog = builder.create()
                    dialog.show()
                }
            }
            else {
                root.findNavController().navigate(
                    R.id.action_landlordMenuFragment_to_repairFragment)
            }
        }

        root.card_personnel.setOnClickListener {
            if (GlobalVariables.loginUser.permission.staff == "N") {
                // setup dialog builder
                val builder = android.app.AlertDialog.Builder(requireActivity())
                builder.setTitle("您沒有權限查看")

                builder.setPositiveButton("是") { _, _ ->

                }

                // create dialog and show it
                requireActivity().runOnUiThread{
                    val dialog = builder.create()
                    dialog.show()
                }
            }
            else {
                root.findNavController().navigate(
                    R.id.action_landlordMenuFragment_to_landlordPersonnelListFragment)
            }
        }

        root.card_data_analysis.setOnClickListener {
            if (GlobalVariables.loginUser.permission.data == "N") {
                // setup dialog builder
                val builder = android.app.AlertDialog.Builder(requireActivity())
                builder.setTitle("您沒有權限查看")

                builder.setPositiveButton("是") { _, _ ->

                }

                // create dialog and show it
                requireActivity().runOnUiThread{
                    val dialog = builder.create()
                    dialog.show()
                }
            }
            else {
                root.findNavController().navigate(
                    R.id.action_landlordMenuFragment_to_landlordDataAnalysisFragment)
            }
        }

        root.card_finance.setOnClickListener {
            if (GlobalVariables.loginUser.permission.payment == "N") {
                // setup dialog builder
                val builder = android.app.AlertDialog.Builder(requireActivity())
                builder.setTitle("您沒有權限查看")

                builder.setPositiveButton("是") { _, _ ->

                }

                // create dialog and show it
                requireActivity().runOnUiThread{
                    val dialog = builder.create()
                    dialog.show()
                }
            }
            else {
                root.findNavController().navigate(
                    R.id.action_landlordMenuFragment_to_financeFragment)
            }
        }


        return root
    }


}
