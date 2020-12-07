package com.smartperty.smartperty.login

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.smartperty.smartperty.tenant.TenantActivity
import com.smartperty.smartperty.R
import com.smartperty.smartperty.data.UserType
import com.smartperty.smartperty.landlord.LandlordActivity
import com.smartperty.smartperty.utils.DBHelper
import com.smartperty.smartperty.utils.GlobalVariables

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        GlobalVariables.activity = this
        GlobalVariables.dbHelper = DBHelper(this)

    }
}
