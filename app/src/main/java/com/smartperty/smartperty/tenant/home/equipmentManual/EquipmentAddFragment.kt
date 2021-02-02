package com.smartperty.smartperty.tenant.home.equipmentManual

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.findNavController
import com.smartperty.smartperty.R
import com.smartperty.smartperty.data.Equipment
import com.smartperty.smartperty.data.Room
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.activity_landlord.*
import kotlinx.android.synthetic.main.fragment_equipment_add.view.*

class EquipmentAddFragment : Fragment() {

    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_equipment_add, container, false)

        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()
        GlobalVariables.toolBarUtils.setSubmitButtonVisibility(true)
        GlobalVariables.activity.toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.button_submit -> {
                    // setup dialog builder
                    val builder = android.app.AlertDialog.Builder(requireActivity())
                    builder.setTitle("確定要送出嗎？")

                    builder.setPositiveButton("是") { _, _ ->
                        val equipment = Equipment(
                            name = root.textView_equipment_name.text.toString(),
                            count = root.textView_equipment_count.text.toString().toInt(),
                            image = root.imageView_equipment.drawable.toBitmap()
                        )

                        val roomList = GlobalVariables.estate.roomList
                        var room = roomList.firstOrNull {
                            it.name == root.textView_room_name.text.toString()
                        }
                        if (room == null) {
                            room = Room(
                                name = root.textView_room_name.text.toString()
                            )
                            GlobalVariables.estate.roomList.add(room)
                        }
                        room.equipmentList.add(equipment)

                        val updateRoomList = mutableListOf<Room>()
                        var updateRoom = updateRoomList.firstOrNull {
                            it.name == room.name
                        }
                        if (updateRoom == null) {
                            updateRoom = Room(
                                name = root.textView_room_name.text.toString()
                            )
                            updateRoomList.add(updateRoom)
                        }
                        updateRoom.equipmentList.add(equipment)

                        Thread {
                            GlobalVariables.api.uploadPropertyEquipment(
                                GlobalVariables.estate.landlord!!.id,
                                GlobalVariables.estate.objectId,
                                updateRoomList
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

        root.button_add_image.setOnClickListener {
            pickImageFromGallery()
        }

        root.button_select_room.setOnClickListener {
            val builderSingle: AlertDialog.Builder = AlertDialog.Builder(activity)

            val arrayAdapter = ArrayAdapter<String>(
                requireActivity(),
                android.R.layout.select_dialog_singlechoice
            )

            GlobalVariables.estate.roomList.forEach {
                arrayAdapter.add(it.name)
            }

            builderSingle.setNegativeButton("cancel") { dialog, which -> dialog.dismiss() }

            builderSingle.setAdapter(arrayAdapter) { _, which ->
                val strName = arrayAdapter.getItem(which)
                val builderInner: AlertDialog.Builder = AlertDialog.Builder(activity)

                builderInner.setTitle("確定選擇 $strName？")
                builderInner.setPositiveButton("是") { dialog, which ->
                    root.textView_room_name.setText(strName)
                    dialog.dismiss()
                }
                builderInner.setNegativeButton("否"
                ) { dialog, which -> dialog.dismiss() }
                builderInner.show()
            }
            builderSingle.show()
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
            root.imageView_equipment.setImageURI(data?.data)
        }
    }
}