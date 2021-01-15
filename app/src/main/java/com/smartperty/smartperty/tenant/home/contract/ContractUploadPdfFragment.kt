package com.smartperty.smartperty.tenant.home.contract

import android.R.attr
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.smartperty.smartperty.R
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.fragment_contract_upload_pdf.view.*
import java.io.File


class ContractUploadPdfFragment : Fragment() {

    private lateinit var root:View
    private var base64String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_contract_upload_pdf, container, false)

        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()

        root.button_contract_upload_select_pdf.setOnClickListener {
            selectPdfFromStorage()
        }

        root.button_contract_upload_submit.setOnClickListener {
            // setup dialog builder
            val builder = android.app.AlertDialog.Builder(requireActivity())
            builder.setTitle("確定要送出嗎？")

            builder.setPositiveButton("是") { _, _ ->
                GlobalVariables.estate.contract!!.pdfString = base64String
                Thread {
                    GlobalVariables.api.uploadContractDocument(
                        GlobalVariables.estate.contract!!.landlord!!.id,
                        GlobalVariables.estate.contract!!.contractId,
                        "PDF",
                        GlobalVariables.estate.contract!!.pdfString
                    )
                }.start()
                root.findNavController().navigateUp()
            }

            // create dialog and show it
            requireActivity().runOnUiThread{
                val dialog = builder.create()
                dialog.show()
            }
        }

        return root
    }

    // the code of getting pdf /////////////////////////////////

    companion object {
        private const val PDF_SELECTION_CODE = 99
    }

    private fun selectPdfFromStorage() {
        Toast.makeText(requireContext(), "selectPDF", Toast.LENGTH_LONG).show()
        val browseStorage = Intent(Intent.ACTION_GET_CONTENT)
        browseStorage.type = "application/pdf"
        browseStorage.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(
            Intent.createChooser(browseStorage, "Select PDF"), PDF_SELECTION_CODE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PDF_SELECTION_CODE && resultCode == Activity.RESULT_OK && data != null) {

//            val name = File(data.data!!.path!!).name
//            root.button_contract_upload_select_pdf.text = name

            val bytes = requireContext().contentResolver.openInputStream(data.data!!)!!.readBytes()
            val _base64String = Base64.encodeToString(bytes, Base64.DEFAULT)

            base64String = ""
            for (char in _base64String) {
                if (char != '\n')
                    base64String += char
            }

            root.pdfView.fromBytes(Base64.decode(base64String, Base64.DEFAULT))
                .defaultPage(0)
                .spacing(10)
                .load()
        }
    }
}