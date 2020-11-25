package com.smartperty.smartperty.tenant.setup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.smartperty.smartperty.R
import com.smartperty.smartperty.utils.GlobalVariables.Companion.toolBarUtils
import kotlinx.android.synthetic.main.tenant_fragment_setup.view.*

class TenantSetupFragment : Fragment() {

    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.tenant_fragment_setup, container, false)

        toolBarUtils.removeAllButtonAndLogo()

        root.button_tenant_setup_help.setOnClickListener {
            root.findNavController().navigate(
                R.id.action_tenantSetupFragment_to_tenantHelpFragment)
        }

        return root
    }
}