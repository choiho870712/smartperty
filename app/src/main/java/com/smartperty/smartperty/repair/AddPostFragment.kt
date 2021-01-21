package com.smartperty.smartperty.repair

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.smartperty.smartperty.R
import com.smartperty.smartperty.data.RepairOrderPost
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.activity_landlord.toolbar
import kotlinx.android.synthetic.main.fragment_repair_add_post.view.*

class AddPostFragment : Fragment() {

    private lateinit var root: View
    private lateinit var imageListAdapter: ImageListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GlobalVariables.repairOrderPost = RepairOrderPost()
        GlobalVariables.imageListUsage = "edit"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_repair_add_post, container, false)

        setToolBar()
        setPickImageButton()
        createImageList()
        writeInfoToView()

        root.textView_date_time.text = GlobalVariables.getCurrentDateTime()

        root.button_repair_signature.setOnClickListener {
            root.findNavController().navigate(
                R.id.action_addMessageFragment_to_repairOrderSignatureFragment
            )
        }

        return root
    }

    private fun setToolBar() {
        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()
        GlobalVariables.toolBarUtils.setSubmitButtonVisibility(true)

        GlobalVariables.activity.toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.button_submit -> {
                    // setup dialog builder
                    val builder = android.app.AlertDialog.Builder(requireActivity())
                    builder.setTitle("確定送出訊息？")

                    builder.setPositiveButton("是") { _, _ ->
                        GlobalVariables.repairOrderPost.message =
                            root.textView_message.text.toString()
                        GlobalVariables.repairOrderPost.createDateTime =
                            root.textView_date_time.text.toString()
                        GlobalVariables.repairOrderPost.sender =
                            GlobalVariables.loginUser
                        GlobalVariables.repairOrder.postList.add(GlobalVariables.repairOrderPost)
                        Thread {
                            GlobalVariables.api.updateEventInformation(
                                GlobalVariables.repairOrder.landlord!!.id,
                                GlobalVariables.repairOrder.event_id,
                                GlobalVariables.repairOrder.description,
                                GlobalVariables.repairOrderPost
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
    }

    private fun setPickImageButton() {
        root.imageButton_add_image.setOnClickListener {
            pickImageFromGallery()
        }
    }

    private fun createImageList() {
        imageListAdapter = ImageListAdapter(
            requireActivity(), root, GlobalVariables.repairOrderPost.imageList)
        root.recyclerView_image.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false )
            adapter = imageListAdapter
        }
    }

    private fun writeInfoToView() {
        root.textView_date_time.text = GlobalVariables.getCurrentDateTime()
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
            val imageContainer = root.imageButton_add_image
            val imageList = GlobalVariables.repairOrderPost.imageList

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