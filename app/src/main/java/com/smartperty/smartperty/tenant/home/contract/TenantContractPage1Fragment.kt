package com.smartperty.smartperty.tenant.home.contract

import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.smartperty.smartperty.R
import com.github.gcacace.signaturepad.views.SignaturePad
import com.smartperty.smartperty.repair.ImageListAdapter2
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.tenant_fragment_contract_page1.view.*
import kotlinx.android.synthetic.main.tenant_fragment_contract_page1.view.webView


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

        root.webView.visibility = View.GONE

        if (GlobalVariables.estate.contract!!.pdfString.isNotEmpty()) {

            if (GlobalVariables.estate.contract!!.pdfString.contains("https://")) {
                root.webView.visibility = View.VISIBLE

                val webView = root.webView
                val webSettings = webView.settings
                webView.settings.builtInZoomControls = false // 是否開啟手指縮放
                webView.settings.setSupportZoom(true)  // 是否顯示縮放按鈕(內部有縮放+/-按鈕)
                val url = "https://docs.google.com/gview?embedded=true&url=" +
                        GlobalVariables.estate.contract!!.pdfString
                //val url = "https://dotblogs.com.tw/STARHAO/2016/11/17/072931"
                webSettings.javaScriptEnabled = true
                webView.webViewClient = WebViewClient()
                webView.loadUrl(url)
            }
        }
        else if (GlobalVariables.estate.contract!!.textString.isNotEmpty()) {
            if (GlobalVariables.estate.contract!!.textString.contains("https://")) {
                root.webView.visibility = View.VISIBLE

                val webView = root.webView
                val webSettings = webView.settings
                val url = GlobalVariables.estate.contract!!.textString
                webSettings.javaScriptEnabled = true
                webView.webViewClient = WebViewClient()
                webView.loadUrl(url)
            }
        }
        else if (GlobalVariables.estate.contract!!.jpgBitmapList.isNotEmpty()) {
            root.recycler_image.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(activity)
                adapter = ImageListAdapter2(requireActivity(), root,
                    GlobalVariables.estate.contract!!.jpgBitmapList
                )
            }
        }

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