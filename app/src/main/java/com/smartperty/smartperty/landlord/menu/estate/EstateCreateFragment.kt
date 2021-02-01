package com.smartperty.smartperty.landlord.menu.estate

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.smartperty.smartperty.R
import com.smartperty.smartperty.repair.ImageListAdapter
import com.smartperty.smartperty.tools.TimeUtil
import com.smartperty.smartperty.utils.GlobalVariables
import com.smartperty.smartperty.utils.Utils
import kotlinx.android.synthetic.main.activity_landlord.*
import kotlinx.android.synthetic.main.fragment_estate.view.imageView_add_image_button
import kotlinx.android.synthetic.main.fragment_estate.view.recycler_image
import kotlinx.android.synthetic.main.fragment_estate_create.view.*
import java.util.*
import kotlin.math.E

class EstateCreateFragment : Fragment() {

    private lateinit var root:View
    private var imageList: MutableList<Bitmap> = mutableListOf()
    private lateinit var imageListAdapter: ImageListAdapter
    private var isNewProperty = false
    private lateinit var datePickerDialog_start: DatePickerDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GlobalVariables.imageListUsage = "edit"
        GlobalVariables.imageEditIndexLog.clear()
        for (i in 0 until(GlobalVariables.estate.imageList.size)) {
            GlobalVariables.imageEditIndexLog.add(false)
        }

