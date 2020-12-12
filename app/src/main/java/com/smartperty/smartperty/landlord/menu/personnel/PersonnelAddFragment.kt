package com.smartperty.smartperty.landlord.menu.personnel

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.findNavController
import com.smartperty.smartperty.R
import com.smartperty.smartperty.data.User
import com.smartperty.smartperty.data.UserType
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.activity_tenant.*
import kotlinx.android.synthetic.main.fragment_personnel_add.view.*
import kotlinx.android.synthetic.main.fragment_repair_order_create.view.spinner

class PersonnelAddFragment : Fragment() {

    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_personnel_add, container, false)

        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()
        GlobalVariables.toolBarUtils.setSubmitButtonVisibility(true)

        GlobalVariables.personnel = User(
            auth = UserType.TENANT
        )

        GlobalVariables.activity.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.button_submit -> {
                    // setup dialog builder
                    val builder = android.app.AlertDialog.Builder(requireActivity())
                    builder.setTitle("是否新增人員？")

                    builder.setPositiveButton("是") { _, _ ->
                        GlobalVariables.personnel.cellPhone = root.textView_phone.text.toString()
                        when (GlobalVariables.personnel.auth) {
                            UserType.TENANT -> {
                                GlobalVariables.tenantList.add(GlobalVariables.personnel)
                            }
                            UserType.TECHNICIAN -> {
                                GlobalVariables.plumberList.add(GlobalVariables.personnel)
                            }
                            UserType.ACCOUNTANT -> {
                                GlobalVariables.accountantList.add(GlobalVariables.personnel)
                            }
                            UserType.AGENT -> {
                                GlobalVariables.managerList.add(GlobalVariables.personnel)
                            }
                            else -> {

                            }
                        }
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

        return root
    }

    class SpinnerActivity : Activity(), AdapterView.OnItemSelectedListener {

        override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)

            GlobalVariables.personnel.auth = UserType.getByValue(pos+1)!!
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