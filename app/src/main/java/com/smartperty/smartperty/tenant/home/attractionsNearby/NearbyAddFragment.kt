package com.smartperty.smartperty.tenant.home.attractionsNearby

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.findNavController
import com.smartperty.smartperty.R
import com.smartperty.smartperty.tenant.home.attractionsNearby.data.AttractionNearbyItem
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.activity_landlord.*
import kotlinx.android.synthetic.main.fragment_nearby_add.view.*

class NearbyAddFragment : Fragment() {

    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_nearby_add, container, false)

        var selectedRegion = GlobalVariables.addressTree.regionList[0].region
        var selectedRoad = GlobalVariables.addressTree.regionList[0].roadList[0].road
        var selectedStreet = GlobalVariables.addressTree.regionList[0].roadList[0].streetList[0]

        root.button_create_property_select_region.text = selectedRegion
        root.button_create_property_select_road.text = selectedRoad
        root.button_create_property_select_street.text = selectedStreet

        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()

        GlobalVariables.toolBarUtils.setSubmitButtonVisibility(true)
        GlobalVariables.activity.toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.button_submit -> {
                    // setup dialog builder
                    val builder = android.app.AlertDialog.Builder(requireActivity())
                    builder.setTitle("確定要送出嗎？")

                    builder.setPositiveButton("是") { _, _ ->

                        val nearbyItem = AttractionNearbyItem(
                            name = root.edit_create_property_name.text.toString(),
                            region = selectedRegion,
                            road = selectedRoad,
                            street = selectedStreet,
                            address = root.edit_create_property_full_address.text.toString(),
                            description = root.edit_create_property_description.text.toString()
                        )

                        GlobalVariables.attractionList.add(nearbyItem)

                        Thread {
                            GlobalVariables.api.uploadAttractionInformation(
                                GlobalVariables.estate.landlord!!.id,
                                GlobalVariables.estate.objectId,
                                nearbyItem
                            )
                        }.start()

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

        root.button_create_property_select_region.setOnClickListener {
            val builderSingle: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            val arrayAdapter = ArrayAdapter<String>(
                requireContext(),
                android.R.layout.select_dialog_singlechoice
            )

            GlobalVariables.addressTree.regionList.forEach {
                arrayAdapter.add(it.region)
            }

            builderSingle.setNegativeButton("cancel") { dialog, which -> dialog.dismiss() }
            builderSingle.setAdapter(arrayAdapter) { _, which ->
                val strName = arrayAdapter.getItem(which)

                selectedRegion = strName!!
                selectedRoad = GlobalVariables.addressTree
                    .regionList.find { it.region == selectedRegion }!!
                    .roadList[0].road
                selectedStreet = GlobalVariables.addressTree
                    .regionList.find { it.region == selectedRegion }!!
                    .roadList.find { it.road == selectedRoad }!!
                    .streetList[0]

                root.button_create_property_select_region.text = selectedRegion
                root.button_create_property_select_road.text = selectedRoad
                root.button_create_property_select_street.text = selectedStreet
            }
            builderSingle.show()
        }

        root.button_create_property_select_road.setOnClickListener {
            val builderSingle: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            val arrayAdapter = ArrayAdapter<String>(
                requireContext(),
                android.R.layout.select_dialog_singlechoice
            )

            GlobalVariables.addressTree.regionList.find { it.region == selectedRegion }!!
                .roadList.forEach {
                    arrayAdapter.add(it.road)
                }

            builderSingle.setNegativeButton("cancel") { dialog, which -> dialog.dismiss() }
            builderSingle.setAdapter(arrayAdapter) { _, which ->
                val strName = arrayAdapter.getItem(which)
                selectedRoad = strName!!
                selectedStreet = GlobalVariables.addressTree
                    .regionList.find { it.region == selectedRegion }!!
                    .roadList.find { it.road == selectedRoad }!!
                    .streetList[0]

                root.button_create_property_select_road.text = selectedRoad
                root.button_create_property_select_street.text = selectedStreet
            }
            builderSingle.show()
        }

        root.button_create_property_select_street.setOnClickListener {
            val builderSingle: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            val arrayAdapter = ArrayAdapter<String>(
                requireContext(),
                android.R.layout.select_dialog_singlechoice
            )

            GlobalVariables.addressTree.regionList.find { it.region == selectedRegion }!!
                .roadList.find { it.road == selectedRoad }!!
                .streetList.forEach {
                    arrayAdapter.add(it)
                }

            builderSingle.setNegativeButton("cancel") { dialog, which -> dialog.dismiss() }
            builderSingle.setAdapter(arrayAdapter) { _, which ->
                val strName = arrayAdapter.getItem(which)
                selectedStreet = strName!!
                root.button_create_property_select_street.text = strName
            }
            builderSingle.show()
        }

        return root
    }

}