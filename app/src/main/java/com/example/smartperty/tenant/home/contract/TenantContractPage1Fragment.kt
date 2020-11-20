package com.example.smartperty.tenant.home.contract

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.smartperty.R
import com.github.gcacace.signaturepad.views.SignaturePad
import kotlinx.android.synthetic.main.tenant_fragment_contract_page1.view.*


class TenantContractPage1Fragment : Fragment() {

    private lateinit var root:View
    private lateinit var saveButton: Button
    private lateinit var clearButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.tenant_fragment_contract_page1, container, false)

        saveButton = root.button_submit_tenant_contract_signature
        clearButton = root.button_clear_tenant_contract_signature

        saveButton.isEnabled = false
        clearButton.isEnabled = false

        root.signaturePad.setOnSignedListener(object : SignaturePad.OnSignedListener {
            override fun onStartSigning() {}
            override fun onSigned() {
                saveButton.isEnabled = true
                clearButton.isEnabled = true
            }

            override fun onClear() {
                saveButton.isEnabled = false
                clearButton.isEnabled = false
            }
        })

        saveButton.setOnClickListener { //write code for saving the signature here
            Toast.makeText(requireContext(), "Signature Saved", Toast.LENGTH_SHORT).show()
        }

        clearButton.setOnClickListener { root.signaturePad.clear() }


        return root
    }
}