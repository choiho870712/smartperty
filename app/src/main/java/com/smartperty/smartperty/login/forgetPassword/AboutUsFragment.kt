package com.smartperty.smartperty.login.forgetPassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.smartperty.smartperty.R
import kotlinx.android.synthetic.main.fragment_about_us.view.*

class AboutUsFragment : Fragment() {

    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_about_us, container, false)

        root.button_back.setOnClickListener {
            root.findNavController().navigateUp()
        }

        return root
    }
}