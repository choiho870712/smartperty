package com.smartperty.smartperty.landlord.menu.personnel

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.navigation.findNavController
import com.smartperty.smartperty.R
import com.smartperty.smartperty.data.Estate
import com.smartperty.smartperty.data.User
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.activity_tenant.*
import kotlinx.android.synthetic.main.fragment_personnel_add.view.*
import kotlinx.android.synthetic.main.fragment_repair_order_create.view.spinner

class PersonnelAddFragment : Fragment() {

    private lateinit var root: View

    companion object {
        private lateinit var buttonSelectProperty: Button
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GlobalVariables.estate = Estate()
        GlobalVariables.personnel = User()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_personnel_add, container, false)

        buttonSelectProperty = root.button_select_property

        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()
        GlobalVariables.toolBarUtils.setSubmitButtonVisibility(true)

        GlobalVariables.activity.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.button_submit -> {
                    // setup dialog builder
                    val builder = android.app.AlertDialog.Builder(requireActivity())
                    builder.setTitle("是否新增人員？")

                    builder.setPositiveButton("是") { _, _ ->

                        Thread {
                            GlobalVariables.api.createAccount(
                                auth = GlobalVariables.personnel.auth,
                                object_id = GlobalVariables.estate.objectId
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

        createSpinner()

        if (GlobalVariables.estate.objectId.isNotEmpty())
            root.button_select_property.text = GlobalVariables.estate.getAddress()

        buttonSelectProperty.setOnClickListener {
            root.findNavController().navigate(
                R.id.action_personnelAddFragment_to_choosePropertyFragment
            )
        }

        return root
    }

    class SpinnerActivity : Activity(), AdapterView.OnItemSelectedListener {

        override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)
            val authArray = mutableListOf("tenant", "technician", "accountant", "agent")
            GlobalVariables.personnel.auth = authArray[pos]

            if (pos > 0) {
                buttonSelectProperty.visibility = View.GONE
            }
            else {
                buttonSelectProperty.visibility = View.VISIBLE
            }
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

}