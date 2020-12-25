package com.smartperty.smartperty.landlord.menu.contract

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.smartperty.smartperty.R
import com.smartperty.smartperty.data.Contract
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.activity_tenant.*
import kotlinx.android.synthetic.main.fragment_contract_create.view.*

class ContractCreateFragment : Fragment() {

    private lateinit var root: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GlobalVariables.contract = Contract()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_contract_create, container, false)

        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()
        GlobalVariables.toolBarUtils.setSubmitButtonVisibility(true)

        if (GlobalVariables.estate.objectId != "nil") {
            GlobalVariables.contract.estate = GlobalVariables.estate
            GlobalVariables.contract.landlord = GlobalVariables.estate.landlord

            root.button_create_contract_select_property.setText(
                GlobalVariables.contract.estate!!.getAddress())
            root.button_create_contract_select_landlord.setText(
                GlobalVariables.contract.landlord!!.name)

            if (GlobalVariables.estate.tenant != null) {
                GlobalVariables.contract.tenant = GlobalVariables.estate.tenant

                root.button_create_contract_select_tenant.setText(
                    GlobalVariables.contract.tenant!!.name)
            }
        }

        root.edit_create_contract_rent.setText(GlobalVariables.contract.rent.toString())
        root.edit_create_contract_deposit.setText(GlobalVariables.contract.deposit.toString())

        GlobalVariables.activity.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.button_submit -> {
                    // setup dialog builder
                    val builder = android.app.AlertDialog.Builder(requireActivity())
                    builder.setTitle("是否送出合約？")

                    builder.setPositiveButton("是") { _, _ ->
                        root.findNavController().navigateUp()
                    }

                    // create dialog and show it
                    requireActivity().runOnUiThread{
                        val dialog = builder.create()
                        dialog.show()
                    }

                    true
                }
                else -> false
            }
        }

        return root
    }

}