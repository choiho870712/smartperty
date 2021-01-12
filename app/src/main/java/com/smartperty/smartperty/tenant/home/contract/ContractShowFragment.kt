package com.smartperty.smartperty.tenant.home.contract

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.recyclerview.widget.LinearLayoutManager
import com.smartperty.smartperty.R
import com.smartperty.smartperty.repair.ImageListAdapter2
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.fragment_contract_show.view.*
import kotlinx.android.synthetic.main.fragment_contract_show.view.recycler_image
import kotlinx.android.synthetic.main.fragment_contract_show.view.webView
import kotlinx.android.synthetic.main.fragment_contract_upload_image.view.*

class ContractShowFragment : Fragment() {

    private lateinit var root: View

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_contract_show, container, false)

        root.pdfView.visibility = View.GONE
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
            else {
                root.pdfView.visibility = View.VISIBLE

                val base64String = GlobalVariables.estate.contract!!.pdfString
                root.pdfView.fromBytes(Base64.decode(base64String, Base64.DEFAULT))
                    .defaultPage(0)
                    .spacing(10)
                    .load()
            }
        }
        else if (GlobalVariables.estate.contract!!.textString.isNotEmpty()) {
            root.webView.visibility = View.VISIBLE

            val webView = root.webView
            val webSettings = webView.settings
            val url = GlobalVariables.estate.contract!!.textString
            webSettings.javaScriptEnabled = true
            webView.webViewClient = WebViewClient()
            webView.loadUrl(url)
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

        return root
    }
}