package com.smartperty.smartperty.landlord.menu.estate

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.smartperty.smartperty.R
import com.smartperty.smartperty.repair.ImageListAdapter
import com.smartperty.smartperty.repair.RepairListAdapter
import com.smartperty.smartperty.utils.GlobalVariables
import com.smartperty.smartperty.utils.Utils
import kotlinx.android.synthetic.main.activity_landlord.*
import kotlinx.android.synthetic.main.fragment_estate.view.*
import kotlinx.android.synthetic.main.fragment_estate.view.recycler_image
import kotlinx.android.synthetic.main.fragment_estate.view.textView_object_item_address
import kotlinx.android.synthetic.main.fragment_estate.view.textView_object_item_content
import kotlinx.android.synthetic.main.fragment_estate.view.textView_object_item_parking_sapce
import kotlinx.android.synthetic.main.fragment_estate.view.textView_object_item_phone
import kotlinx.android.synthetic.main.fragment_estate.view.textView_object_item_square_ft
import kotlinx.android.synthetic.main.fragment_estate.view.textView_object_item_tenant_name

class EstateFragment : Fragment() {

    private lateinit var root: View
    private var imageList: MutableList<Bitmap> = mutableListOf()
    private lateinit var imageListAdapter: ImageListAdapter

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_estate, container, false)

        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()
        GlobalVariables.toolBarUtils.setTitle(GlobalVariables.estate.objectName)

        fillInformation()

        if (GlobalVariables.loginUser.auth == "landlord") {
            GlobalVariables.toolBarUtils.setEditButtonVisibility(true)
            GlobalVariables.activity.toolbar.setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.button_edit -> {
                        setEditable(true)
                        setButtonEnable(false)
                        true
                    }
                    R.id.button_cancel -> {
                        if (GlobalVariables.estate.objectId.isNotEmpty()) {
                            fillInformation()
                            setEditable(false)
                            setButtonEnable(true)
                        }
                        else {
                            root.findNavController().navigateUp()
                        }
                        true
                    }
                    R.id.button_submit -> {
                        // setup dialog builder
                        val builder = android.app.AlertDialog.Builder(requireActivity())
                        builder.setTitle("確定要送出嗎？")

                        builder.setPositiveButton("是") { _, _ ->
                            storeInformation()
                            fillInformation()
                            setEditable(false)
                            setButtonEnable(true)
                        }
                        builder.setNegativeButton("否") { _, _ ->
                            if (GlobalVariables.estate.objectId.isNotEmpty()) {
                                fillInformation()
                                setEditable(false)
                                setButtonEnable(true)
                            }
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

            if (GlobalVariables.estate.objectId.isEmpty()) {
                setEditable(true)
                setButtonEnable(false)
            }
            else {
                setEditable(false)
                setButtonEnable(true)
            }
        }

        imageListAdapter = ImageListAdapter(requireActivity(), root, imageList)
        root.recycler_image.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false )
            adapter = imageListAdapter
        }

        root.recycler_repair.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, true )
            adapter = RepairListAdapter(
                requireActivity(), root,
                GlobalVariables.estate.repairList
            )
        }

        if (GlobalVariables.estate.tenant != null)
        root.textView_object_item_tenant_name.setOnClickListener {
            GlobalVariables.personnel = GlobalVariables.estate.tenant!!
            root.findNavController().navigate(
                R.id.action_estateFragment_to_landlordPersonnelItemFragment
            )
        }

        root.button_set_attraction.setOnClickListener {
            root.findNavController().navigate(
                R.id.action_estateFragment_to_tenantAttractionsNearbyFragment2
            )
        }
        root.button_set_equipment.setOnClickListener {
            root.findNavController().navigate(
                R.id.action_estateFragment_to_tenantEquipmentManualFragment2
            )
        }
        root.button_set_housing_rules.setOnClickListener {
            root.findNavController().navigate(
                R.id.action_estateFragment_to_tenantHosingRulesFragment2
            )
        }
        root.imageView_add_image_button.setOnClickListener {
            pickImageFromGallery()
        }

        return root
    }

    @SuppressLint("SetTextI18n")
    private fun fillInformation() {
        root.textView_object_item_address.setText(
            GlobalVariables.estate.region +
                    GlobalVariables.estate.road +
                    GlobalVariables.estate.street +
                    GlobalVariables.estate.fullAddress
        )
        root.textView_object_item_floor.setText(
            GlobalVariables.estate.floor.toString())
        root.textView_object_item_square_ft.setText(
            GlobalVariables.estate.area.toString())
        root.textView_object_item_parking_sapce.setText(
            GlobalVariables.estate.parkingSpace)
        root.textView_object_item_tenant_name.setText(
            GlobalVariables.estate.tenant?.name ?: "")
        root.textView_object_item_content.setText(
            GlobalVariables.estate.description)
        if (GlobalVariables.estate.tenant != null)
        root.textView_object_item_phone.setText(
            GlobalVariables.estate.tenant!!.cellPhone)

        imageList.clear()
        imageList.addAll(GlobalVariables.estate.imageList)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setEditable(editable: Boolean) {
        GlobalVariables.toolBarUtils.setEditButtonVisibility(!editable)
        GlobalVariables.toolBarUtils.setCancelButtonVisibility(editable)
        GlobalVariables.toolBarUtils.setSubmitButtonVisibility(editable)

        if (editable) {
            root.imageView_add_image_button.visibility = View.VISIBLE
        }
        else {
            root.imageView_add_image_button.visibility = View.GONE
        }

        if (editable) {
            root.imageView_add_image_button.visibility = View.VISIBLE
        }
        else {
            root.imageView_add_image_button.visibility = View.GONE
        }

        if (editable) {
            if (GlobalVariables.estate.objectId.isEmpty())
                GlobalVariables.toolBarUtils.setTitle("")
            else
                GlobalVariables.toolBarUtils.setTitle("新增物件")
            root.textView_estate_title.setText(
                GlobalVariables.estate.objectName)
            root.textView_estate_title.visibility = View.VISIBLE
        }
        else {
            GlobalVariables.toolBarUtils.setTitle(GlobalVariables.estate.objectName)
            root.textView_estate_title.visibility = View.GONE
        }

        var background =
            if (editable)
                resources.getDrawable(R.drawable.style_hollow_white_smoke)
            else
                resources.getDrawable(R.drawable.style_empty)

        root.textView_object_item_address.background = background
        root.textView_object_item_floor.background = background
        root.textView_object_item_square_ft.background = background
        root.textView_object_item_parking_sapce.background = background
        root.textView_object_item_content.background = background
        root.textView_estate_title.background = background

        if (editable) {
            root.textView_object_item_address.inputType = InputType.TYPE_CLASS_TEXT
            root.textView_object_item_floor.inputType = InputType.TYPE_CLASS_NUMBER
            root.textView_object_item_square_ft.inputType = InputType.TYPE_CLASS_NUMBER
            root.textView_object_item_parking_sapce.inputType = InputType.TYPE_CLASS_TEXT
            root.textView_object_item_content.inputType = InputType.TYPE_CLASS_TEXT
            root.textView_estate_title.inputType = InputType.TYPE_CLASS_TEXT
        }
        else {
            root.textView_object_item_address.inputType = InputType.TYPE_NULL
            root.textView_object_item_floor.inputType = InputType.TYPE_NULL
            root.textView_object_item_square_ft.inputType = InputType.TYPE_NULL
            root.textView_object_item_parking_sapce.inputType = InputType.TYPE_NULL
            root.textView_object_item_content.inputType = InputType.TYPE_NULL
            root.textView_estate_title.inputType = InputType.TYPE_NULL
        }
    }

    private fun storeInformation() {
        GlobalVariables.estate.fullAddress = root.textView_object_item_address.text.toString()
        GlobalVariables.estate.floor = root.textView_object_item_floor.text.toString().toInt()
        GlobalVariables.estate.area = root.textView_object_item_square_ft.text.toString().toDouble()
        GlobalVariables.estate.parkingSpace = root.textView_object_item_parking_sapce.text.toString()
        GlobalVariables.estate.description = root.textView_object_item_content.text.toString()
        GlobalVariables.estate.objectName = root.textView_estate_title.text.toString()

        GlobalVariables.estate.imageList.clear()
        GlobalVariables.estate.imageList.addAll(imageList)

        if (GlobalVariables.estate.objectId.isEmpty()) {
            GlobalVariables.estate.objectId = "nil"
            GlobalVariables.estate.groupName = GlobalVariables.estateFolder.title
            GlobalVariables.estate.landlord = GlobalVariables.loginUser
            GlobalVariables.estateFolder.list.add(GlobalVariables.estate)

            Utils.createEstate(GlobalVariables.estate)
        }
    }

    private fun setButtonEnable(enable: Boolean) {
        root.textView_object_item_tenant_name.isEnabled = enable
        root.button_set_attraction.isEnabled = enable
        root.button_set_equipment.isEnabled = enable
        root.button_set_housing_rules.isEnabled = enable
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
            if (imageList.isEmpty()) {
                imageList.add(root.imageView_add_image_button.drawable.toBitmap())
                imageListAdapter = ImageListAdapter(requireActivity(), root, imageList)
                root.recycler_image.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false )
                    adapter = adapter
                }
            }
            else {
                imageList.add(root.imageView_add_image_button.drawable.toBitmap())
                imageListAdapter.notifyDataSetChanged()
            }

            root.imageView_add_image_button.setImageResource(R.drawable.add_photo)
        }
    }
}