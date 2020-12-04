package com.smartperty.smartperty.repair

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.smartperty.smartperty.R
import com.smartperty.smartperty.data.UserType
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.activity_landlord.*
import kotlinx.android.synthetic.main.fragment_recycler.view.*
import kotlinx.android.synthetic.main.fragment_repair_order_create.view.*

class RepairListFragment : Fragment() {

    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_recycler, container, false)

        setToolBar()
        refreshRepairList()
        createRepairListView()

        return root
    }

    private fun setToolBar() {
        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()
        GlobalVariables.toolBarUtils.setAddButtonVisibility(true)

        GlobalVariables.activity.toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.button_add -> {
                    // setup dialog builder
                    val builder = android.app.AlertDialog.Builder(requireActivity())
                    builder.setTitle("是否新增報修單？")

                    builder.setPositiveButton("是") { _, _ ->
                        root.findNavController().navigate(
                            R.id.action_repairListFragment_to_repairOrderCreateFragment
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

    private fun refreshRepairList() {
        // TODO call api : refresh repair list
    }

    private fun createRepairListView() {
        GlobalVariables.repairListLayoutManager = LinearLayoutManager(activity)
        GlobalVariables.repairListAdapter = RepairListAdapter(
            requireActivity(), root,
            GlobalVariables.repairList
        )
        root.recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = GlobalVariables.repairListLayoutManager
            adapter = GlobalVariables.repairListAdapter
        }
    }
}