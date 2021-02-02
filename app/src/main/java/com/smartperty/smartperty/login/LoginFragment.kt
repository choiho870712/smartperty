package com.smartperty.smartperty.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.smartperty.smartperty.R
import com.smartperty.smartperty.landlord.LandlordActivity
import com.smartperty.smartperty.tenant.TenantActivity
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.fragment_login.view.*

class LoginFragment : Fragment() {

    private lateinit var root: View
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_login, container, false)

        GlobalVariables.toolBarUtils.setVisibility(false)

        val username = root.username
        val password = root.password
        val login = root.login
        val loading = root.loading

        loginViewModel = ViewModelProviders.of(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(requireActivity(), Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(requireActivity(), Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)

                //Complete and destroy login activity once successful
                requireActivity().setResult(Activity.RESULT_OK)
                requireActivity().finish()
            }
        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            username.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }

            password.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                    //Perform Code
                    loading.visibility = View.VISIBLE
                    loginViewModel.login(username.text.toString(), password.text.toString())

                    return@OnKeyListener true
                }
                false
            })

            login.setOnClickListener {
                loading.visibility = View.VISIBLE
                loginViewModel.login(username.text.toString(), password.text.toString())
            }
        }

        root.forget_password.setOnClickListener {
            root.findNavController().navigate(
                R.id.action_loginFragment_to_forgetPasswordFragment)
        }

        root.text_login_about_us_entry.setOnClickListener {
            root.findNavController().navigate(
                R.id.action_loginFragment_to_aboutUsFragment)
        }

        root.textView_contact_us.setOnClickListener {
            root.findNavController().navigate(
                R.id.action_loginFragment_to_contactUsFragment
            )
        }

        root.text_login_policy_entry.setOnClickListener {
            root.findNavController().navigate(
                R.id.action_loginFragment_to_privacyFragment
            )
        }

        return root
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        if (GlobalVariables.loginUser.auth == "landlord") {
            requireActivity().intent = Intent(requireActivity(), LandlordActivity().javaClass)
            startActivity(requireActivity().intent)
        }
        else if (GlobalVariables.loginUser.auth == "accountant") {

        }
        else if (GlobalVariables.loginUser.auth == "tenant" ||
                 GlobalVariables.loginUser.auth == "technician") {
            requireActivity().intent = Intent(requireActivity(), TenantActivity().javaClass)
            startActivity(requireActivity().intent)
        }
        else {
            requireActivity().intent = Intent(requireActivity(), LandlordActivity().javaClass)
            startActivity(requireActivity().intent)
        }
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(requireContext(), errorString, Toast.LENGTH_SHORT).show()
    }

}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}