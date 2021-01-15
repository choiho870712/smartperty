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
import com.smartperty.smartperty.data.Estate
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.fragment_choose_property.view.*

class ChoosePropertyFragment : Fragment() {

    private lateinit var root: View

    companion object {
        private var propertyList = mutableListOf<Estate>()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_choose_property, container, false)

        setToolBar()
        createFolderSelector()
        createTenantList()

        return root
    }

    private fun setToolBar() {
        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()
        GlobalVariables.toolBarUtils.setTitle("選擇物件")
    }

    class SpinnerActivity : Activity(), AdapterView.OnItemSelectedListener {

        override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)

            propertyList.clear()
            if (GlobalVariables.propertySelectorUsage == "rented") {
                GlobalVariables.estateDirectory[pos].list.forEach {
                    if (it.tenant != null) {
                        propertyList.add(it)
                    }
                }
            }
            else if (GlobalVariables.propertySelectorUsage == "not rented") {
                GlobalVariables.estateDirectory[pos].list.forEach {
                    if (it.tenant == null) {
                        propertyList.add(it)
                    }
                }
            }
            else {
                propertyList.addAll(GlobalVariables.estateDirectory[pos].list)
            }
            GlobalVariables.estateAdapter!!.notifyDataSetChanged()
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
        propertyList.clear()
        if (GlobalVariables.propertySelectorUsage == "rented") {
            GlobalVariables.estateDirectory[0].list.forEach {
                if (it.tenant != null) {
                    propertyList.add(it)
                }
            }
        }
        else if (GlobalVariables.propertySelectorUsage == "not rented") {
            GlobalVariables.estateDirectory[0].list.forEach {
                if (it.tenant == null) {
                    propertyList.add(it)
                }
            }
        }
        else {
            propertyList.addAll(GlobalVariables.estateDirectory[0].list)
        }
        GlobalVariables.estateLayoutManager = LinearLayoutManager(activity)
        GlobalVariables.estateAdapter =
            EstateAdapter(requireActivity(), root, propertyList)

        root.recycler_tenant_list.apply {
            setHasFixedSize(true)
            layoutManager = GlobalVariables.estateLayoutManager
            adapter = GlobalVariables.estateAdapter
        }
    }
}