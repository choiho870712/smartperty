package com.smartperty.smartperty.login.forgetPassword

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.smartperty.smartperty.R
import com.smartperty.smartperty.data.EstateList
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.fragment_estate_directory_create.view.*
import kotlinx.android.synthetic.main.fragment_forget_password.view.*

class ForgetPasswordFragment : Fragment() {

    private lateinit var root: View

    @SuppressLint("ShowToast")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_forget_password, container, false)

        GlobalVariables.toolBarUtils.setVisibility(true)
        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()

        root.button_forgot_password_submit.setOnClickListener {

            // setup dialog builder
            val builder = android.app.AlertDialog.Builder(requireActivity())
            builder.setTitle("確定要送出嗎？")

            builder.setPositiveButton("是") { _, _ ->

                val userName = root.textView_forget_password_username.text.toString()
                val phone = root.textView_forget_password_phone.text.toString()

                Thread {
                    if (GlobalVariables.api.forgotPassword(userName, phone)) {
                        if (activity!=null) requireActivity().runOnUiThread {
                            Toast.makeText(requireActivity(), "已傳送新密碼", Toast.LENGTH_LONG).show()
                            root.findNavController().navigateUp()
                        }
                    }
                    else {
                        if (activity!=null) requireActivity().runOnUiThread {
                            Toast.makeText(requireActivity(), "Error", Toast.LENGTH_LONG).show()
                        }
                    }

                }.start()
            }

            // create dialog and show it
            requireActivity().runOnUiThread{
                val dialog = builder.create()
                dialog.show()
            }
        }

        return root
    }

}