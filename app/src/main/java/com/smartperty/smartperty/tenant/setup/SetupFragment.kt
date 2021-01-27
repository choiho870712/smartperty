package com.smartperty.smartperty.tenant.setup

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.findNavController
import com.smartperty.smartperty.R
import com.smartperty.smartperty.data.EstateList
import com.smartperty.smartperty.login.LoginActivity
import com.smartperty.smartperty.utils.GlobalVariables
import com.smartperty.smartperty.utils.GlobalVariables.Companion.toolBarUtils
import kotlinx.android.synthetic.main.fragment_estate_directory_create.view.*
import kotlinx.android.synthetic.main.fragment_personnel_user_info.view.*
import kotlinx.android.synthetic.main.fragment_setup.*
import kotlinx.android.synthetic.main.fragment_setup.view.*
import kotlinx.android.synthetic.main.fragment_setup.view.button_tenant_setup_help
import kotlinx.android.synthetic.main.fragment_setup.view.image_userIcon

class SetupFragment : Fragment() {

    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_setup, container, false)

        toolBarUtils.removeAllButtonAndLogo()

        root.textView_username.text = GlobalVariables.loginUser.name
        if (GlobalVariables.loginUser.icon!= null)
            root.image_userIcon.setImageBitmap(GlobalVariables.loginUser.icon)
        root.image_userIcon.setOnClickListener {
            GlobalVariables.imageHelper.openLargeImage(root.image_userIcon.drawable.toBitmap())
        }

        root.button_tenant_setup_person_info.setOnClickListener {
            GlobalVariables.personnel = GlobalVariables.loginUser
            GlobalVariables.personnelUserInfoUsage = "update"
            root.findNavController().navigate(
                R.id.action_setupFragment_to_personnelUserInfoFragment
            )
        }

        root.button_tenant_setup_language.setOnClickListener {
            val builderSingle: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            val arrayAdapter = ArrayAdapter<String>(
                requireContext(),
                android.R.layout.select_dialog_singlechoice
            )

            arrayAdapter.add("繁體中文")

            builderSingle.setNegativeButton("cancel") { dialog, which -> dialog.dismiss() }
            builderSingle.setAdapter(arrayAdapter) { _, which ->
                val strName = arrayAdapter.getItem(which)
            }
            builderSingle.show()
        }

        root.button_tenant_setup_help.setOnClickListener {

        }

        root.button_tenant_setup_score.setOnClickListener {

        }

        root.button_tenant_setup_privacy.setOnClickListener {
            root.findNavController().navigate(
                R.id.action_setupFragment_to_privacyFragment2
            )
        }

        root.button_tenant_setup_expiration.setOnClickListener {
            root.findNavController().navigate(
                R.id.action_setupFragment_to_aboutUsFragment2
            )
        }

        root.button_tenant_setup_logout.setOnClickListener {
            // setup dialog builder
            val builder = android.app.AlertDialog.Builder(requireActivity())
            builder.setTitle("確定要登出嗎？")

            builder.setPositiveButton("是") { _, _ ->
                GlobalVariables.logout()
                requireActivity().setResult(Activity.RESULT_OK)
                startActivity(Intent(requireActivity(), LoginActivity().javaClass))
                requireActivity().finish()
            }

            // create dialog and show it
            requireActivity().runOnUiThread{
                val dialog = builder.create()
                dialog.show()
            }
        }

        return root
    }
}