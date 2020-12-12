package com.smartperty.smartperty.landlord.menu.personnel

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.smartperty.smartperty.R
import com.smartperty.smartperty.data.User
import com.smartperty.smartperty.tools.SwipeToDeleteCallback
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.activity_tenant.*
import kotlinx.android.synthetic.main.fragment_personnel_list.view.*


class PersonnelListFragment : Fragment() {

    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_personnel_list, container, false)

        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()
        GlobalVariables.toolBarUtils.setAddButtonVisibility(true)

        GlobalVariables.activity.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.button_add -> {
                    // setup dialog builder
                    root.findNavController().navigate(
                        R.id.action_landlordPersonnelListFragment_to_personnelAddFragment
                    )

                    true
                }
                else -> false
            }
        }

        root.textView_tenant_count.text = GlobalVariables.tenantList.size.toString()
        root.textView_accountant_count.text = GlobalVariables.accountantList.size.toString()
        root.textView_manager_count.text = GlobalVariables.managerList.size.toString()
        root.textView_plumber_count.text = GlobalVariables.plumberList.size.toString()

        val tenantAdapter =
            PersonnelListAdapter(requireActivity(), root, GlobalVariables.tenantList)
        root.recycler_personnel_tenant.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = tenantAdapter
        }
        //enableSwipeToDeleteAndUndo(tenantAdapter, root.recycler_personnel_tenant)

        val managerAdapter =
            PersonnelListAdapter(requireActivity(), root, GlobalVariables.managerList)
        root.recycler_personnel_manager.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = managerAdapter
        }
        //enableSwipeToDeleteAndUndo(managerAdapter, root.recycler_personnel_manager)


        val accountantAdapter =
            PersonnelListAdapter(requireActivity(), root, GlobalVariables.accountantList)
        root.recycler_personnel_accountant.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = accountantAdapter
        }
        //enableSwipeToDeleteAndUndo(accountantAdapter, root.recycler_personnel_accountant)

        val plumberAdapter =
            PersonnelListAdapter(requireActivity(), root, GlobalVariables.plumberList)
        root.recycler_personnel_plumber.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = plumberAdapter
        }
        //enableSwipeToDeleteAndUndo(plumberAdapter, root.recycler_personnel_plumber)

        root.layout_tenant_title.setOnClickListener {
            if (root.recycler_personnel_tenant.visibility == View.VISIBLE)
                root.recycler_personnel_tenant.visibility = View.GONE
            else
                root.recycler_personnel_tenant.visibility = View.VISIBLE
        }

        root.layout_manager_title.setOnClickListener {
            if (root.recycler_personnel_manager.visibility == View.VISIBLE)
                root.recycler_personnel_manager.visibility = View.GONE
            else
                root.recycler_personnel_manager.visibility = View.VISIBLE
        }

        root.layout_accountant_title.setOnClickListener {
            if (root.recycler_personnel_accountant.visibility == View.VISIBLE)
                root.recycler_personnel_accountant.visibility = View.GONE
            else
                root.recycler_personnel_accountant.visibility = View.VISIBLE
        }

        root.layout_plumber_title.setOnClickListener {
            if (root.recycler_personnel_plumber.visibility == View.VISIBLE)
                root.recycler_personnel_plumber.visibility = View.GONE
            else
                root.recycler_personnel_plumber.visibility = View.VISIBLE
        }

        root.recycler_personnel_tenant.visibility = View.GONE
        root.recycler_personnel_manager.visibility = View.GONE
        root.recycler_personnel_accountant.visibility = View.GONE
        root.recycler_personnel_plumber.visibility = View.GONE

        return root
    }

    private fun enableSwipeToDeleteAndUndo(mAdapter: PersonnelListAdapter, recyclerView:RecyclerView) {
        val swipeToDeleteCallback: SwipeToDeleteCallback = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(@NonNull viewHolder: RecyclerView.ViewHolder, i: Int) {
                val position = viewHolder.adapterPosition
                val item: User = mAdapter.getData()[position]
                mAdapter.removeItem(position)
                val snackbar = Snackbar
                    .make(
                        root,
                        "Item was removed from the list.",
                        Snackbar.LENGTH_LONG
                    )
                snackbar.setAction("UNDO") {
                    mAdapter.restoreItem(item, position)
                    recyclerView.scrollToPosition(position)
                }
                snackbar.setActionTextColor(Color.YELLOW)
                snackbar.show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}