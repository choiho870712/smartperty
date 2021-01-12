package com.smartperty.smartperty.tenant.home.contract

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.smartperty.smartperty.R
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.fragment_contract_upload_text.view.*

class ContractUploadTextFragment : Fragment() {

    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_contract_upload_text, container, false)

        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()

        root.button_contract_upload_submit.setOnClickListener {
            GlobalVariables.estate.contract!!.textString =
                root.text_contract_upload_text.text.toString()
            Thread {
                GlobalVariables.api.uploadContractDocument(
                    GlobalVariables.estate.contract!!.landlord!!.id,
                    GlobalVariables.estate.contract!!.contractId,
                    "TXT",
                    GlobalVariables.estate.contract!!.textString
                )
            }.start()
            root.findNavController().navigateUp()
        }

        return root
    }
}