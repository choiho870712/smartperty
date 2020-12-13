package com.smartperty.smartperty.login.beforeLogin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.smartperty.smartperty.R
import kotlinx.android.synthetic.main.fragment_before_login_page.view.*

class BeforeLoginPageFragment(
    private val imageResourceId: Int,
    private val titleString:String,
    private val subTitleString:String) : Fragment() {

    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_before_login_page, container, false)

        root.imageView.setImageResource(imageResourceId)
        root.textView_title.text = titleString
        root.textView_subTitle.text = subTitleString

        return root
    }
}