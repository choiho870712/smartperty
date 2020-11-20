package com.example.smartperty.tenant.setup.help

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.smartperty.R
import com.example.smartperty.utils.GlobalVariables.Companion.toolBarUtils

class TenantHelpFragment : Fragment() {

    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.tenant_fragment_help, container, false)

        toolBarUtils.removeAllButtonAndLogo()

        return root
    }

}