package com.smartperty.smartperty.repair

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.smartperty.smartperty.R
import com.smartperty.smartperty.data.User
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.fragment_repair_order.view.*


class RepairOrderFragment : Fragment() {

    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_repair_order, container, false)

        setToolBar()
        writeInfoToView()
        createPostList()
        setChooseTenantButton()
        setChoosePlumberButton()
        setAddMessageButton()

        if (GlobalVariables.loginUser.auth == "landlord") {
            root.textView_repair_order_status.setOnClickListener {
                val builderSingle: AlertDialog.Builder = AlertDialog.Builder(requireContext())

                val arrayAdapter = ArrayAdapter<String>(
                    requireContext(),
                    android.R.layout.select_dialog_singlechoice
                )
                arrayAdapter.add("已接案")
                arrayAdapter.add("已指派")
                arrayAdapter.add("處理中")
                arrayAdapter.add("已結案")

                builderSingle.setNegativeButton("cancel") { dialog, which -> dialog.dismiss() }

                builderSingle.setAdapter(arrayAdapter) { _, which ->
                    val strName = arrayAdapter.getItem(which)
                    val builderInner: AlertDialog.Builder = AlertDialog.Builder(requireContext())
                    builderInner.setMessage(strName)
                    builderInner.setTitle("確定修改狀態？")
                    builderInner.setPositiveButton("是") { dialog, which ->
                        root.textView_repair_order_status.text = strName

                        GlobalVariables.repairOrder.status =
                            when (strName) {
                                "已接案" -> {
                                    "Received"
                                }
                                "已指派" -> {
                                    "Assigned"
                                }
                                "處理中" -> {
                                    "Processing"
                                }
                                "已結案" -> {
                                    "Closed"
                                }
                                else -> {
                                    "nil"
                                }
                            }
                        setStatusButtonColor()

                        Thread {
                            GlobalVariables.api.changeEventStatus(
                                GlobalVariables.repairOrder.landlord!!.id,
                                GlobalVariables.repairOrder.event_id,
                                GlobalVariables.repairOrder.status
                            )

                            val userList = mutableListOf<User>()
                            userList.addAll(GlobalVariables.repairOrder.participant)
                            if (GlobalVariables.repairOrder.landlord != null)
                                userList.add(GlobalVariables.repairOrder.landlord!!)

                            Thread {
                                GlobalVariables.api.createMessage(
                                    userList, "已修改狀態", "Event")
                            }.start()
                        }.start()
                        dialog.dismiss()
                    }
                    builderInner.setNegativeButton("否"
                    ) { dialog, which -> dialog.dismiss() }
                    builderInner.show()
                }
                builderSingle.show()
            }
        }

        return root
    }

    private fun setToolBar() {
        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()
    }

    private fun writeInfoToView() {
        val repairOrder = GlobalVariables.repairOrder
        val plumber = repairOrder.getTechnician()
        if (plumber != null) {
            root.textView_repair_order_plumber_name.text = plumber.name
            root.textView_repair_order_plumber_cell_phone.text = plumber.cellPhone
        }

        root.textView_repair_order_tenant_address.text = repairOrder.estate?.getAddress() ?: "no estate"

        if (repairOrder.creator != null) {
            root.textView_repair_order_creator_name.text = repairOrder.creator!!.name
            root.textView_repair_order_creator_cell_phone.text = repairOrder.creator!!.cellPhone
        }

        root.textView_repair_order_title.text = repairOrder.description
        root.textView_repair_order_repair_date_time.text = repairOrder.date

        setStatusButtonColor()
    }

    private fun setStatusButtonColor() {
        val myDrawable = when(GlobalVariables.repairOrder.status) {
            "Received" -> {
                R.drawable.style_hollow_light_blue
            }
            "Assigned" -> {
                R.drawable.style_hollow_light_blue
            }
            "Processing" -> {
                R.drawable.style_hollow_light_blue
            }
            "Closed" -> {
                R.drawable.style_hollow_green
            }
            else -> {
                R.drawable.style_hollow_red
            }
        }

        root.textView_repair_order_status.background =
            requireActivity().resources.getDrawable(myDrawable)

        val myTextColor = when(GlobalVariables.repairOrder.status) {
            "Received" -> {
                R.color.colorLightBlue
            }
            "Assigned" -> {
                R.color.colorLightBlue
            }
            "Processing" -> {
                R.color.colorLightBlue
            }
            "Closed" -> {
                R.color.colorGreen
            }
            else -> {
                R.color.colorRed
            }
        }

        val myText = when(GlobalVariables.repairOrder.status) {
            "Received" -> {
                "已接案"
            }
            "Assigned" -> {
                "已指派"
            }
            "Processing" -> {
                "處理中"
            }
            "Closed" -> {
                "已結案"
            }
            else -> {
                "選擇狀態"
            }
        }

        root.textView_repair_order_status.text = myText

        root.textView_repair_order_status.setTextColor(
            requireActivity().resources.getColor(myTextColor)
        )
    }

    private fun createPostList() {
        root.recyclerView_repair_order_post.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, true)
            adapter = RepairOrderPostAdapter(
                requireActivity(),
                root,
                GlobalVariables.repairOrder.postList
            )
        }
    }

    private fun setChooseTenantButton() {
        if (GlobalVariables.repairOrder.getTenant() != null)
        {
            root.button_choose_tenant.visibility = View.VISIBLE
            root.button_choose_tenant.setOnClickListener {
                root.findNavController().navigate(
                    R.id.action_repairOrderFragment_to_chooseTenantFragment
                )
            }
        }
        else {
            root.button_choose_tenant.visibility = View.GONE
        }
    }

    private fun setChoosePlumberButton() {
        if (GlobalVariables.repairOrder.getTechnician() != null) {
            root.button_choose_plumber.visibility = View.GONE
        }
        else {
            root.button_choose_plumber.visibility = View.VISIBLE
            root.button_choose_plumber.setOnClickListener {
                root.findNavController().navigate(
                    R.id.action_repairOrderFragment_to_choosePlumberFragment
                )
            }
        }
    }

    private fun setAddMessageButton() {
        root.button_add_message.setOnClickListener {
            root.findNavController().navigate(
                R.id.action_repairOrderFragment_to_addMessageFragment
            )
        }
    }
}
