package com.smartperty.smartperty.repair

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.smartperty.smartperty.R
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.fragment_choose_tenant.view.*

class ChooseTenantFragment : Fragment() {

    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_choose_tenant, container, false)

        setToolBar()
        createFolderSelector()
        createTenantList()

        return root
    }

    private fun setToolBar() {
        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()
    }

    class SpinnerActivity : Activity(), AdapterView.OnItemSelectedListener {

        override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)

            GlobalVariables.estateList.list.clear()
            GlobalVariables.estateList.list.addAll(
                GlobalVariables.estateDirectory[pos].list)
            GlobalVariables.estateAdapter.notifyDataSetChanged()
        }

        override fun onNothingSelected(parent: AdapterView<*>) {
            // Another interface callback
        }
    }

    private fun createFolderSelector() {
        val folderTitleList = mutableListOf<String>()
        GlobalVariables.estateDirectory.forEach {
            folderTitleList.add(it.title)
        }

        ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item, folderTitleList
        )
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                root.spinner.adapter = adapter
            }

        root.spinner.onItemSelectedListener = SpinnerActivity()
    }

    private fun createTenantList() {
        GlobalVariables.estateList.list.clear()
        GlobalVariables.estateList.list.addAll(
            GlobalVariables.estateDirectory[0].list)
        GlobalVariables.estateLayoutManager = LinearLayoutManager(activity)
        GlobalVariables.estateAdapter =
            EstateAdapter(requireActivity(), root, GlobalVariables.estateList.list)

        root.recycler_tenant_list.apply {
            setHasFixedSize(true)
            layoutManager = GlobalVariables.estateLayoutManager
            adapter = GlobalVariables.estateAdapter
        }
    }
}