package com.smartperty.smartperty.landlord.menu.`object`.estateList

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.smartperty.smartperty.R
import com.smartperty.smartperty.data.Contract
import com.smartperty.smartperty.data.Estate
import com.smartperty.smartperty.data.User
import com.smartperty.smartperty.repair.ImageListAdapter
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.activity_landlord.*
import kotlinx.android.synthetic.main.fragment_estate_list_create.view.*
import java.util.*

class EstateListCreateFragment : Fragment() {

    private lateinit var root: View
    private lateinit var datePickerDialog: DatePickerDialog
    private var imageList: MutableList<Bitmap> = mutableListOf()
    private lateinit var imageListAdapter: ImageListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_estate_list_create, container, false)

        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()
        GlobalVariables.toolBarUtils.setSubmitButtonVisibility(true)

        GlobalVariables.activity.toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.button_submit -> {
                    var floor = 0
                    if (root.textView_object_item_floor.text.isNotEmpty())
                        floor = root.textView_object_item_floor.text.toString().toInt()

                    var squareFt = 0.0
                    if (root.textView_object_item_square_ft.text.isNotEmpty())
                        squareFt = root.textView_object_item_square_ft.text.toString().toDouble()

                    var rentAmount = 0
                    if (root.textView_object_item_rent_amount_per_month.text.isNotEmpty())
                        rentAmount = root.textView_object_item_rent_amount_per_month.text.toString().toInt()


                    val item = Estate(
                        groupName = GlobalVariables.estateList.title,
                        title = root.textView_object_item_title.text.toString(),
                        floor = floor,
                        squareFt = squareFt,
                        parkingSpace = root.textView_object_item_parking_sapce.text.toString(),
                        content = root.textView_object_item_content.text.toString(),
                        district = "", // TODO
                        street = "", // TODO
                        road = "", // TODO
                        address = root.textView_object_item_address.text.toString(),
                        type = "", // TODO
                        contract = Contract(
                            landlord = GlobalVariables.user,
                            rentAmount = rentAmount,
                            rentEndDate = root.textView_object_item_rent_end_date.text.toString(),
                            purchasePrice = 10000
                        )
                    )

                    item.imageList.addAll(imageList)
                    GlobalVariables.estateList.list.add(item)
                    GlobalVariables.estateListAdapter!!.notifyDataSetChanged()

                    Thread{
                        GlobalVariables.api.createProperty(item)
                    }.start()

                    // TODO wait for object_id
//                    item.imageList.forEach {
//                        GlobalVariables.api.uploadPropertyImage(
//                            GlobalVariables.user.id,
//                            GlobalVariables
//                        )
//                    }

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