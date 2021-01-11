package com.smartperty.smartperty.tenant.setup

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.smartperty.smartperty.R
import com.smartperty.smartperty.login.LoginActivity
import com.smartperty.smartperty.utils.GlobalVariables
import com.smartperty.smartperty.utils.GlobalVariables.Companion.toolBarUtils
import kotlinx.android.synthetic.main.fragment_setup.*
import kotlinx.android.synthetic.main.fragment_setup.view.*

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

        root.button_tenant_setup_person_info.setOnClickListener {
            GlobalVariables.personnel = GlobalVariables.loginUser
            GlobalVariables.personnelUserInfoUsage = "update"
            root.findNavController().navigate(
                R.id.action_setupFragment_to_personnelUserInfoFragment
            )
        }

        root.button_tenant_setup_language.setOnClickListener {

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
            GlobalVariables.logout()
            requireActivity().setResult(Activity.RESULT_OK)
            startActivity(Intent(requireActivity(), LoginActivity().javaClass))
            requireActivity().finish()
        }

        return root
    }
}