package com.smartperty.smartperty.landlord.menu.personnel

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
import com.smartperty.smartperty.R
import com.smartperty.smartperty.utils.GlobalVariables
import com.smartperty.smartperty.utils.Utils
import kotlinx.android.synthetic.main.activity_tenant.*
import kotlinx.android.synthetic.main.fragment_personnel_add.view.spinner
import kotlinx.android.synthetic.main.fragment_personnel_user_info.view.*

class PersonnelUserInfoFragment : Fragment() {

    private lateinit var root: View
    private var imageBuffer: Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_personnel_user_info, container, false)

        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()
        GlobalVariables.toolBarUtils.setTitle(GlobalVariables.personnel.name)

        if (GlobalVariables.personnelUserInfoUsage != "read") {
            GlobalVariables.toolBarUtils.setSubmitButtonVisibility(true)

            GlobalVariables.activity.toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.button_submit -> {
                        // setup dialog builder
                        val builder = android.app.AlertDialog.Builder(requireActivity())
                        builder.setTitle("是否送出個人資料？")

                        builder.setPositiveButton("是") { _, _ ->

                            if (imageBuffer != null)
                                GlobalVariables.personnel.icon = imageBuffer
                            GlobalVariables.personnel.name = root.textView_name.text.toString()
                            GlobalVariables.personnel.sex = root.textView_gender.text.toString()
                            GlobalVariables.personnel.email = root.textView_email.text.toString()
                            GlobalVariables.personnel.cellPhone = root.textView_phone.text.toString()
                            GlobalVariables.personnel.annual_income = root.textView_income.text.toString()
                            GlobalVariables.personnel.industry = "nil"

                            when(GlobalVariables.personnelUserInfoUsage) {
                                "create" -> {
                                    Utils.createAccount(
                                        GlobalVariables.personnel,GlobalVariables.estate.objectId)
                                }
                                "update" -> {

                                }
                                else -> {

                                }
                            }

                            GlobalVariables.personnelUserInfoUsage = "read"
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

        if (GlobalVariables.personnel.icon != null)
            root.image_userIcon.setImageBitmap(GlobalVariables.personnel.icon)
        root.textView_name.setText(GlobalVariables.personnel.name)
        root.textView_gender.setText(GlobalVariables.personnel.sex)
        root.textView_email.setText(GlobalVariables.personnel.email)
        root.textView_phone.setText(GlobalVariables.personnel.cellPhone)
        root.textView_income.setText(GlobalVariables.personnel.annual_income)
        if (GlobalVariables.personnel.industry.isNotEmpty())
            root.button_select_industry.setText(GlobalVariables.personnel.industry)

        //createSpinner()

        root.button_select_image.setOnClickListener {
            pickImageFromGallery()
        }

        root.button_select_industry.setOnClickListener {

        }

        return root
    }

    class SpinnerActivity : Activity(), AdapterView.OnItemSelectedListener {

        override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)

            GlobalVariables.personnel.auth = "landlord"
        }

        override fun onNothingSelected(parent: AdapterView<*>) {
            // Another interface callback
        }
    }

    private fun createSpinner() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.personnel_type_list,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            root.spinner.adapter = adapter
        }

        root.spinner.onItemSelectedListener = SpinnerActivity()
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
            root.image_userIcon.setImageURI(data?.data)
            imageBuffer = root.image_userIcon.drawable.toBitmap()
        }
    }
}