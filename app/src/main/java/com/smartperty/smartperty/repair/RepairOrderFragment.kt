package com.smartperty.smartperty.repair

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.smartperty.smartperty.R
import com.smartperty.smartperty.data.RepairStatus
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
        if (repairOrder.postList.isNotEmpty())
            root.textView_repair_order_content.text = repairOrder.postList[0].message

        root.textView_repair_order_status.text = RepairStatus.getStringByStatus(repairOrder.status)
    }

    private fun createPostList() {
        root.recyclerView_repair_order_post.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, true)
            adapter = RepairOrderPostAdapter(requireActivity(), root, GlobalVariables.repairOrder.postList)
        }
    }

    private fun setChooseTenantButton() {
        if (GlobalVariables.repairOrder.status != RepairStatus.CHOOSING_TENANT) {
            root.button_choose_tenant.visibility = View.GONE
        }
        else {
            root.button_choose_tenant.visibility = View.VISIBLE
            root.button_choose_tenant.setOnClickListener {
                root.findNavController().navigate(
                    R.id.action_repairOrderFragment_to_chooseTenantFragment
                )
            }
        }
    }

    private fun setChoosePlumberButton() {
        if (GlobalVariables.repairOrder.status != RepairStatus.CHOOSING_PLUMBER) {
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
