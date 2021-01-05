package com.smartperty.smartperty.tenant.home.contract

import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.smartperty.smartperty.R
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.fragment_contract_show.view.*

class ContractShowFragment : Fragment() {

    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_contract_show, container, false)

        if (GlobalVariables.estate.contract!!.pdfString.isNotEmpty()) {
            val base64String = GlobalVariables.estate.contract!!.pdfString
            root.pdfView.fromBytes(Base64.decode(base64String, Base64.DEFAULT))
                .defaultPage(0)
                .spacing(10)
                .load()
        }
        else if (GlobalVariables.estate.contract!!.jpgBitmap != null) {
            root.imageView_show_contract.visibility = View.VISIBLE
            root.imageView_show_contract.setImageBitmap(
                GlobalVariables.estate.contract!!.jpgBitmap
            )
        }

        return root
    }
}