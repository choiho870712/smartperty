package com.smartperty.smartperty.login.forgetPassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.smartperty.smartperty.R
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.fragment_forget_password.view.*

class ForgetPasswordFragment : Fragment() {

    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_forget_password, container, false)

        GlobalVariables.toolBarUtils.setVisibility(true)
        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()

        return root
    }

}