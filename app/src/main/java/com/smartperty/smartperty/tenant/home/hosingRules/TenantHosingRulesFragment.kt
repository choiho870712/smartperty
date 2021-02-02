package com.smartperty.smartperty.tenant.home.hosingRules

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.smartperty.smartperty.R
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.activity_landlord.*
import kotlinx.android.synthetic.main.tenant_fragment_housing_rules.view.*

class TenantHosingRulesFragment : Fragment() {

    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.tenant_fragment_housing_rules, container, false)

        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()
        root.text_housing_rules.isEnabled = false
        fillInformation()

        if (GlobalVariables.loginUser.permission.property == "A") {
            GlobalVariables.toolBarUtils.setEditButtonVisibility(true)
            GlobalVariables.activity.toolbar.setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.button_edit -> {
                        GlobalVariables.toolBarUtils.setEditButtonVisibility(false)
                        GlobalVariables.toolBarUtils.setSubmitButtonVisibility(true)
//                        root.text_housing_rules.inputType = defaultInputType
                        root.text_housing_rules.isEnabled = true
                        root.text_housing_rules.background =
                            resources.getDrawable(R.drawable.style_hollow_white_smoke)
                        true
                    }
                    R.id.button_submit -> {
                        // setup dialog builder
                        val builder = android.app.AlertDialog.Builder(requireActivity())
                        builder.setTitle("確定要修改嗎？")

                        builder.setPositiveButton("是") { _, _ ->
                            GlobalVariables.estate.rules = root.text_housing_rules.text.toString()
                            GlobalVariables.toolBarUtils.setEditButtonVisibility(true)
                            GlobalVariables.toolBarUtils.setSubmitButtonVisibility(false)
//                            root.text_housing_rules.inputType = InputType.TYPE_NULL
                            root.text_housing_rules.isEnabled = false
                            root.text_housing_rules.background =
                                resources.getDrawable(R.drawable.style_empty)

                            Thread {
                                GlobalVariables.api.uploadPropertyRules(
                                    GlobalVariables.estate.landlord!!.id,
                                    GlobalVariables.estate.objectId,
                                    GlobalVariables.estate.rules
                                )
                            }.start()
                        }
                        builder.setNegativeButton("否") { _, _ ->
                            fillInformation()
                            GlobalVariables.toolBarUtils.setEditButtonVisibility(true)
                            GlobalVariables.toolBarUtils.setSubmitButtonVisibility(false)
//                            root.text_housing_rules.inputType = InputType.TYPE_NULL
                            root.text_housing_rules.isEnabled = false
                            root.text_housing_rules.background =
                                resources.getDrawable(R.drawable.style_empty)
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

        return root
    }

    private fun fillInformation() {
        root.text_housing_rules.setText(GlobalVariables.estate.rules)

    }

}
