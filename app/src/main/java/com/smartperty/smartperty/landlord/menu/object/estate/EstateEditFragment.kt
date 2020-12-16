package com.smartperty.smartperty.landlord.menu.`object`.estate

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.smartperty.smartperty.R
import com.smartperty.smartperty.repair.ImageListAdapter
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.activity_landlord.*
import kotlinx.android.synthetic.main.fragment_estate_edit.view.*
import java.text.DateFormat
import java.util.*

class EstateEditFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var root: View
    private lateinit var datePickerDialog: DatePickerDialog
    private var imageList: MutableList<Bitmap> = mutableListOf()
    private lateinit var imageListAdapter: ImageListAdapter

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_estate_edit, container, false)

        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()
        GlobalVariables.toolBarUtils.setSubmitButtonVisibility(true)

        GlobalVariables.activity.toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.button_submit -> {
                    GlobalVariables.estate.title = root.textView_object_item_title.text.toString()
                    GlobalVariables.estate.address = root.textView_object_item_address.text.toString()
                    GlobalVariables.estate.floor = root.textView_object_item_floor.text.toString().toInt()
                    GlobalVariables.estate.squareFt = root.textView_object_item_square_ft.text.toString().toInt()
                    GlobalVariables.estate.parkingSpace = root.textView_object_item_parking_sapce.text.toString()
                    GlobalVariables.estate.content = root.textView_object_item_content.text.toString()

                    root.findNavController().navigateUp()

                    true
                }
                else -> false
            }
        }

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, y, m, d ->
                val dateString = "$y/$m/$d"
                root.textView_object_item_rent_end_date.text = dateString
            },
            year, month, day
        )

        root.textView_object_item_rent_end_date.setOnClickListener {
            datePickerDialog.show()
        }

        if(GlobalVariables.estate.address.isNotEmpty())
            root.textView_object_item_address.setText(
                GlobalVariables.estate.address
            )
        if(GlobalVariables.estate.parkingSpace.isNotEmpty())
            root.textView_object_item_parking_sapce.setText(
                GlobalVariables.estate.parkingSpace
            )
        if(GlobalVariables.estate.contract.tenant != null) {
            root.textView_object_item_tenant_name.setText(
                GlobalVariables.estate.contract.tenant!!.name
            )
            root.textView_object_item_phone.setText(
                GlobalVariables.estate.contract.tenant!!.cellPhone
            )
        }
        if(GlobalVariables.estate.contract.rentEndDate.isNotEmpty())
            root.textView_object_item_rent_end_date.setText(
                GlobalVariables.estate.contract.rentEndDate
            )
        if(GlobalVariables.estate.content.isNotEmpty())
            root.textView_object_item_content.setText(
                GlobalVariables.estate.content
            )
        if(GlobalVariables.estate.title.isNotEmpty())
            root.textView_object_item_title.setText(
                GlobalVariables.estate.title
            )

        root.textView_object_item_floor.setText(
            GlobalVariables.estate.floor.toString()
        )
        root.textView_object_item_square_ft.setText(
            GlobalVariables.estate.squareFt.toString()
        )
        root.textView_object_item_rent_amount_per_month.setText(
            GlobalVariables.estate.contract.rentAmount.toString()
        )

        imageList = GlobalVariables.estate.imageList

        root.image_object_item.setOnClickListener {
            pickImageFromGallery()
        }

        imageListAdapter = ImageListAdapter(requireActivity(), root, imageList)
        root.recycler_image.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false )
            adapter = imageListAdapter
        }

        return root
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val c: Calendar = Calendar.getInstance()
        c.set(Calendar.YEAR, year)
        c.set(Calendar.MONTH, month)
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        val currentDateString: String =
            DateFormat.getDateInstance(DateFormat.FULL).format(c.time)

        root.textView_object_item_rent_end_date.text = currentDateString
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
            root.image_object_item.setImageURI(data?.data)
            if (imageList.isEmpty()) {
                imageList.add(root.image_object_item.drawable.toBitmap())
                imageListAdapter = ImageListAdapter(requireActivity(), root, imageList)
                root.recycler_image.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false )
                    adapter = adapter
                }
            }
            else {
                imageList.add(root.image_object_item.drawable.toBitmap())
                imageListAdapter.notifyDataSetChanged()
            }

            root.image_object_item.setImageResource(R.drawable.add_photo)
        }
    }
}