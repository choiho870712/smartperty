package com.smartperty.smartperty.repair


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.smartperty.smartperty.R
import com.smartperty.smartperty.data.*
import com.smartperty.smartperty.tools.TimeUtil
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.activity_tenant.*
import kotlinx.android.synthetic.main.fragment_repair_order_create.view.*
import java.util.*

class RepairOrderCreateFragment : Fragment() {

    private lateinit var root: View
    private lateinit var imageListAdapter: ImageListAdapter
    private var imageList = mutableListOf<Bitmap>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initRepairOrder()
    }

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_repair_order_create, container, false)

        setToolBar()
        createSpinner()
        writeInfoToView()

        setPickImageButton()
        createImageList()
        setChooseTenantButton()

        return root
    }

    private fun setToolBar() {
        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()
        GlobalVariables.toolBarUtils.setSubmitButtonVisibility(true)

        GlobalVariables.activity.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.button_submit -> {
                    // setup dialog builder
                    val builder = android.app.AlertDialog.Builder(requireActivity())
                    builder.setTitle("是否送出報修單？")

                    builder.setPositiveButton("是") { _, _ ->
                        submitOrder()
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
    }

    class SpinnerActivity : Activity(), AdapterView.OnItemSelectedListener {

        override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)

            GlobalVariables.repairOrder.type = RepairType.getByValue(pos)!!
        }

        override fun onNothingSelected(parent: AdapterView<*>) {
            // Another interface callback
        }
    }

    private fun createSpinner() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.repair_type_list,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            root.spinner.adapter = adapter
        }

        root.spinner.onItemSelectedListener = SpinnerActivity()
    }

    private fun writeInfoToView() {
        root.textView_repair_order_writer.text = GlobalVariables.user.name
        root.textView_repair_order_phone.text = GlobalVariables.user.homePhone
        root.textView_repair_order_cell_phone.text = GlobalVariables.user.cellPhone
        root.textView_repair_order_email.text = GlobalVariables.user.email
        root.textView_repair_order_address.text = GlobalVariables.estate.address
    }

    private fun initRepairOrder() {
        GlobalVariables.repairOrder = RepairOrder(
            creator = GlobalVariables.user,
            type = RepairType.EQUIPMENT
        )
    }

    private fun submitOrder() {
        GlobalVariables.repairOrder.initiate_id = GlobalVariables.user.id
        GlobalVariables.repairOrder.timestamp = TimeUtil.getCurrentUnixTimeStamp()
        GlobalVariables.repairOrder.typeString = "maintain"
        GlobalVariables.repairOrder.statusString = "nil"

        if (GlobalVariables.repairOrder.estate != null) {
            if (GlobalVariables.repairOrder.estate!!.contract.tenant != null) {
                GlobalVariables.repairOrder.tenant = GlobalVariables.repairOrder.estate!!.contract.tenant!!
                GlobalVariables.repairOrder.tenant_id = GlobalVariables.repairOrder.estate!!.contract.tenant!!.id
            }
            GlobalVariables.repairOrder.object_id = GlobalVariables.repairOrder.estate!!.objectId
        }
        if (GlobalVariables.repairOrder.plumber.id.isNotEmpty()) {
            GlobalVariables.repairOrder.plumber_id = GlobalVariables.repairOrder.plumber.id
        }

        GlobalVariables.repairOrder.title = root.textView_repair_order_title.text.toString()
        GlobalVariables.repairOrder.repairDateTime = TimeUtil.StampToDate(
            GlobalVariables.repairOrder.timestamp, Locale.TAIWAN
        )

        GlobalVariables.repairOrder.postList.add(
            RepairOrderPost(
                sender = GlobalVariables.user,
                message = root.textView_repair_order_content.text.toString(),
                createDateTime = TimeUtil.getCurrentDateTime(),
                imageList = imageList
            )
        )

        GlobalVariables.repairList.add(GlobalVariables.repairOrder)
        GlobalVariables.repairListAdapter!!.notifyDataSetChanged()

        Thread {
            GlobalVariables.repairOrder.event_id =
                GlobalVariables.api.createEvent(GlobalVariables.repairOrder)

            GlobalVariables.api.updateEventInformation(
                GlobalVariables.repairOrder.event_id,
                GlobalVariables.repairOrder.title,
                GlobalVariables.repairOrder.postList[0]
            )
        }.start()
    }

    private fun setPickImageButton() {
        root.imageButton_repair_order_add_image.setOnClickListener {
            pickImageFromGallery()
        }
    }

    private fun createImageList() {
        imageListAdapter = ImageListAdapter(
            requireActivity(), root, imageList)
        root.recyclerView_image.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false )
            adapter = imageListAdapter
        }
    }

    private fun setChooseTenantButton() {
        root.button_choose_tenant.visibility = View.VISIBLE
        root.button_choose_tenant.setOnClickListener {
            root.findNavController().navigate(
                R.id.action_repairOrderCreateFragment_to_chooseTenantFragment)
        }
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

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            val imageContainer = root.imageButton_repair_order_add_image
            val imageList = imageList

            imageContainer.setImageURI(data?.data)
            if (imageList.isEmpty()) {
                imageList.add(imageContainer.drawable.toBitmap())
                createImageList()
            }
            else {
                imageList.add(imageContainer.drawable.toBitmap())
                imageListAdapter.notifyDataSetChanged()
            }

            imageContainer.setImageResource(R.drawable.add_photo)
        }
    }
}
