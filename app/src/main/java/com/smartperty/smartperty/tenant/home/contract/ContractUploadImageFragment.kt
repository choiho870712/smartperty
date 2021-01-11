package com.smartperty.smartperty.tenant.home.contract

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
import com.smartperty.smartperty.landlord.menu.estate.EstateCreateFragment
import com.smartperty.smartperty.repair.ImageListAdapter
import com.smartperty.smartperty.repair.ImageListAdapter2
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.fragment_contract_upload_image.view.*
import kotlinx.android.synthetic.main.fragment_contract_upload_image.view.recycler_image
import kotlinx.android.synthetic.main.fragment_estate.view.*

class ContractUploadImageFragment : Fragment() {

    private lateinit var root:View
    private var imageList: MutableList<Bitmap> = mutableListOf()
    private lateinit var imageListAdapter: ImageListAdapter2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_contract_upload_image, container, false)

        root.button_contract_upload_select_image.setOnClickListener {
            pickImageFromGallery()
        }

        root.button_contract_upload_submit.setOnClickListener {
            GlobalVariables.estate.contract!!.jpgBitmapList.clear()
            GlobalVariables.estate.contract!!.jpgBitmapList.addAll(imageList)
            Thread {
                GlobalVariables.api.uploadContractDocumentJpg(
                    GlobalVariables.estate.contract!!.landlord!!.id,
                    GlobalVariables.estate.contract!!.contractId,
                    "JPG",
                    imageList
                )
            }.start()
            root.findNavController().navigateUp()
        }

        imageListAdapter = ImageListAdapter2(requireActivity(), root, imageList)
        root.recycler_image.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
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

    // handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            root.imageView_contract_upload_select_image.setImageURI(data?.data)
            if (imageList.isEmpty()) {
                imageList.add(root.imageView_contract_upload_select_image.drawable.toBitmap())
                imageListAdapter = ImageListAdapter2(requireActivity(), root, imageList)
                root.recycler_image.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(activity)
                    adapter = adapter
                }
            }
            else {
                imageList.add(root.imageView_contract_upload_select_image.drawable.toBitmap())
                imageListAdapter.notifyDataSetChanged()
            }
        }
    }
}