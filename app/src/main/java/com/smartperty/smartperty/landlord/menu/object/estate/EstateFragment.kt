package com.smartperty.smartperty.landlord.menu.`object`.estate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.smartperty.smartperty.R
import com.smartperty.smartperty.repair.ImageListAdapter
import com.smartperty.smartperty.repair.RepairListAdapter
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.activity_landlord.*
import kotlinx.android.synthetic.main.fragment_estate.view.*

class EstateFragment : Fragment() {

    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_estate, container, false)

        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()
        GlobalVariables.toolBarUtils.setEditButtonVisibility(true)
        GlobalVariables.toolBarUtils.setTitle(GlobalVariables.estate.title)

        GlobalVariables.activity.toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.button_edit -> {
                    root.findNavController().navigate(
                        R.id.action_landlordObjectFragment_to_landlordObjectItemEditFragment)

                    true
                }
                else -> false
            }
        }

        root.textView_object_item_address.text = GlobalVariables.estate.address
        root.textView_object_item_floor.text = GlobalVariables.estate.floor.toString()
        root.textView_object_item_square_ft.text = GlobalVariables.estate.squareFt.toString()
        root.textView_object_item_parking_sapce.text = GlobalVariables.estate.parkingSpace
        root.textView_object_item_tenant_name.text = GlobalVariables.estate.contract.tenant?.name ?: ""
        root.textView_object_item_phone.text = GlobalVariables.estate.contract.tenant?.cellPhone ?: ""
        root.textView_object_item_rent_amount_per_month.text = GlobalVariables.estate.contract.rentAmount.toString()
        root.textView_object_item_rent_end_date.text = GlobalVariables.estate.contract.rentEndDate

        root.textView_object_item_content.text = GlobalVariables.estate.content

        root.recycler_image.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false )
            adapter = ImageListAdapter(
                requireActivity(), root, GlobalVariables.estate.imageList)
        }

        root.recycler_repair.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = RepairListAdapter(
                requireActivity(), root,
                GlobalVariables.estate.repairList
            )
        }


        return root
    }
}