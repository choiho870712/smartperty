package com.smartperty.smartperty.login.contactUs

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.findNavController
import com.smartperty.smartperty.R
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.fragment_contact_us.view.*

class ContactUsFragment : Fragment() {

    private lateinit var root:View

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_contact_us, container, false)

        GlobalVariables.toolBarUtils.setVisibility(true)
        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()

        val webView = root.webView
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        webView.loadUrl("http://google.com")

        return root
    }

}