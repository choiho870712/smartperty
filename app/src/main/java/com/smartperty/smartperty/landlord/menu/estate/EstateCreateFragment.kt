package com.smartperty.smartperty.landlord.menu.estate

import android.app.Activity
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
import com.smartperty.smartperty.data.Estate
import com.smartperty.smartperty.repair.ImageListAdapter
import com.smartperty.smartperty.utils.GlobalVariables
import com.smartperty.smartperty.utils.Utils
import kotlinx.android.synthetic.main.activity_landlord.*
import kotlinx.android.synthetic.main.fragment_estate.view.*
import kotlinx.android.synthetic.main.fragment_estate.view.imageView_add_image_button
import kotlinx.android.synthetic.main.fragment_estate.view.recycler_image
import kotlinx.android.synthetic.main.fragment_estate_create.view.*

class EstateCreateFragment : Fragment() {

    private lateinit var root:View
    private var imageList: MutableList<Bitmap> = mutableListOf()
    private lateinit var imageListAdapter: ImageListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GlobalVariables.estate = Estate()
        GlobalVariables.estate.landlord = GlobalVariables.loginUser
        GlobalVariables.estate.groupName = GlobalVariables.estateFolder.title
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root =  inflater.inflate(R.layout.fragment_estate_create, container, false)

        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()

        if (GlobalVariables.loginUser.auth == "landlord") {
            GlobalVariables.toolBarUtils.setSubmitButtonVisibility(true)
            GlobalVariables.activity.toolbar.setOnMenuItemClickListener {
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
                                root.edit_create_property_floor.text.toString().toInt()
                            GlobalVariables.estate.area =
                                root.edit_create_property_area.text.toString().toDouble()
                            GlobalVariables.estate.parkingSpace =
                                root.edit_create_property_parking_space.text.toString()
                            GlobalVariables.estate.type =
                                if (root.button_create_property_select_type.text.toString() != "選擇")
                                    root.button_create_property_select_type.text.toString()
                                else
                                    ""
                            GlobalVariables.estate.description =
                                root.edit_create_property_description.text.toString()
                            GlobalVariables.estate.purchasePrice =
                                root.edit_create_property_purchase_price.text.toString().toLong()

                            GlobalVariables.estate.imageList.clear()
                            GlobalVariables.estate.imageList.addAll(imageList)

                            Utils.createEstate(GlobalVariables.estate)

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