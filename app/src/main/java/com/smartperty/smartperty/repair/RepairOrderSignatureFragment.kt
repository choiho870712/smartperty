package com.smartperty.smartperty.repair

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.findNavController
import com.github.gcacace.signaturepad.views.SignaturePad
import com.smartperty.smartperty.R
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.tenant_fragment_contract_page1.view.*

class RepairOrderSignatureFragment : Fragment() {

    private lateinit var root : View
    private lateinit var saveButton: Button
    private lateinit var clearButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_repair_order_signature, container, false)

        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()

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
            GlobalVariables.repairOrderPost.imageList.add(root.signaturePad.signatureBitmap)
            root.findNavController().navigateUp()
        }

        clearButton.setOnClickListener { root.signaturePad.clear() }

        return root
    }
}