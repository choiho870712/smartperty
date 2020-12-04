package com.smartperty.smartperty.landlord.menu.personnel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.smartperty.smartperty.R
import com.smartperty.smartperty.utils.GlobalVariables
import kotlinx.android.synthetic.main.fragment_personnel.view.*

class PersonnelFragment : Fragment() {

    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_personnel, container, false)

        GlobalVariables.toolBarUtils.removeAllButtonAndLogo()
        GlobalVariables.toolBarUtils.setTitle(GlobalVariables.personnel.name)

        root.textView_personnel_name.text = GlobalVariables.personnel.name
        root.textView_personnel_company.text = GlobalVariables.personnel.company
        root.textView_personnel_phone.text = GlobalVariables.personnel.cellPhone

        return root
    }

}