        if (GlobalVariables.estate.objectId.length < 5) {
            isNewProperty = true
        }
        else {
            isNewProperty = false
            imageList.addAll(GlobalVariables.estate.imageList)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root =  inflater.inflate(R.layout.fragment_estate_create, container, false)

        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()
        if (!isNewProperty)
            GlobalVariables.toolBarUtils.setTitle("編輯物件")

        if (GlobalVariables.loginUser.auth == "landlord") {
            GlobalVariables.toolBarUtils.setSubmitButtonVisibility(true)
            GlobalVariables.activity.toolbar.setOnMenuItemClickListener { it ->
                when(it.itemId) {
                    R.id.button_submit -> {
                        // setup dialog builder
                        val builder = android.app.AlertDialog.Builder(requireActivity())
                        builder.setTitle("確定要送出嗎？")

                        builder.setPositiveButton("是") { _, _ ->

                            GlobalVariables.estate.objectName =
                                root.edit_create_property_name.text.toString()
                            GlobalVariables.estate.region =
                                if (root.button_create_property_select_region.text.toString() != "選擇")
                                    root.button_create_property_select_region.text.toString()
                                else
                                    ""
                            GlobalVariables.estate.road =
                                if (root.button_create_property_select_road.text.toString() != "選擇")
                                    root.button_create_property_select_road.text.toString()
                                else
                                    ""
                            GlobalVariables.estate.street =
                                if (root.button_create_property_select_street.text.toString() != "選擇")
                                    root.button_create_property_select_street.text.toString()
                                else
                                    ""
                            GlobalVariables.estate.fullAddress =
                                root.edit_create_property_full_address.text.toString()
                            GlobalVariables.estate.floor =
                                root.edit_create_property_floor.text.toString()
                            GlobalVariables.estate.area =
                                root.edit_create_property_area.text.toString().toInt()
                            GlobalVariables.estate.parkingSpace =
                                root.edit_create_property_parking_space.text.toString()
                            GlobalVariables.estate.type =
                                when (root.button_create_property_select_type.text.toString()) {
                                    "住宅" -> {
                                        "Dwelling"
                                    }
                                    "套房" -> {
                                        "Suite"
                                    }
                                    "店面" -> {
                                        "Storefront"
                                    }
                                    "辦公" -> {
                                        "Office"
                                    }
                                    "住辦" -> {
                                        "DwellingOffice"
                                    }
                                    "廠房" -> {
                                        "Factory"
                                    }
                                    "車位" -> {
                                        "ParkingSpace"
                                    }
                                    "土地" -> {
                                        "LandPlace"
                                    }
                                    else -> {
                                        "Other"
                                    }
                                }
                            GlobalVariables.estate.description =
                                root.edit_create_property_description.text.toString()
                            GlobalVariables.estate.purchasePrice =
                                root.edit_create_property_purchase_price2.text.toString().toLong()
                            GlobalVariables.estate.rent =
                                root.edit_create_property_rent.text.toString().toInt()

                            GlobalVariables.estate.purchaseDate =
                                TimeUtil.DateToStamp(
                                    root.button_create_property_select_purchase_date.text.toString(),
                                    Locale.TAIWAN
                                )

                            if (isNewProperty) {
                                GlobalVariables.estate.imageList.clear()
                                GlobalVariables.estate.imageList.addAll(imageList)
                                Utils.createEstate(GlobalVariables.estate)
                            }
                            else {
                                Thread {
                                    GlobalVariables.api.updatePropertyInformation(GlobalVariables.estate)
                                }.start()
                                Thread {
                                    var removedCount = 0
                                    val originalSize = GlobalVariables.imageEditIndexLog.size
                                    for (index in originalSize - 1 downTo 0) {
                                        val isRemoved = GlobalVariables.imageEditIndexLog[index]
                                        if (isRemoved) {
                                            val imageUrl = GlobalVariables.estate.imageUrlList[index]
                                            Thread {
                                                GlobalVariables.api.deletePropertyImage(
                                                    GlobalVariables.estate.landlord!!.id,
                                                    GlobalVariables.estate.objectId,
                                                    index,
                                                    imageUrl
                                                )
                                            }.start()
                                            GlobalVariables.estate.imageList.removeAt(index)
                                            GlobalVariables.estate.imageUrlList.removeAt(index)
                                            removedCount++
                                        }
                                    }
                                    for (index in (originalSize-removedCount) until(imageList.size) ) {
//                                        val imageIndex = GlobalVariables.estate.imageList.size
                                        GlobalVariables.estate.imageList.add(imageList[index])
//                                        GlobalVariables.estate.imageUrlList.add("")
                                        Thread {
//                                            val newImageUrl = GlobalVariables.api.uploadPropertyImage(
//                                                GlobalVariables.estate.landlord!!.id,
//                                                GlobalVariables.estate.objectId,
//                                                imageList[index]
//                                            )
//                                            GlobalVariables.estate.imageUrlList[imageIndex] = newImageUrl

                                            GlobalVariables.estate.imageUrlList.addAll(
                                                GlobalVariables.api.uploadPropertyImage(
                                                    GlobalVariables.estate.landlord!!.id,
                                                    GlobalVariables.estate.objectId,
                                                    imageList
                                                )
                                            )
                                        }.start()
                                    }
                                }.start()
                            }

                            GlobalVariables.refreshAllChart()
                            root.findNavController().navigate(
                                R.id.action_estateCreateFragment_to_estateFragment
                            )
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
        }

        imageListAdapter = ImageListAdapter(requireActivity(), root, imageList)
        root.recycler_image.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false )
            adapter = imageListAdapter
        }

        root.imageView_add_image_button.setOnClickListener {
            pickImageFromGallery()
        }

        root.button_create_property_select_type.setOnClickListener {
            val builderSingle: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            val arrayAdapter = ArrayAdapter<String>(
                requireContext(),
                android.R.layout.select_dialog_singlechoice
            )

            arrayAdapter.add("住宅")
            arrayAdapter.add("套房")
            arrayAdapter.add("店面")
            arrayAdapter.add("辦公")
            arrayAdapter.add("住辦")
            arrayAdapter.add("廠房")
            arrayAdapter.add("車位")
            arrayAdapter.add("土地")
            arrayAdapter.add("其他")

            builderSingle.setNegativeButton("cancel") { dialog, which -> dialog.dismiss() }
            builderSingle.setAdapter(arrayAdapter) { _, which ->
                val strName = arrayAdapter.getItem(which)
                root.button_create_property_select_type.text = strName
            }
            builderSingle.show()
        }

        var selectedRegion = GlobalVariables.addressTree.regionList[0].region
        var selectedRoad = GlobalVariables.addressTree.regionList[0].roadList[0].road
        var selectedStreet = GlobalVariables.addressTree.regionList[0].roadList[0].streetList[0]

        if (GlobalVariables.estate.region.isNotEmpty()) {
            selectedRegion = GlobalVariables.estate.region
            selectedRoad = GlobalVariables.estate.road
            selectedStreet = GlobalVariables.estate.street
        }

        root.button_create_property_select_region.text = selectedRegion
        root.button_create_property_select_road.text = selectedRoad
        root.button_create_property_select_street.text = selectedStreet

        root.edit_create_property_name.setText(GlobalVariables.estate.objectName)
        root.edit_create_property_full_address.setText(GlobalVariables.estate.fullAddress)
        root.edit_create_property_floor.setText(GlobalVariables.estate.floor)
        root.edit_create_property_area.setText(GlobalVariables.estate.area.toString())
        root.edit_create_property_parking_space.setText(GlobalVariables.estate.parkingSpace)
        root.edit_create_property_description.setText(GlobalVariables.estate.description)
        root.edit_create_property_purchase_price2.setText(GlobalVariables.estate.purchasePrice.toString())
        root.edit_create_property_rent.setText(GlobalVariables.estate.rent.toString())
        if (GlobalVariables.estate.purchaseDate > 0) {
            root.button_create_property_select_purchase_date.text =
                TimeUtil.StampToDate(GlobalVariables.estate.purchaseDate, Locale.TAIWAN)
        }
        root.button_create_property_select_type.text =
            when (GlobalVariables.estate.type) {
                "Dwelling" -> {
                    "住宅"
                }
                "Suite" -> {
                    "套房"
                }
                "Storefront" -> {
                    "店面"
                }
                "Office" -> {
                    "辦公"
                }
                "DwellingOffice" -> {
                    "住辦"
                }
                "Factory" -> {
                    "廠房"
                }
                "ParkingSpace" -> {
                    "車位"
                }
                "LandPlace" -> {
                    "土地"
                }
                else -> {
                    "點擊選單"
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

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        datePickerDialog_start = DatePickerDialog(
            requireContext(),
            { _, y, m, d ->
                val m2 = m+1
                val dateString = "$y/$m2/$d"
                root.button_create_property_select_purchase_date.text = dateString
            },
            year, month, day
        )

        root.button_create_property_select_purchase_date.setOnClickListener {
            datePickerDialog_start.show()
        }

        return root
    }

    // the code of getting image /////////////////////////////////

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent,
            IMAGE_PICK_CODE
        )
    }

    companion object {
        //image pick code
        private const val IMAGE_PICK_CODE = 1000
        //Permission code
        private const val PERMISSION_CODE = 1001
    }

    //handle requested permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromGallery()
                }
            }
        }
    }

    // handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            root.imageView_add_image_button.setImageURI(data?.data)

            imageList.add(root.imageView_add_image_button.drawable.toBitmap())

            if (imageList.isEmpty()) {
                imageListAdapter = ImageListAdapter(requireActivity(), root, imageList)
                root.recycler_image.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false )
                    adapter = adapter
                }
            }
            else {
                imageListAdapter.notifyDataSetChanged()
            }

            root.imageView_add_image_button.setImageResource(R.drawable.add_photo)
        }
    }
}