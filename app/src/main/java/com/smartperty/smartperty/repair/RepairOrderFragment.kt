package com.smartperty.smartperty.repair

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.smartperty.smartperty.R
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
                    GlobalVariables.repairOrder.statusString = strName!!
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

    private fun setToolBar() {
        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()
    }

    private fun writeInfoToView() {
        val repairOrder = GlobalVariables.repairOrder
        root.textView_repair_order_company.text = repairOrder.plumber.company
        root.textView_repair_order_plumber_name.text = repairOrder.plumber.name
        root.textView_repair_order_plumber_cell_phone.text = repairOrder.plumber.cellPhone

        root.textView_repair_order_tenant_address.text = repairOrder.estate?.address ?: ""
        root.textView_repair_order_creator_name.text = repairOrder.creator.name
        root.textView_repair_order_creator_cell_phone.text = repairOrder.creator.cellPhone

        root.textView_repair_order_title.text = repairOrder.title
        root.textView_repair_order_repair_date_time.text = repairOrder.repairDateTime

//        root.textView_repair_order_status.text = RepairStatus.getStringByStatus(repairOrder.status)
        root.textView_repair_order_status.text = repairOrder.statusString
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
        root.button_choose_tenant.visibility = View.VISIBLE
        root.button_choose_tenant.setOnClickListener {
            root.findNavController().navigate(
                R.id.action_repairOrderFragment_to_chooseTenantFragment
            )
        }
    }

    private fun setChoosePlumberButton() {
        root.button_choose_plumber.visibility = View.VISIBLE
        root.button_choose_plumber.setOnClickListener {
            root.findNavController().navigate(
                R.id.action_repairOrderFragment_to_choosePlumberFragment
            )
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
