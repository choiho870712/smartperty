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
import com.smartperty.smartperty.R
import com.smartperty.smartperty.data.EstateList
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.activity_landlord.*
import kotlinx.android.synthetic.main.fragment_estate_directory_create.view.*
import kotlinx.android.synthetic.main.fragment_personnel_user_info.view.*

class EstateDirectoryCreateFragment : Fragment() {

    private lateinit var root: View
    private var image: Bitmap? = null

    private var isNewFolder = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isNewFolder = GlobalVariables.estateFolder.title.isEmpty()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_estate_directory_create, container, false)

        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()
        GlobalVariables.toolBarUtils.setSubmitButtonVisibility(true)

        if (!isNewFolder) {
            GlobalVariables.toolBarUtils.setTitle("編輯物件群組")
            root.text_object_folder_title.setText(GlobalVariables.estateFolder.title)
        }

        GlobalVariables.activity.toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.button_submit -> {
                    // setup dialog builder
                    val builder = android.app.AlertDialog.Builder(requireActivity())
                    builder.setTitle("確定要送出嗎？")

                    builder.setPositiveButton("是") { _, _ ->

                        if (!isNewFolder) {
                            GlobalVariables.estateFolder.title =
                                root.text_object_folder_title.text.toString()
                            GlobalVariables.estateFolder.image = image
                            Thread {
                                GlobalVariables.api.updatePropertyGroupTagInformation(
                                    group_index = GlobalVariables.estateFolder.groupIndex,
                                    id = GlobalVariables.loginUser.id,
                                    system_id = GlobalVariables.loginUser.system_id,
                                    image = image!!,
                                    name = root.text_object_folder_title.text.toString()
                                )
                            }.start()
                        }
                        else {
                            Thread {
                                GlobalVariables.api.createPropertyGroupTag(
                                    GlobalVariables.loginUser.id,
                                    GlobalVariables.loginUser.system_id,
                                    root.text_object_folder_title.text.toString(),
                                    image
                                )
                            }.start()

                            GlobalVariables.estateDirectory.add(
                                EstateList(
                                    title = root.text_object_folder_title.text.toString(),
                                    image = image
                                )
                            )
                        }

                        GlobalVariables.estateDirectoryAdapter!!.notifyDataSetChanged()
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

        root.button_select_dir_image.setOnClickListener {
            pickImageFromGallery()
        }

        root.image_object_folder_add.setOnClickListener {
            GlobalVariables.imageHelper.openLargeImage(root.image_object_folder_add.drawable.toBitmap())
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
            root.image_object_folder_add.setImageURI(data?.data)
            image = root.image_object_folder_add.drawable.toBitmap()
        }
    }
}